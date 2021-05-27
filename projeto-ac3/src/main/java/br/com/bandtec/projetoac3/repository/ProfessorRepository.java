package br.com.bandtec.projetoac3.repository;

import br.com.bandtec.projetoac3.model.Aula;
import br.com.bandtec.projetoac3.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
}
