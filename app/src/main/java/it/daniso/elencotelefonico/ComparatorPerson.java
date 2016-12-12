package it.daniso.elencotelefonico;

import java.util.Comparator;

/**
 * Created by Daniso on 12/12/2016.
 */
public class ComparatorPerson implements Comparator<Person> {
    @Override
    public int compare(Person person, Person t1) {
        return person.getCodFiscale().compareTo(t1.getCodFiscale());
    }
}
