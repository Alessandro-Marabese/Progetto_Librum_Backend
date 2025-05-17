package it.epicode.Progetto_Librum_Backend.openlibraryimport;

import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.autori.AutoreRepository;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.generi.GenereRepository;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OpenLibraryImportService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final AutoreRepository autoreRepository;
    private final LibroRepository libroRepository;
    private final GenereRepository genereRepository;

    @Transactional
    public void importBooksBySubject(String subjectId, int maxBooks) {
        Genere genere = genereRepository.findById(subjectId).orElseGet(() -> fetchGenreFromAPI(subjectId));

        int imported = 0;
        int offset = 0;

        while (imported < maxBooks) {
            String url = "https://openlibrary.org/subjects/" + subjectId + ".json?limit=100&offset=" + offset;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> works = (List<Map<String, Object>>) response.get("works");

            for (Map<String, Object> work : works) {
                if (imported >= maxBooks) break;

                String workId = (String) work.get("key");

                Libro libro = libroRepository.findById(workId).orElse(null);
                if (libro != null) {
                    // Aggiunge il genere se non presente
                    if (!libro.getGeneri().contains(genere)) {
                        libro.getGeneri().add(genere);
                        genere.getLibri().add(libro);
                        libroRepository.save(libro);
                    }
                    continue;
                }

                libro = new Libro();
                libro.setId(workId);
                libro.setTitolo((String) work.get("title"));
                libro.setPrimoAnnoPubblicazione((Integer) work.get("first_publish_year"));

                if (work.containsKey("cover_id")) {
                    Integer coverId = (Integer) work.get("cover_id");
                    libro.setCoverUrl("https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg");
                }

                try {
                    Map<String, Object> workDetails = restTemplate.getForObject("https://openlibrary.org" + workId + ".json", Map.class);
                    Object desc = workDetails.get("description");
                    if (desc instanceof Map)
                        libro.setDescrizione((String) ((Map<String, Object>) desc).get("value"));
                    else if (desc instanceof String)
                        libro.setDescrizione((String) desc);
                } catch (Exception ignored) {}

                List<Map<String, Object>> authors = (List<Map<String, Object>>) work.get("authors");
                if (authors != null && !authors.isEmpty()) {
                    Set<Autore> autoriLibro = new HashSet<>();

                    for (Map<String, Object> authorEntry : authors) {
                        Object rawAuthorMap = authorEntry.get("author");

                        if (rawAuthorMap == null) {
                            String authorKey = (String) authorEntry.get("key");
                            if (authorKey != null && authorKey.startsWith("/authors/")) {
                                String authorId = authorKey.replace("/authors/", "");
                                Autore autore = autoreRepository.findById(authorId)
                                        .orElseGet(() -> fetchAuthorFromAPI(authorId));

                                if (autore != null) {
                                    autoriLibro.add(autore);
                                    autore.getGeneri().add(genere);
                                }
                            } else {
                                System.out.println("Chiave autore non valida per work: " + work.get("key"));
                            }
                            continue;
                        }

                        Map<String, Object> authorMap = (Map<String, Object>) rawAuthorMap;
                        String authorKey = (String) authorMap.get("key");

                        if (authorKey == null || !authorKey.startsWith("/authors/")) {
                            System.out.println("Chiave autore non valida per work: " + work.get("key") + " authorKey: " + authorKey);
                            continue;
                        }

                        String authorId = authorKey.replace("/authors/", "");
                        Autore autore = autoreRepository.findById(authorId)
                                        .orElseGet(() -> fetchAuthorFromAPI(authorId));

                        if (autore != null) {
                            autoriLibro.add(autore);
                            autore.getGeneri().add(genere);
                        }
                    }

                    libro.setAutori(autoriLibro);
                }

                libro.getGeneri().add(genere);
                genere.getLibri().add(libro);
                libroRepository.save(libro);
                imported++;
            }

            offset += 100;
        }

        genereRepository.save(genere);
    }

    private Autore fetchAuthorFromAPI(String authorKey) {
        System.out.println("Fetching author from API: " + authorKey);
        try {
            String cleanAuthorKey = authorKey.startsWith("/authors/") ? authorKey : "/authors/" + authorKey;
            String url = "https://openlibrary.org" + cleanAuthorKey + ".json";
            Map<String, Object> authorData = restTemplate.getForObject(url, Map.class);

            Autore autore = new Autore();
            autore.setId(cleanAuthorKey.replace("/authors/", ""));
            String name = (String) authorData.getOrDefault("personal_name", authorData.get("name"));
            autore.setName(name);

            Object bio = authorData.get("bio");
            if (bio instanceof Map)
                autore.setBio((String) ((Map<String, Object>) bio).get("value"));
            else if (bio instanceof String)
                autore.setBio((String) bio);

            String birthDateStr = (String) authorData.get("birth_date");
            String deathDateStr = (String) authorData.get("death_date");

            autore.setDataNascita(birthDateStr);
            autore.setDataMorte(deathDateStr);

            if (authorData.containsKey("photos")) {
                List<Integer> photos = (List<Integer>) authorData.get("photos");
                if (!photos.isEmpty()) {
                    autore.setPhotoUrl("https://covers.openlibrary.org/a/id/" + photos.get(0) + "-M.jpg");
                }
            }

            return autoreRepository.save(autore);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Genere fetchGenreFromAPI(String genreId) {
        try {
            String url = "https://openlibrary.org/subjects/" + genreId + ".json";
            Map<String, Object> data = restTemplate.getForObject(url, Map.class);

            Genere genere = new Genere();
            genere.setId(genreId);
            genere.setName((String) data.get("name"));

            return genereRepository.save(genere);
        } catch (Exception e) {
            return null;
        }
    }
}

