package it.daniso.elencotelefonico;

/**
 * Created by Daniso on 12/12/2016.
 */

public class Person {
    private String codFiscale;
    private String name;
    private String surname;
    private String telNumber;
    private String email;

    public Person(){}

    public Person(String codFiscale, String name, String surname, String telNumber, String email){
        this.codFiscale = codFiscale;
        this.name=name;
        this.surname=surname;
        this.telNumber="+39 0461 9 "+telNumber;
        this.email=email+"@comalp.esercito.difesa.it";
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
