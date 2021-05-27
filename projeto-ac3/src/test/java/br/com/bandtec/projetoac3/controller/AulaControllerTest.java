package br.com.bandtec.projetoac3.controller;

import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Professor;
import br.com.bandtec.projetoac3.repository.AulaRepository;
import br.com.bandtec.projetoac3.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AulaControllerTest {

    @Autowired
    AulaController aulaController;

    @MockBean
    AulaRepository aulaRepository;


    @Test
    void getAulasDeveRetornar200() {
        List<Aula> aulas = Arrays.asList(new Aula(), new Aula());
        Mockito.when(aulaRepository.findAll()).thenReturn(aulas);
        ResponseEntity<List<Aula>> resposta = aulaController.getAulas();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(2, resposta.getBody().size());
    }

    @Test
    void getAulasDeveRetornar204() {
        List<Aula> aulas = Arrays.asList();
        Mockito.when(aulaRepository.findAll()).thenReturn(aulas);
        ResponseEntity resposta = aulaController.getAulas();

        assertEquals((204), resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void getAulaDeveRetornar200() {
        Optional<Aula> aula = Optional.of(new Aula());
        Mockito.when(aulaRepository.findById(1)).thenReturn(aula);
        ResponseEntity resposta = aulaController.getAula(1);

        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void getAulaDeveRetornar404() {
        ResponseEntity resposta = aulaController.getAula(1);

        assertEquals(404, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postAulaDeveRetornar201parte1() {
        Professor professor = new Professor(1);
        Aula aula = new Aula("arq", 6, professor);
        ResponseEntity resposta = aulaController.postAula(aula);

        assertEquals(201, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postAulaDeveRetornar201parte2() {
        Professor professor = new Professor(1);
        Aula aula = new Aula("dsajdhas", 4, professor);
        ResponseEntity resposta = aulaController.postAula(aula);

        assertEquals(201, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void deleteAulaDeveRetornar200() {
        Optional<Aula> aula = Optional.of(new Aula());
        Mockito.when(aulaRepository.findById(1)).thenReturn(aula);
        ResponseEntity resposta = aulaController.deleteAula(1);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void deleteAulaDeveRetornar404() {
        ResponseEntity resposta = aulaController.deleteAula(1);

        assertEquals(404, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void putAulaDeveRetornar200() {
        Aula aula = new Aula();
        aula.setId(1);
        Mockito.when(aulaRepository.existsById(1)).thenReturn(true);
        ResponseEntity resposta = aulaController.putAula(aula);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());

    }

    @Test
    void putAulaDeveRetornar404() {
        Aula aula = new Aula();
        aula.setId(1);
        Mockito.when(aulaRepository.existsById(1)).thenReturn(false);
        ResponseEntity resposta = aulaController.putAula(aula);

        assertEquals(404, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void getAulaDesfazerDeveRetornar200() {
        Aula aula = new Aula();
        aulaController.desfazer.push(aula);

        ResponseEntity resposta = aulaController.getAulaDesfazer();

        assertEquals(200, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void getAulaDesfazerDeveRetornar400() {


        ResponseEntity resposta = aulaController.getAulaDesfazer();

        assertEquals(400, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }
}