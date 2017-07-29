package com.example.juhi.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.juhi.newsapp.data.Contract;
import com.example.juhi.newsapp.data.DBHelper;
import com.example.juhi.newsapp.data.DBUtils;
import com.example.juhi.newsapp.utilities.NewsAdapter;
import com.example.juhi.newsapp.utilities.NewsJob;
import com.example.juhi.newsapp.utilities.ScheduleUtils;


// Adding loader manager and callbacks
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>,NewsAdapter.ItemClickListener {
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

        rv = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        mProgressIndicator = (ProgressBar) findViewById(R.id.progressBar1);

        //Task 6 -> check if the app is installed or not. If it is not loaded, load data into db
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isNotInstalled = prefs.getBoolean("isNotInstalled",true);

        if(isNotInstalled){
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.restartLoader(LOADER, null, this).forceLoad();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isNotInstalled", false);
            editor.commit();
        }
        ScheduleUtils.scheduleRefresh(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Task 4. Get a writable database reference and store in db
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cur = DBUtils.getAll(db);
        newsAdapter = new NewsAdapter(cur, this);
        rv.setAdapter(newsAdapter);
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

        return super.onOptionsItemSelected(item);
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
                NewsJob.refreshArticles(MainActivity.this);
                return null;
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<Void> loader){}

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data){
        mProgressIndicator.setVisibility(View.INVISIBLE);
        // Task 7 :- get info from db
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cur = DBUtils.getAll(db);

        // Task 7 :- Reset data in recyclerview
        newsAdapter = new NewsAdapter(cur, this);
        rv.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }

    // Task 4 -- update onListItemClick to include Cursor --> Completed
    @Override
    public void onListItemClick(Cursor cursor, int index){
        cursor.moveToPosition(index);
        String url = cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_URL));
        Log.d(TAG, String.format("Url is %s",url));

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }
    }
    //Removed Network Task extending AsyncTask ---> Task 2

}