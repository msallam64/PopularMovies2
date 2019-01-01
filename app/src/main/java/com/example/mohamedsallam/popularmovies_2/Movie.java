package com.example.mohamedsallam.popularmovies_2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed Sallam on 17-Feb-18.
 */

class Movie implements Parcelable {
    private String title;
    private String releaseData;
    private String descrption;
    private double voteAverage;
    private final String posterUrl;
    private String id;


    protected Movie(Parcel in) {
        title = in.readString();
        releaseData = in.readString();
        descrption = in.readString();
        voteAverage = in.readDouble();
        posterUrl = in.readString();
        id = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getId() {
        return id;
    }


    public Movie(String title, String posterUrl, String releaseData, double voteAverage, String descrption, String Id) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseData = releaseData;
        this.voteAverage = voteAverage;
        this.descrption = descrption;
        this.id=Id;
    }

    public Movie(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public String getReleaseData() {
        return this.releaseData;
    }

    public String getPosterUrl() {
        return this.posterUrl;
    }

    public String getDescrption() {
        return this.descrption;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(releaseData);
        dest.writeString(posterUrl);
        dest.writeDouble(voteAverage);
        dest.writeString(id);
    }

}
