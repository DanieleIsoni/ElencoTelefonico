package it.daniso.elencotelefonico;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IncaricoListAdapter extends ArrayAdapter<Incarico> {

    private List<Incarico> incarichi;
    private List<Incarico> incarichiOrig;

    public IncaricoListAdapter(Context context, int resource, List<Incarico> objects) {
        super(context, resource, objects);
        incarichi = objects;
        incarichiOrig = new ArrayList<>();
        incarichiOrig.addAll(incarichi);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Incarico incarico = incarichi.get(position);

        TextView nomeIncarico = (TextView) convertView.findViewById(R.id.nomeIncarico);
        nomeIncarico.setText(incarico.getNomeIncarico());

        TextView nomePersona = (TextView) convertView.findViewById(R.id.nomePersona);
        nomePersona.setText(incarico.getNomePersona());

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase();
        incarichi.clear();
        if (charText.length() == 0) {
            incarichi.addAll(incarichiOrig);
        } else {
            for (Incarico inc : incarichiOrig) {
                if (inc.getNomeIncarico().toLowerCase().contains(charText) || inc.getNomePersona().toLowerCase().contains(charText)) {
                    incarichi.add(inc);
                }
            }
        }
        notifyDataSetChanged();
    }

}
