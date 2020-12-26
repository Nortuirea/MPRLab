package com.example.mprlab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<Model> {

    public static class ViewHolder{
        private TextView title;
        private TextView author;
        private TextView description;
    }

    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Model> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.listview_items, parent, false);

            vh = new ViewHolder();
            vh.title = convertView.findViewById(R.id.book_title);
            vh.author = convertView.findViewById(R.id.book_author);
            vh.description = convertView.findViewById(R.id.book_description);

            convertView.setTag(vh);
        }
        else{
            vh = (ViewHolder) convertView.getTag();
        }

        Model record = getItem(position);

        if(record != null){
            vh.title.setText(record.title);
            vh.author.setText(record.author);
            vh.description.setText(record.description);
        }

        return convertView;
    }
}
