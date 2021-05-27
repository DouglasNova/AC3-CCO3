package br.com.bandtec.projetoac3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 12)
    private String nome;

    @OneToMany(mappedBy = "professor")
    @JsonIgnore
    private List<Aula> aula;

    public Professor(Integer id) {
        this.id = id;
    }


    public Professor() {

    }

    public List<Aula> getAula() {
        return aula;
    }

    public void setAula(List<Aula> aula) {
        this.aula = aula;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
