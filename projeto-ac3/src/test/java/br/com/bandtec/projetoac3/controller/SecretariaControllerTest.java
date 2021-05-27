package br.com.bandtec.projetoac3.controller;

import br.com.bandtec.projetoac3.FilaObj;
import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Contratacao;
import br.com.bandtec.projetoac3.model.Professor;
import br.com.bandtec.projetoac3.repository.AulaRepository;
import br.com.bandtec.projetoac3.services.ContratacaoService;
import br.com.bandtec.projetoac3.services.GravaDocumentoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SecretariaControllerTest {

    @Autowired
    SecretariaController secretariaController;

    @MockBean
    AulaRepository aulaRepository;

    @MockBean
    GravaDocumentoService gravaDocumentoService;

    @MockBean
    ContratacaoService contratacaoService;


    @Test
    void postProfessorDeveRetornar202() throws IOException {
        Professor professor = new Professor();
        professor.setNome("teste");
        contratacaoService.contratacaoFila = new FilaObj(1);
        contratacaoService.aguardando = new ArrayList<String>();
        ResponseEntity resposta = secretariaController.postProfessor(professor);

        assertEquals(202, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postProfessorDeveRetornar429() throws IOException {
        Professor professor = new Professor();
        professor.setNome("teste");
        contratacaoService.contratacaoFila = new FilaObj(0);
        ResponseEntity resposta = secretariaController.postProfessor(professor);

        assertEquals(429, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void getProfessorDeveRetornar202() {
        contratacaoService.Concluido = new ArrayList<String>();
        contratacaoService.aguardando = new ArrayList<String>();
        contratacaoService.aguardando.add("1");
        ResponseEntity resposta = secretariaController.getProfessor("1");

        assertEquals(202, resposta.getStatusCodeValue());
        assertEquals("analisando", resposta.getBody());
    }

    @Test
    void getProfessorDeveRetornar200() {
        contratacaoService.Concluido = new ArrayList<String>();
        contratacaoService.Concluido.add("1");
        ResponseEntity resposta = secretariaController.getProfessor("1");

        assertEquals(200, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void getDocumentoDeveRetornar200() {
        List<Aula> aulas = Arrays.asList(new Aula(), new Aula());
        Mockito.when(aulaRepository.findAll()).thenReturn(aulas);
        ResponseEntity resposta = secretariaController.getDocumento();

        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    void getDocumentoDeveRetornar204() {
        List<Aula> aulas = Arrays.asList();
        Mockito.when(aulaRepository.findAll()).thenReturn(aulas);
        ResponseEntity resposta = secretariaController.getDocumento();

        assertEquals(204, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postDocumentoDeveRetornar400() throws IOException {
        MockMultipartFile file = new MockMultipartFile("multiFile", "filename.txt", "text/plain", "some kml".getBytes());
        ResponseEntity resposta = secretariaController.PostDocumento(file);

        assertEquals(400, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }

    @Test
    void postDocumentoDeveRetornar200() throws IOException {
        FileInputStream inputFile = new FileInputStream("ArquivoSecretaria.txt");
        MockMultipartFile file = new MockMultipartFile("multiFile", "filename.txt", "text/plain", inputFile);
        ResponseEntity resposta = secretariaController.PostDocumento(file);

        assertEquals(200, resposta.getStatusCodeValue());
        assertNull(resposta.getBody());
    }
}