package it.daniso.elencotelefonico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static it.daniso.elencotelefonico.DatabaseHandler.DEFAULT;

/**
 * Created by Daniso on 14/12/2016.
 */

public class PersonSpinnerAdapter extends ArrayAdapter<Person> {

    private Context context;
    private List<Person> listData;

    public PersonSpinnerAdapter(Context context, int textViewResourceId, List<Person> listData) {
        super(context, textViewResourceId, listData);
        this.context = context;
        this.listData = listData;
    }

    public int getCount(){
        return listData.size();
    }

    public Person getItem(int position){
        return listData.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item, parent, false);
        }
        TextView title = (TextView) convertView.findViewById(R.id.spItemTitle);
        if(listData.get(position) != null)
            title.setText(listData.get(position).getName()+" "+listData.get(position).getSurname());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.itemTitle);
        TextView subtitle = (TextView) convertView.findViewById(R.id.itemSubtitle);

        title.setText(listData.get(position).getName() + " " + listData.get(position).getSurname());
        if(listData.get(position).getCodFiscale() != DEFAULT) {
            subtitle.setText(listData.get(position).getCodFiscale());
        } else {
            subtitle.setText("");
        }

        return convertView;
    }

}
