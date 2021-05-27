package br.com.bandtec.projetoac3.model;

public class Contratacao {

    private String uuid;
    private Professor professor;

    public Contratacao(String uuid, Professor professor) {
        this.uuid = uuid;
        this.professor = professor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
}
