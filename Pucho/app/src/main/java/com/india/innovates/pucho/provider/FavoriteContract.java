package com.india.innovates.pucho.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Raghunandan on 10-02-2016.
 */
public class FavoriteContract {


    public interface FavoriteColumns {


        String QUESTION_ID = "question_id";
        String QUESTION_ASKEDBY_USERNAME= "question_askedby_username";
        String QUESTION_ACTIVE = "question_active";
        String QUESTION_TITLE = "question_title";
        String QUESTION_CONTENT = "question_content";
        String QUESTION_UPVOTE = "question_upvote";
        String QUESTION_DOWNVOTE = "question_dwonvote";
        String QUESTION_AUDIO_FILE_URL = "question_audio_url";
        String QUESTION_ASKEDON = "question_askedon";
        String QUESTION_SAVED = "question_saved";

    }

    public static final String CONTENT_AUTHORITY = "com.india.innovates.pucho.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final String PATH_FAVORITE = "favorites";


    public static class Favorite implements FavoriteColumns,BaseColumns {


        public static final String TABLE_FAVORITES = "Favorites";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        /**
         * MIME type for All Questions.
         */
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.pucho.favorites";
        /**
         * MIME type for individual Question.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.pucho.favorite";

        /**
         * Default "ORDER BY" clause.
         */
        public static final String DEFAULT_SORT = BaseColumns._ID + " DESC";

        /**
         * Build {@link Uri} for requested {@link #QUESTION_ID}.
         */
        public static Uri buildFavoriteUri(String questionId) {
            return CONTENT_URI.buildUpon().appendPath(questionId).build();
        }
    }
}
