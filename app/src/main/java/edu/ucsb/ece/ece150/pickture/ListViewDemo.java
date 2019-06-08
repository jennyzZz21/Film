package edu.ucsb.ece.ece150.pickture;

import android.app.ListActivity;
import java.util.ArrayList;

import android.content.Intent;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.View;
import android.os.Parcel;

public class ListViewDemo extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_save);

        // Getting the intent passed from MainActivity
        final Intent intent = getIntent();
        ArrayList<Movie> movieList = intent.getParcelableArrayListExtra("MOVIE_LIST");

        /*adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);*/
        CustomAdapter customAdapter = new CustomAdapter(this, movieList);
        setListAdapter(customAdapter);

        //addItems(movie_obj);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void addItems(Movie movie_obj) {
        String title = movie_obj.getTitle();
        String year = movie_obj.getYear();
        String language = movie_obj.getLanguage();
        String country = movie_obj.getCountry();
        String url = movie_obj.getUrl();

        listItems.add(title);
        listItems.add(year);
        listItems.add(language);
        listItems.add(country);
        listItems.add(url);
        adapter.notifyDataSetChanged();
    }
}
