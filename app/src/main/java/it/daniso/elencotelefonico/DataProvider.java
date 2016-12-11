package it.daniso.elencotelefonico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DataProvider {

    public static List<Incarico> incaricoList = new ArrayList<>();
    public static Map<String,Incarico> incaricoMap = new HashMap<>();

    static {
        addIncarico("01", "Rilascio Documenti di Riconoscimento",
                    "Gianfranco", "Isoni", "40924", "addoc5terr");
        addIncarico("02", "02Incarico",
                    "Daniele", "Isoni", "1555", "email");
        addIncarico("04", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("05", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("06", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("07", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("08", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("09", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("10", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
        addIncarico("11", "03Incarico",
                    "Nome persona", "Cognome persona", "1555", "email");
    }

    private static void addIncarico(String incaricoId, String nomeIncarico, String name,
                                    String surname, String telNumber, String email) {
        Incarico item = new Incarico(incaricoId, nomeIncarico, name, surname, telNumber, email);
        incaricoList.add(item);
        incaricoMap.put(incaricoId, item);
    }

    public static List<String> getProductNames() {
        List<String> list = new ArrayList<>();
        for (Incarico incarico: incaricoList) {
            list.add(incarico.getNomeIncarico());
        }
        return list;
    }

    public static List<Incarico> getFilteredList(String searchString) {

        List<Incarico> filteredList = new ArrayList<>();
        for (Incarico incarico: incaricoList) {
            if (incarico.getIncaricoId().contains(searchString)) {
                filteredList.add(incarico);
            }
        }

        return filteredList;

    }
}
