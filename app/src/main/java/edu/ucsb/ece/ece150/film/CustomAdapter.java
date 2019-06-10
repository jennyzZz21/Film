package edu.ucsb.ece.ece150.film;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Movie> {

    public CustomAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, 0, movieList);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie_obj = getItem(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            TextView title = convertView.findViewById(R.id.list_text);
            ImageView imag = convertView.findViewById(R.id.list_img);
            title.setText(movie_obj.getTitle());
            Picasso.get().load(movie_obj.getUrl()).into(imag);
        }
        return convertView;
    }
}

