package it.daniso.elencotelefonico;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniso on 13/12/2016.
 */

public class PersonExpandableListAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<Person>> listDataChild;
    public static String select = "Seleziona Dipendente";

    public PersonExpandableListAdapter(Context context,
                                 HashMap<String, List<Person>> listDataChild) {
        this.context = context;
        this.listDataHeader = new ArrayList<>();
        this.listDataHeader.add(select);
        this.listDataChild = listDataChild;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView groupItemTitle = (TextView) convertView.findViewById(R.id.group_item_title);
        groupItemTitle.setTypeface(null, Typeface.BOLD);
        groupItemTitle.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Person person = (Person) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView personName = (TextView) convertView.findViewById(R.id.itemTitle);
        personName.setText(person.getName()+" "+person.getSurname());

        TextView personCodFiscale = (TextView) convertView.findViewById(R.id.itemSubtitle);
        personCodFiscale.setText(person.getCodFiscale());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
