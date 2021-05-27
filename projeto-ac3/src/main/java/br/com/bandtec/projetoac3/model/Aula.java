package br.com.bandtec.projetoac3.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 3, max = 12)
    private String materia;

    @NotNull
    @Max(99)
    @PositiveOrZero
    private Integer sala;

    @NotNull
    @ManyToOne
    private Professor prof;


    public Aula(@NotBlank @Size(min = 3, max = 12) String materia, @NotNull @PositiveOrZero Integer sala, @NotNull Professor prof) {
        this.id = id;
        this.materia = materia;
        this.sala = sala;
        this.prof = prof;
    }

    public Aula() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor professor) {
        this.prof = professor;
    }

    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", materia='" + materia + '\'' +
                ", sala=" + sala +
                ", professor=" + prof +
                '}';
    }
}
