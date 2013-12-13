package com.cse3345.f13.Santoski;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final ArrayList<String> names;
	private final ArrayList<String> urls;
	private final ArrayList<String> decks;
	
	public CustomAdapter(Context context, ArrayList<String> names, ArrayList<String> urls, ArrayList<String> decks){
		super(context, R.layout.custom_layout, names);
        this.context = context;
        this.names = names;
        this.urls = urls;
        this.decks = decks;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView;
        if (rowView == null) {
        rowView = inflater.inflate(R.layout.custom_layout, parent, false);
        }
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        TextView textView2= (TextView) rowView.findViewById(R.id.deck);
        textView.setText(names.get(position));
        textView2.setText(decks.get(position));
        return rowView;
        }
}
