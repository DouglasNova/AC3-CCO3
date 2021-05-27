package br.com.bandtec.projetoac3.services;

import br.com.bandtec.projetoac3.FilaObj;
import br.com.bandtec.projetoac3.model.Contratacao;
import br.com.bandtec.projetoac3.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContratacaoService {

    @Autowired
    private ProfessorRepository professorRepository;

    public FilaObj<Contratacao> contratacaoFila;

    public List<String> aguardando;
    public List<String> Concluido;

    public ContratacaoService() {
        contratacaoFila = new FilaObj(10);
        this.aguardando = new ArrayList<String>();
        Concluido = new ArrayList<String>();
    }

    @Scheduled(fixedRate = 30000)
    public void Contratando() {
        if (!contratacaoFila.isEmpty()) {
            Contratacao contratacao = contratacaoFila.poll();
            professorRepository.save(contratacao.getProfessor());
            Concluido.add(contratacao.getUuid());
            aguardando.remove(contratacao.getUuid());
            System.out.println("Contratou");
        }
    }

}
