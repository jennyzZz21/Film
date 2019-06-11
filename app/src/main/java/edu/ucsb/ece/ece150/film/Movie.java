package edu.ucsb.ece.ece150.film;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable, Comparable {
    private String title;
    private String year;
    private String language;
    private String country;
    private String url;

    public void setTitle (String t){ this.title = t; }
    public void setYear (String y){ this.year = y; }
    public void setLanguage (String l){ this.language = l; }
    public void setCountry (String c){ this.country = c; }
    public void setUrl (String u){ this.url = u; }

    public String getTitle (){ return this.title;}
    public String getYear (){ return this.year;}
    public String getLanguage (){ return this.language;}
    public String getCountry (){ return this.country;}
    public String getUrl (){ return this.url;}

    public Movie(String title, String year, String language, String country, String url){
        this.title = title;
        this.year = year;
        this.language = language;
        this.country = country;
        this.url = url;
    }

    public Movie(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.title = data[0];
        this.year = data[1];
        this.language = data[2];
        this.country = data[3];
        this.url = data[4];
    }

    @Override
    public int compareTo(Object o){
        Movie compMovie = (Movie) o;
        return this.title.compareTo(compMovie.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return title.equals(movie.title) &&
                year.equals(movie.year) &&
                language.equals(movie.language) &&
                country.equals(movie.country);
    }

    @Override
    public int hashCode(){
        String hashStr = title + year + language + country;
        return hashStr.hashCode();
    }

    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title, this.year, this.language, this.country,
                this.url});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
