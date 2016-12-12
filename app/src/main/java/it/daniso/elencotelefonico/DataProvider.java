package it.daniso.elencotelefonico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataProvider {

    public static List<Incarico> incaricoList = new ArrayList<>();
    public static Map<String,Incarico> incaricoMap = new HashMap<>();

    static {
        addIncarico("Rilascio Documenti di Riconoscimento", "snigfr02a59v647g",
                    "Gianfranco", "Isoni", "40924", "addoc5terr");
        addIncarico("02Incarico", "snidnl96m21l378n",
                    "Daniele", "Isoni", "1555", "email");
        addIncarico("03Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("04Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("05Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("06Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("07Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("08Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("09Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("10Incarico", "nmpcgp01l02l000a",
                    "Nome persona", "Cognome persona", "1555", "email");
    }

    private static void addIncarico(String nomeIncarico, String codFiscale, String name,
                                    String surname, String telNumber, String email) {
        Person person = new Person(codFiscale, name, surname, telNumber, email);
        Incarico item = new Incarico(nomeIncarico, person);
        incaricoList.add(item);
        incaricoMap.put(nomeIncarico, item);
    }
}
