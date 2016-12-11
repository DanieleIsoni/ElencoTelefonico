package it.daniso.elencotelefonico;

public class Incarico {
    private String incaricoId;
    private String nomeIncarico;
    private String name;
    private String surname;
    private String telNumber;
    private String email;

    public Incarico(String incaricoId, String nomeIncarico, String name, String surname, String telNumber, String email){
        this.incaricoId = incaricoId;
        this.nomeIncarico = nomeIncarico;
        this.name=name;
        this.surname=surname;
        this.telNumber="+39 0461 9 "+telNumber;
        this.email=email+"@comalp.esercito.difesa.it";
    }

    public String getIncaricoId() {
        return incaricoId;
    }

    public String getNomeIncarico() {
        return nomeIncarico;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getNomePersona(){
        return name+" "+surname;
    }

    public void setName(String name) {
        this.name = name;
    }
}
