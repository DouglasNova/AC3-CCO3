package br.com.bandtec.projetoac3.controller;

import br.com.bandtec.projetoac3.PilhaObj;
import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Professor;
import br.com.bandtec.projetoac3.repository.AulaRepository;
import br.com.bandtec.projetoac3.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    PilhaObj<Aula> desfazer = new PilhaObj(5);

    @Autowired
    private AulaRepository repository;


    @GetMapping
    public ResponseEntity<List<Aula>> getAulas() {
        List<Aula> aulas = repository.findAll();
        if (aulas.isEmpty())
            return ResponseEntity.status(204).build();
        return ResponseEntity.status(200).body(aulas);

    }


    @GetMapping("/{id}")
    public ResponseEntity getAula(@PathVariable int id) {
        Optional<Aula> opAula = repository.findById(id);
        if (!opAula.isPresent())
            return ResponseEntity.status(404).build();
        return ResponseEntity.status(200).body(opAula);
    }

    @PostMapping
    public ResponseEntity postAula(@RequestBody @Valid Aula NovaAula) {
        repository.save(NovaAula);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAula(@PathVariable int id) {
        Optional<Aula> opAula = repository.findById(id);
        if (!opAula.isPresent())
            return ResponseEntity.status(404).build();
        repository.delete(opAula.get());
        desfazer.push(opAula.get());
        return ResponseEntity.status(200).build();
    }

    @PutMapping
    public ResponseEntity putAula(@RequestBody @Valid Aula AlterarAula) {
        if (!repository.existsById(AlterarAula.getId()))
            return ResponseEntity.status(404).build();
        repository.save(AlterarAula);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/desfazer")
    public ResponseEntity getAulaDesfazer() {
        if (desfazer.isEmpty())
            return ResponseEntity.status(400).build();
        repository.save(desfazer.pop());
        return ResponseEntity.status(200).build();

    }
}
