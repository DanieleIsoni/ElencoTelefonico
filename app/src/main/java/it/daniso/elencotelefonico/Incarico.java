package it.daniso.elencotelefonico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.daniso.elencotelefonico.Person.persone;

public class Incarico {
    private String incaricoId;
    private String nomeIncarico;
    public Person person;
    public static List<Incarico> incarichi = new ArrayList<>();
    public static Map<String, Incarico> incarichiMap = new HashMap<>();

    public Incarico(){}

    public Incarico(String nomeIncarico, Person person){
        this.incaricoId = nomeIncarico.toLowerCase();
        this.nomeIncarico = nomeIncarico;
        this.person = person;
    }

    public Incarico(String nomeIncarico, String personId){
        this.incaricoId = nomeIncarico.toLowerCase();
        this.nomeIncarico = nomeIncarico;
        setPerson(personId);
    }

    public String getIncaricoId() {
        return incaricoId;
    }

    public String getNomeIncarico() {
        return nomeIncarico;
    }

    public String getNomePersona(){
        return person.getName()+" "+person.getSurname();
    }

    public void setIncaricoId(String incaricoId) {
        this.incaricoId = incaricoId;
    }

    public void setNomeIncarico(String nomeIncarico) {
        this.nomeIncarico = nomeIncarico;
    }

    public void setPerson(String personId) {
        this.person = persone.get(personId.toLowerCase());
    }
}
