package com.example.mohamedsallam.popularmovies_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Mohamed Sallam on 17-Feb-18.
 */

public class MovieAdapter extends  ArrayAdapter<Movie> {


public MovieAdapter(Context context, ArrayList<Movie> items) {
        super(context, 0, items);
        }


@Override
public View getView(int position, View convertView,  ViewGroup parent) {
        View rootView = convertView;
        if (convertView == null) {
        rootView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item, parent, false);
        }
        Movie movieApp = getItem(position);
        ImageView posterImageView = rootView.findViewById(R.id.poster_iv);
        Picasso.with(getContext()).load(movieApp.getPosterUrl()).into(posterImageView);
        return rootView;
        }

        }
