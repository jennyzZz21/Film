package edu.ucsb.ece.ece150.film;

import android.app.ListActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.os.Bundle;
import android.view.View;
import android.os.Parcel;
import android.widget.Toast;

import com.google.gson.Gson;

public class ListViewDemo extends ListActivity {
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayList<Movie> movieList = new ArrayList<Movie>();


    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;



    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_save);

        // Getting the intent passed from MainActivity
        final Intent intent = getIntent();
        ArrayList<Movie> movieList = loadArray(getApplicationContext());

        try {
            Movie movie = intent.getExtras().getParcelable("MOVIE");
            movieList.add(movie);
            // Remove Repeated Movies
            Set<Movie> set = new HashSet<>(movieList);
            movieList.clear();
            movieList.addAll(set);

            // Sort and Save
            Collections.sort(movieList);
            saveLists(getApplicationContext(), movieList);
        } catch(Exception e){
            e.printStackTrace();
        }

        CustomAdapter customAdapter = new CustomAdapter(this, movieList);
        setListAdapter(customAdapter);
    }


    public void saveLists(Context context, ArrayList<Movie> movies){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int length = movies.size();
        sharedPreferences.edit().putInt("arrayLength", length).apply();
        for(int i = 0; i < length; i++){
            Movie currMovie = movies.get(i);
            String movieStr = new Gson().toJson(currMovie);
            sharedPreferences.edit().putString("movie" + i, movieStr).apply();
        }
        sharedPreferences.edit().commit();
    }

    public static ArrayList<Movie> loadArray(Context context){
        ArrayList<Movie> movies = new ArrayList<Movie>();
        try{
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int length = sharedPreferences.getInt("arrayLength", 0);
            for (int i = 0; i < length; i++){
                String json = sharedPreferences.getString("movie" + i , null);
                Movie currMovie = new Gson().fromJson(json, Movie.class);
                movies.add(currMovie);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return movies;
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
