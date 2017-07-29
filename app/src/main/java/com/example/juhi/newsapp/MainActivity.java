package com.example.juhi.newsapp;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;


// Adding loader manager and callbacks
public abstract class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,NewsAdapter.ItemClickListener {
    // to uniquely identify the loader
    private static final int LOADER = 1;


    static final String TAG = "mainactivity";
    private NewsAdapter newsAdapter;
    private ProgressBar mProgressIndicator;
    private RecyclerView rv;


    private SQLiteDatabase db; //Sqlite database instance
    private Cursor cur; //cursor object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressIndicator = (ProgressBar) findViewById(R.id.progressBar1);

        rv = (RecyclerView)findViewById(R.id.recyclerView);

        rv.setLayoutManager(new LinearLayoutManager(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.search) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(LOADER,null,this).forceLoad();
            return true;
        }

        return true;
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args){
        return new AsyncTaskLoader<Void>(this){
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                mProgressIndicator.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                // HW4: 7. Refresh articles method
           //     NewsJob.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<Void> loader){}

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        mProgressIndicator.setVisibility(View.INVISIBLE);
    }

    //Removed Network Task extending AsyncTask ---> Task 2

}