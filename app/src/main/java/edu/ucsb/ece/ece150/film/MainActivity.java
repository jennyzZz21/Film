package edu.ucsb.ece.ece150.film;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Parcelable;

import java.util.ArrayList;

/*
 * Whatever you do, remember: whatever gets the job done is a good first solution.
 * Then start to redo it, keeping the job done, but the solutions more and more elegant.
 *
 * Don't satisfy yourself with the first thing that comes out.
 * Dig through the documentation, read your error logs.
 */
public class MainActivity extends AppCompatActivity {
    TextView tvYear, tvCountry, tvLanguage;
    ImageView ivPoster;

    EditText edMName;

    Movie movie_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edMName = findViewById(R.id.edMName);
        tvYear = findViewById(R.id.tvYear);
        tvCountry = findViewById(R.id.tvCountry);
        tvLanguage = findViewById(R.id.tvLanguage);
        ivPoster = findViewById(R.id.ivPoster);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void search(View view) {
        final String mName = edMName.getText().toString();
        if (mName.isEmpty()){
            edMName.setError("please provide movie name");
            return;
        }

        // OMDB API Key: 16317712
        final String url = "http://www.omdbapi.com/?t=" + mName + "&plot=full&apikey=16317712";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /*tvYear.setText(response);
                        Log.d("MOVIE", response);*/
                        try {
                            JSONObject movie = new JSONObject(response);
                            String result = movie.getString("Response");

                            if (result.equals("True")){
                                Toast.makeText(MainActivity.this, "Found", Toast.LENGTH_SHORT).show();
                                String title = movie.getString("Title");
                                String year = movie.getString("Year");
                                tvYear.setText("Year: " + year);

                                String country = movie.getString("Country");
                                tvCountry.setText("Country: " + country);

                                String language = movie.getString("Language");
                                tvLanguage.setText("Language: " + language);

                                String posterUrl = movie.getString("Poster");

                                if (posterUrl.equals("N/A")){ }
                                else{
                                    Picasso.get().load(posterUrl).into(ivPoster);
                                }

                                movie_obj = new Movie(title, year, language, country, posterUrl);
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(request);
    }

    public void save(View view) {
        if(movie_obj == null){
            Toast.makeText(getApplicationContext(), "No Movie Selected", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(MainActivity.this, ListViewDemo.class);
            intent.putExtra("MOVIE", movie_obj);
            startActivity(intent);
        }
    }

    public void openLists(View view) {
        Intent intent = new Intent(MainActivity.this, ListViewDemo.class);
        startActivity(intent);
    }


    public void share(View view) {
    }

    // depending on how you are going to pass information back and forth, you might need this
    // uncommented and filled out:
    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO: I bring news from the nether!
    }
    */
}
