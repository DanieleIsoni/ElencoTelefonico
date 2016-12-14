package it.daniso.elencotelefonico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniso on 12/12/2016.
 */

public class Person {
    private String codFiscale;
    private String name;
    private String surname;
    private String telNumber;
    private String email;
    public static List<Person> personeList = new ArrayList<>();
    public static Map<String,Person> persone = new HashMap<>();

    public Person(){}

    public Person(String codFiscale, String name, String surname, String telNumber, String email){
        this.codFiscale = codFiscale;
        this.name=name;
        this.surname=surname;
        this.telNumber=telNumber;
        this.email=email;
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
