package it.epicode.Progetto_Librum_Backend.generi;

import it.epicode.Progetto_Librum_Backend.autori.Autore;
import it.epicode.Progetto_Librum_Backend.autori.AutoreRepository;
import it.epicode.Progetto_Librum_Backend.exception.NotFoundException;
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
public class GenereService {
    @Autowired
    private GenereRepository genereRepository;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutoreRepository autoreRepository;

    public GenereResponse fromEntity(Genere genere) {
        GenereResponse genereResponse = new GenereResponse();
        genereResponse.setId(genere.getId());
        genereResponse.setName(genere.getName());
        if(genere.getLibri() != null) {
            genereResponse.setLibri(
                    genere.getLibri().stream()
                            .map(Libro::getTitolo)
                            .collect(Collectors.toSet())
            );
        }
        if(genere.getAutori() != null) {
            genereResponse.setAutori(
                    genere.getAutori().stream()
                            .map(Autore::getName)
                            .collect(Collectors.toSet())
            );
        }
        return genereResponse;
    }

    public Genere findGenereByName(String genereName) {
        String likeParam = "%" + genereName + "%";
        return genereRepository.findByName(likeParam).orElseThrow(() -> new NotFoundException("Genere non trovato"));
    }

    public Page<GenereResponse> findAllGeneri(Pageable pageable) {
        return genereRepository.findAll(pageable).map(this::fromEntity);
    }

    public void deleteGenere(String id) {
        if (!genereRepository.existsById(id)) {
            throw new NotFoundException("Genere non trovato");
        }
        genereRepository.deleteById(id);
    }

    public Genere saveGenere(@Valid GenereRequest request) {
        Genere genere = new Genere();
        BeanUtils.copyProperties(request, genere);
        return genereRepository.save(genere);
    }

    public Genere updateGenere(@Valid GenereRequest request, String id) {
        Genere genere = genereRepository.findById(id).orElseThrow(() -> new NotFoundException("Genere non trovato"));
        BeanUtils.copyProperties(request, genere);
        return genereRepository.save(genere);
    }
}
