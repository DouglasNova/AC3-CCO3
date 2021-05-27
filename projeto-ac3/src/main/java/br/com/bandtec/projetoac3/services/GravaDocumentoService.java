package br.com.bandtec.projetoac3.services;

import br.com.bandtec.projetoac3.model.Aula;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GravaDocumentoService {

    public void gravaRegistro(String nomeArq, String registro, Boolean sob) {
        BufferedWriter saida = null;
        try {
            // o argumento true é para indicar que o arquivo não será sobrescrito e sim
            // gravado com append (no final do arquivo)
            saida = new BufferedWriter(new FileWriter(nomeArq, sob));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        try {
            saida.append(registro + "\n");
            saida.close();

        } catch (IOException e) {
            System.err.printf("Erro ao gravar arquivo: %s.\n", e.getMessage());
        }
    }

    public void gerarArquivo(List<Aula> aula) {

        String nomeArq = "ArquivoSecretaria.txt";
        String header = "";
        String corpo = "";
        String trailer = "";
        int contRegDados = 0;

        // Monta o registro header
        Date dataDeHoje = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        header += "00 DADOS SECRETARIA 01201116 ";
        header += formatter.format(dataDeHoje);
        header += " 01";

        // Grava o registro header
        gravaRegistro(nomeArq, header, false);


        for (Aula i : aula) {
            corpo = "02";
            corpo += String.format("%04d", i.getId());
            corpo += String.format("%-12s", i.getMateria());
            corpo += String.format("%02d", i.getProfessor().getId());
            corpo += String.format("%02d", i.getSala());
            contRegDados++;
            gravaRegistro(nomeArq, corpo, true);
        }


        // monta o trailer
        trailer += "01";
        trailer += String.format("%010d", contRegDados);
        gravaRegistro(nomeArq, trailer, true);

    }
}
