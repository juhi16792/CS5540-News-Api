package com.example.juhi.newsapp.utilities;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juhi.newsapp.R;
import com.example.juhi.newsapp.data.Contract;
import com.squareup.picasso.Picasso;

/**
 * Created by juhi on 6/29/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{

    private static final String TAG = NewsAdapter.class.getSimpleName();
    //private ArrayList<NewsItem> data; --> No need of ArrayList
    ItemClickListener listener;


    // Task 4 :- Add Cursor
    private Cursor cursor;

    private Context context;

    //Task 4 :- Editing the constructor with cursor as the parameter
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    // Task 4 :- Adding cursor in item click listener
    public interface ItemClickListener {
        void onListItemClick(Cursor cursor,int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    // Task 4 :- editing arraylist with cursor count
    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView time;

        //task 8 --> Add  image to recycler view
        public final ImageView img;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.description);
            time = (TextView)view.findViewById(R.id.time);
            img = (ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }

        public void bind(int pos){
            cursor.moveToPosition(pos);

            //Task 4 :- setting cursor index
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_DESC)));
            time.setText(cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_PUBLISHED_AT)));

            //Task 8 :- using picasso to load a thumbnail
            String urlToImage = cursor.getString(cursor.getColumnIndex(Contract.NewsItem.COLUMN_IMAGE_URL));
            Log.d(TAG, urlToImage);
            if(urlToImage != null){
                Picasso.with(context)
                        .load(urlToImage)
                        .into(img);
            }
        }

        @Override
        public void onClick(View v) {
            listener.onListItemClick(cursor,getAdapterPosition());
        }
    }
}