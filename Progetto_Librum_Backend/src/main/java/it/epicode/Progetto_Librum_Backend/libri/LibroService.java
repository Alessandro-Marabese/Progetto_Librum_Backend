package it.epicode.Progetto_Librum_Backend.libri;

import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.autori.AutoreRepository;
import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.generi.GenereRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private GenereRepository genereRepository;

    public LibroResponse fromEntity(Libro libro) {
        LibroResponse libroResponse = new LibroResponse();
        libroResponse.setId(libro.getId());
        libroResponse.setTitolo(libro.getTitolo());
        libroResponse.setDescrizione(libro.getDescrizione());
        libroResponse.setCoverUrl(libro.getCoverUrl());
        libroResponse.setPrimoAnnoPubblicazione(libro.getPrimoAnnoPubblicazione());

        if(libro.getAutori() != null) {
            libroResponse.setNomiAutori(
                    libro.getAutori().stream()
                            .map(Autore::getName)
                            .collect(Collectors.toSet())
            );
        }
        if(libro.getGeneri() != null) {
            libroResponse.setNomiGeneri(
                    libro.getGeneri().stream()
                            .map(Genere::getName)
                            .collect(Collectors.toSet()));
        }
        return libroResponse;
    }

    public Page<LibroResponse> findAllByAutore(String autore, Pageable pageable) {
        String likeParam = "%" + autore + "%";
        return libroRepository.findAllByAutoreNameContainingOrderByAutoreNameAsc(likeParam, pageable).map(this::fromEntity);
    }

    public Page<LibroResponse> findAllByAutoreId(String autoreId, Pageable pageable) {
        return libroRepository.findAllByAutoreId(autoreId, pageable)
                .map(this::fromEntity);
    }

    public Page<LibroResponse> findAllByGenere(String genere, Pageable pageable) {
        String likeParam = "%" + genere + "%";
        return libroRepository.findAllByGeneriNameContainingOrderByGenereNameAsc(likeParam, pageable).map(this::fromEntity);
    }

    @Transactional
    public LibroResponse findById(String id) {
        Libro libro = libroRepository.findById(id).orElse(null);
        if (libro == null) return null;

        return new LibroResponse(
                libro.getId(),
                libro.getTitolo(),
                libro.getDescrizione(), // il campo LOB, ora accessibile
                libro.getCoverUrl(),
                libro.getPrimoAnnoPubblicazione(),
                libro.getAutori().stream().map(Autore::getName).collect(Collectors.toSet()),
                libro.getGeneri().stream().map(Genere::getName).collect(Collectors.toSet())
        );
    }

    public Page<LibroResponse> findAllByTitolo(String titolo, Pageable pageable) {
        String likeParam = "%" + titolo + "%";
        return libroRepository.findAllByTitoloContainingOrderByTitoloAsc(likeParam, pageable).map(this::fromEntity);
    }

    @Transactional
    public void deleteLibro(String id) {
        Libro libro = libroRepository.findById(id).orElseThrow(() -> new NotFoundException("Libro non trovato"));
        Set<Autore> autori = libro.getAutori();
        Set<Genere> generi = libro.getGeneri();
        if(generi != null) {
            generi.forEach(genere -> genere.getLibri().remove(libro));
        }
        if(autori != null) {
            autori.forEach(autore -> autore.getLibri().remove(libro));
        }
        libroRepository.delete(libro);
    }

    public Libro save(@Valid LibroRequest request) {
        Libro libro = new Libro();
        BeanUtils.copyProperties(request, libro);
        return libroRepository.save(libro);
    }

    public Libro update(@Valid LibroRequest request, String id) {
        Libro libro = libroRepository.findById(id).orElseThrow(() -> new NotFoundException("Libro non trovato"));
        BeanUtils.copyProperties(request, libro);
        return libroRepository.save(libro);
    }

}
