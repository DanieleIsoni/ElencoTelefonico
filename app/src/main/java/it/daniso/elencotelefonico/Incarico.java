package it.daniso.elencotelefonico;

import static it.daniso.elencotelefonico.MainActivity.persone;

public class Incarico {
    private String nomeIncarico;
    public Person person;

    public Incarico(){}

    public Incarico(String nomeIncarico, Person person){
        this.nomeIncarico = nomeIncarico;
        this.person = person;
    }

    public String getNomeIncarico() {
        return nomeIncarico;
    }

    public String getNomePersona(){
        return person.getName()+" "+person.getSurname();
    }

    public void setNomeIncarico(String nomeIncarico) {
        this.nomeIncarico = nomeIncarico;
    }

    public void setPerson(String personId) {
        this.person = findPerson(personId, 0, persone.size()-1);
    }

    private Person findPerson(String personId, int i, int f){
        int m = (i+f)/2;
        if(persone.get(m).getCodFiscale() == personId)
            return persone.get(m);

        return persone.get(m).getCodFiscale().compareTo(personId) == -1 ? findPerson(personId, i, m) : findPerson(personId, m, f);
    }
}
