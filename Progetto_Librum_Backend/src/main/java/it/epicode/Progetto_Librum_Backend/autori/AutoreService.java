package it.epicode.Progetto_Librum_Backend.autori;

import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
import it.epicode.Progetto_Librum_Backend.generi.Genere;
import it.epicode.Progetto_Librum_Backend.generi.GenereRepository;
import it.epicode.Progetto_Librum_Backend.libri.Libro;
import it.epicode.Progetto_Librum_Backend.libri.LibroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.stream.Collectors;

@Service
@Validated
public class AutoreService {
    @Autowired
    private AutoreRepository autoreRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private GenereRepository genereRepository;

    public AutoreResponse fromEntity(Autore autore) {
        AutoreResponse autoreResponse = new AutoreResponse();
        autoreResponse.setId(autore.getId());
        autoreResponse.setName(autore.getName());
        autoreResponse.setBio(autore.getBio());
        autoreResponse.setPhotoUrl(autore.getPhotoUrl());
        autoreResponse.setDataNascita(autore.getDataNascita());
        autoreResponse.setDataMorte(autore.getDataMorte());

        if(autore.getLibri() != null) {
            autoreResponse.setLibri(
                    autore.getLibri().stream()
                            .map(Libro::getTitolo)
                            .collect(Collectors.toSet())
            );
        }
        if(autore.getGeneri() != null) {
            autoreResponse.setGeneri(
                    autore.getGeneri().stream()
                            .map(Genere::getName)
                            .collect(Collectors.toSet())
            );
        }
        return autoreResponse;
    }

    public Autore findById(String id) {
        return autoreRepository.findById(id).orElseThrow(() -> new NotFoundException("Autore non trovato"));
    }

    public Autore findByName(String autoreName) {
        return autoreRepository.findByName(autoreName).orElseThrow(() -> new NotFoundException("Autore non trovato"));
    }

    public Page<AutoreResponse> findAll(Pageable pageable) {
        return autoreRepository.findAll(pageable).map(this::fromEntity);
    }

    public Autore saveAutore(@Valid AutoreRequest autoreRequest) {
        Autore autore = new Autore();
        BeanUtils.copyProperties(autoreRequest, autore);
        return autoreRepository.save(autore);
    }

    public Autore updateAutore(String id, @Valid AutoreRequest autoreRequest) {
        Autore autore = findById(id);
        BeanUtils.copyProperties(autoreRequest, autore);
        return autoreRepository.save(autore);
    }

    public void deleteAutore(String id) {
        if(!autoreRepository.existsById(id)) {
            throw new NotFoundException("Autore non trovato");
        }
        Autore autore = findById(id);
        autoreRepository.delete(autore);
    }
}
