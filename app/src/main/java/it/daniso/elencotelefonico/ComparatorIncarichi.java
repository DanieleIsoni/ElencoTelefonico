package it.daniso.elencotelefonico;

import java.util.Comparator;

/**
 * Created by Daniso on 12/12/2016.
 */

public class ComparatorIncarichi implements Comparator<Incarico>{

    @Override
    public int compare(Incarico incarico, Incarico t1) {

        return incarico.getNomeIncarico().compareTo(t1.getNomeIncarico());
    }
}
