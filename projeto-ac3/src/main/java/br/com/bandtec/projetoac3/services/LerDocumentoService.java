package br.com.bandtec.projetoac3.services;

import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Professor;
import br.com.bandtec.projetoac3.repository.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class LerDocumentoService {

    @Autowired
    private AulaRepository repository;

    public ResponseEntity leArquivo(MultipartFile Arq) {
        BufferedReader entrada = null;
        String registro;
        String tipoRegistro;
        int erros = 0;
        int contRegistro = 0;

        // Abre o arquivo
        try {

            entrada = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(Arq.getBytes())));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        // Lê os registros do arquivo
        try {
            // Lê um registro
            registro = entrada.readLine();

            while (registro != null) {
                // Obtém o tipo do registro
                tipoRegistro = registro.substring(0, 2); // obtém os 2 primeiros caracteres do registro

                if (tipoRegistro.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Tipo de arquivo: " + registro.substring(3, 19));
                    int RA = Integer.parseInt(registro.substring(21, 28));
                    System.out.println("RA: " + RA);
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(29, 48));
                    System.out.println("Versão do layout: " + registro.substring(49, 51));
                } else if (tipoRegistro.equals("01")) {
                    System.out.println("\nTrailer");
                    int qtdRegistro = Integer.parseInt(registro.substring(2, 12));
                    if (qtdRegistro == contRegistro) {
                        System.out.println("Quantidade de registros gravados compatível com quantidade lida");
                    } else {
                        System.out.println("Quantidade de registros gravados não confere com quantidade lida");
                    }
                } else if (tipoRegistro.equals("02")) {
                    if (contRegistro == 0) {
                        System.out.println();
                        System.out.printf("Iniciando o Registro");

                    }
                    try {
                        Aula aula = new Aula();
                        aula.setId(Integer.parseInt(registro.substring(2, 6)));
                        aula.setMateria(registro.substring(6, 18).trim());
                        aula.setProf(new Professor(Integer.parseInt(registro.substring(18, 20))));
                        ;
                        aula.setSala(Integer.parseInt(registro.substring(20, 22)));
                        System.out.println();
                        System.out.println("registrando aluno " + aula.toString());
                        System.out.println();
                        repository.save(aula);
                    } catch (Exception e) {
                        erros++;
                    }


                    contRegistro++;
                } else {
                    return ResponseEntity.status(400).build();
                }

                // lê o próximo registro
                registro = entrada.readLine();
            }

            // Fecha o arquivo
            entrada.close();
        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
            return ResponseEntity.status(500).build();
        }
        if (erros == 0)
            return ResponseEntity.status(200).build();
        return ResponseEntity.status(200).body("concluido com erros, ocorreram " + erros + " erro(s) de " + contRegistro);
    }
}
