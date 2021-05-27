package br.com.bandtec.projetoac3.controller;

import br.com.bandtec.projetoac3.FilaObj;
import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Contratacao;
import br.com.bandtec.projetoac3.model.Professor;
import br.com.bandtec.projetoac3.repository.AulaRepository;
import br.com.bandtec.projetoac3.repository.ProfessorRepository;
import br.com.bandtec.projetoac3.services.ContratacaoService;
import br.com.bandtec.projetoac3.services.GravaDocumentoService;
import br.com.bandtec.projetoac3.services.LerDocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/secretaria")
public class SecretariaController {


    @Autowired
    ContratacaoService contratacaoService;
    @Autowired
    private AulaRepository repository;
    @Autowired
    private LerDocumentoService leitura;
    @Autowired
    private GravaDocumentoService gravacao;


    @PostMapping("/contratar")
    public ResponseEntity postProfessor(@RequestBody @Valid Professor professor) {
        String protocolo = UUID.randomUUID().toString();
        Contratacao contratacao = new Contratacao(protocolo, professor);
        if (contratacaoService.contratacaoFila.isFull()) {
            return ResponseEntity.status(429).build();
        }
        contratacaoService.contratacaoFila.insert(contratacao);
        contratacaoService.aguardando.add(protocolo);
        return ResponseEntity.status(202).header("protocolo", protocolo).build();
    }

    @GetMapping("/contratar/{uuid}")
    public ResponseEntity getProfessor(@PathVariable String uuid) {
        if (contratacaoService.Concluido.contains(uuid)) {
            contratacaoService.Concluido.remove(uuid);
            return ResponseEntity.status(200).build();
        }
        if (contratacaoService.aguardando.contains(uuid)) {
            return ResponseEntity.status(202).body("analisando");
        }
        return ResponseEntity.status(404).build();
    }


    @GetMapping("/documentos")
    public ResponseEntity getDocumento() {

        List<Aula> aulas = repository.findAll();
        if (aulas.isEmpty())
            return ResponseEntity.status(204).build();
        gravacao.gerarArquivo(aulas);

        String filename = "ArquivoSecretaria.txt";
        File file = new File(filename);


        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.parseMediaType("application/text"));


        headers.add("content-disposition", "inline;filename=" + filename);
        byte[] fileContent;
        try {
            fileContent = Files.readAllBytes(file.toPath());
        } catch (IOException ioe) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(200)
                .headers(headers)
                .body(fileContent);
    }

    @PostMapping("/documentos")
    public ResponseEntity PostDocumento(@RequestParam MultipartFile multiFile) {
        return leitura.leArquivo(multiFile);
    }


}
