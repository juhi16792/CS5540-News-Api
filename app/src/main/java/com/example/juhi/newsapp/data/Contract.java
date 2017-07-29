package com.example.juhi.newsapp.data;

import android.provider.BaseColumns;

/**
 * Created by juhi on 7/28/17.
 */

public class Contract {


    //Task 3: Contract --> Completed
    public static final class NewsItem implements BaseColumns{
        public static final String TABLE_NAME = "news";

        //source of the news article
        public static final String COLUMN_SOURCE =  "source";
        //author of the news article
        public static final String COLUMN_AUTHOR = "author";
        //title of the article
        public static final String COLUMN_TITLE = "title";
        //description of the article
        public static final String COLUMN_DESC = "description";
        //URL to the article
        public static final String COLUMN_URL = "url";
        //URL to the image
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_PUBLISHED_AT = "publishedAt";
    }
}
