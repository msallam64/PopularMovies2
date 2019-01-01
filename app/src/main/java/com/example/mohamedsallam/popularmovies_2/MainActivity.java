package com.example.mohamedsallam.popularmovies_2;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

import com.example.mohamedsallam.popularmovies_2.data.Contract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mAdapter;
    GridView gridView ;
    ArrayList<Movie> x;
    int index;
    Bundle y;
    private String title ;
    private String releaseDate;
    private String decription;
    private double voteAverage;
    private String posterUrl;
    private String ID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.gridview);
        x = new ArrayList<>();
        mAdapter = new MovieAdapter(this, x);
        gridView.setAdapter(mAdapter);
        if(savedInstanceState == null || !savedInstanceState.containsKey("XList")) {
            x = new ArrayList<Movie>();
            mAdapter = new MovieAdapter(this, x);
            gridView.setAdapter(mAdapter);
        }else {
            x = savedInstanceState.getParcelableArrayList("XList");
            mAdapter = new MovieAdapter(this, x);
            gridView.setAdapter(mAdapter);
        }
       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                 title = mAdapter.getItem(i).getTitle();
                 releaseDate = mAdapter.getItem(i).getReleaseData();
                 decription = mAdapter.getItem(i).getDescrption();
                 voteAverage = mAdapter.getItem(i).getVoteAverage();
                 posterUrl = mAdapter.getItem(i).getPosterUrl();
                 ID = mAdapter.getItem(i).getId();
                intent.putExtra("ID", ID);
                intent.putExtra("title", title);
                intent.putExtra("release_date", releaseDate);
                intent.putExtra("decription", decription);
                intent.putExtra("vote_average", voteAverage);
                intent.putExtra("poster_url", posterUrl);
                startActivity(intent);
            }
        });


        MovieAppAsyncTask task = new MovieAppAsyncTask();
        task.execute(MyData.baseRequest + MyData.popular + MyData.apiKey);
            }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("XList", x);
        super.onSaveInstanceState(outState);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        x = savedInstanceState.getParcelableArrayList("XList");
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private class MovieAppAsyncTask extends AsyncTask<String, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(String... strings) {
            if (strings.length < 1 || strings[0] == null) {
                return null;
            }
            return Networking.fetchMovieAppData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Movie> movieApps) {
            mAdapter.clear();
            if (movieApps != null && !movieApps.isEmpty()) {
                mAdapter.addAll(movieApps);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fav) {
            Cursor cursor = getContentResolver().query(Contract.MovieEntry.CONTENT_URI, new String[] {Contract.MovieEntry._ID,
                    Contract.MovieEntry.COLUMN_NAME}, null, null, null);
            startManagingCursor(cursor);

            // THE DESIRED COLUMNS TO BE BOUND
            String[] columns = new String[] {  Contract.MovieEntry.COLUMN_NAME};
            // THE XML DEFINED VIEWS WHICH THE DATA WILL BE BOUND TO
            int[] to = new int[] { R.id.movieTtle};
            // CREATE THE ADAPTER USING THE CURSOR POINTING TO THE DESIRED DATA AS WELL AS THE LAYOUT INFORMATION
            SimpleCursorAdapter Adapter = new SimpleCursorAdapter(this, R.layout.favourit_item, cursor, columns, to);

            // SET THIS ADAPTER AS YOUR LISTACTIVITY'S ADAPTER
            gridView.setAdapter(Adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    title = mAdapter.getItem(i).getTitle();
                    releaseDate = mAdapter.getItem(i).getReleaseData();
                    decription = mAdapter.getItem(i).getDescrption();
                    voteAverage = mAdapter.getItem(i).getVoteAverage();
                    posterUrl = mAdapter.getItem(i).getPosterUrl();
                    ID = mAdapter.getItem(i).getId();
                    intent.putExtra("ID", ID);
                    intent.putExtra("title", title);
                    intent.putExtra("release_date", releaseDate);
                    intent.putExtra("decription", decription);
                    intent.putExtra("vote_average", voteAverage);
                    intent.putExtra("poster_url", posterUrl);
                    startActivity(intent);
                }
            });


        }
        if (id == R.id.top_rated) {
            new MovieAppAsyncTask().execute(MyData.baseRequest + MyData.toprated + MyData.apiKey);
            return true;
        }
        if (id == R.id.popular) {
            new MovieAppAsyncTask().execute(MyData.baseRequest + MyData.popular + MyData.apiKey);


        }
        return super.onOptionsItemSelected(item);
    }
}
