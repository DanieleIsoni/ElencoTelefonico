package it.daniso.elencotelefonico;

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
        for(Person p : MainActivity.persone){
            if(p.getCodFiscale() == personId){
                this.person = p;
            }
        }
    }
}
