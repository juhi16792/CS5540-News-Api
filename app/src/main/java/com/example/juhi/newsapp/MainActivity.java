package com.example.juhi.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.juhi.newsapp.utilities.NetworkUtils;

import java.net.URL;

import static com.example.juhi.newsapp.R.menu.search;

public class MainActivity extends AppCompatActivity {

    private TextView mNewsTextView;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewsTextView = (TextView) findViewById(R.id.news_data);
        spinner = (ProgressBar) findViewById((R.id.progressBar1));
        spinner.setVisibility(View.GONE);

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
            mNewsTextView.setText("");
            NetworkRequests task = new NetworkRequests();
            task.execute();
        }
        return true;
    }
    private void loadNewsData(){
        new NetworkRequests().execute();
    }
    public class NetworkRequests extends AsyncTask<String, Void, String[]> {
      @Override
      protected void onPreExecute (){
          super.onPreExecute();
          spinner.setVisibility(View.VISIBLE);

      }
      @Override
      protected String[] doInBackground(String... params) {
            URL builtURL = NetworkUtils.buildUrl();
            try {
                String newsResponse = NetworkUtils
                        .getResponseFromHttpUrl(builtURL);
                String[] response = new String[1];
                response[0] = newsResponse;
                return response;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
      }

      @Override
      protected void onPostExecute(String[] newsData){
            spinner.setVisibility(View.GONE);
            if(newsData[0] == null)
            {
                mNewsTextView.setText("no text was received") ;

            }
            else {

                mNewsTextView.setText(newsData[0] + "\n\n\n");
            }
      }
    }
}
