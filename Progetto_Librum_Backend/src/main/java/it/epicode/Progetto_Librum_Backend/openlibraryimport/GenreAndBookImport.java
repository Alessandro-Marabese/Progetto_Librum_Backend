package it.epicode.Progetto_Librum_Backend.openlibraryimport;

import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.generi.GenereRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class GenreAndBookImport implements CommandLineRunner {

    private final OpenLibraryImportService importService;
    private final GenereRepository genreRepository;

    @Override
    public void run(String... args) {
        /*try {
            System.out.println("🔎 Scraping OpenLibrary subjects...");
            Document doc = Jsoup.connect("https://openlibrary.org/subjects").get();

            Elements genreLinks = doc.select("a[href^=/subjects/]");

            Set<String> uniqueSubjects = new HashSet<>();

            for (Element link : genreLinks) {
                String href = link.attr("href"); // es: /subjects/fantasy
                String[] parts = href.split("/");

                if (parts.length >= 3) {
                    String subjectId = parts[2];
                    if (subjectId.matches("^[a-z0-9_\\-]+$")) {
                        uniqueSubjects.add(subjectId);
                    }
                }
            }

            System.out.println("🎯 Found " + uniqueSubjects.size() + " genres.");

            for (String subjectId : uniqueSubjects) {
                System.out.println("📚 Importing genre: " + subjectId);
                importService.fetchGenreFromAPI(subjectId);
            }

            System.out.println("✅ Genres saved. Importing books and authors...");

            List<Genere> genres = genreRepository.findAll();

            for (Genere genre : genres) {
                System.out.println("📘 Importing books for genre: " + genre.getId());
                importService.importBooksBySubject(genre.getId(), 5);
            }

            System.out.println("🏁 Import completed!");

        } catch (IOException e) {
            System.err.println("❌ Error fetching genres: " + e.getMessage());
        }*/
    }
}
