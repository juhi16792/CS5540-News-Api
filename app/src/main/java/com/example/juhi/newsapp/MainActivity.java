package com.example.juhi.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.juhi.newsapp.model.NewsItem;
import com.example.juhi.newsapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;

import static com.example.juhi.newsapp.R.menu.search;

public class MainActivity extends AppCompatActivity {


    static final String TAG = "mainactivity";
    private ProgressBar spinner;

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        spinner = (ProgressBar) findViewById((R.id.progressBar1));
        spinner.setVisibility(View.GONE);

        rv = (RecyclerView)findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(search, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
            NetworkRequests task = new NetworkRequests();
            task.execute();
        }
        return true;
    }
    private void loadNewsData(){
        new NetworkRequests().execute();
    }
    public class NetworkRequests extends AsyncTask<URL, Void, ArrayList<NewsItem>> {
      @Override
      protected void onPreExecute (){
          super.onPreExecute();
          spinner.setVisibility(View.VISIBLE);
      }

      @Override
      protected ArrayList<NewsItem> doInBackground(URL... params) {
            ArrayList<NewsItem> result = null;
            URL builtURL = NetworkUtils.buildUrl();
            try {
                String newsResponse = NetworkUtils
                        .getResponseFromHttpUrl(builtURL);
                String[] response = new String[1];
                response[0] = newsResponse;
                result = NetworkUtils.parseJSON(response[0]);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            return result;
      }

      @Override
      protected void onPostExecute(final ArrayList<NewsItem> newsData){
            super.onPostExecute(newsData);
            spinner.setVisibility(View.GONE);
            if(newsData!=null) {
                NewsAdapter adapter = new NewsAdapter(newsData, new NewsAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int clickedItemIndex) {
                        String url = newsData.get(clickedItemIndex).getUrl();
                        Log.d(TAG, String.format("Url %s", url));
                        openWebPage(url);
                    }
                });
                rv.setAdapter(adapter);
            }
      }
        public void openWebPage(String url) {
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
