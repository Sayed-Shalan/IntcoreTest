package com.sayed.intcoretest.offline_db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

public class MovieOfflineProvider extends ContentProvider {

    // fields for content provider
    static final String PROVIDER_NAME = "com.sayed.intcore.test.provider";
    public static final String URL = "content://" + PROVIDER_NAME + "/movie";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    // fields for the table (movie data)
    public static final String MOVIE__ID = "movie_id";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String POSTER_PATH = "poster_path";
    public static String[] PROJECTION = {MOVIE__ID, MOVIE_TITLE, POSTER_PATH};

    static final int MOVIE = 1;
    static final int MOVIE_ID = 2;
    DBHelper dbHelper;

    // projection map for a query
    private static HashMap<String, String> movieMap;

    // maps content URI "patterns" to the integer values that were set above
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "movie", MOVIE); //for all movies
        uriMatcher.addURI(PROVIDER_NAME, "movie/#", MOVIE_ID); //for a single movie (by id)
    }
    // database declarations
    private SQLiteDatabase database;
    static final String TABLE_NAME = "movie";

    //On Provider created
    @Override
    public boolean onCreate() {
        Context context = getContext();
        dbHelper = new DBHelper(context);
        // permissions to be writable
        database = dbHelper.getWritableDatabase();
        return database != null;
    }

    //for GET REQUEST
    @Override
    public Cursor query(Uri uri,String[] projection,String selection,String[] selectionArgs,String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME); //the TABLE_NAME to query on

        switch (uriMatcher.match(uri)) // get all or get by id
        {
            // maps all database column names
            case MOVIE:
                queryBuilder.setProjectionMap(movieMap);
                break;
            case MOVIE_ID: //when movie id = ?
                queryBuilder.appendWhere( MOVIE__ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder.isEmpty()){
            // No sorting-> sort on names by default
            sortOrder = MOVIE__ID;
        }
        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /*
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    //get type
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            // Get all movies records
            case MOVIE:
                return "DIR";
            // Get a particular movie
            case MOVIE_ID:
                return "ITEM";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    //POST REQUEST to insert
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long row = database.insert(TABLE_NAME, "", values);
        // If record is added successfully
        if(row >= 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }else {
            throw new SQLException("Fail to add a new record into " + uri);
        }
    }


    //delete
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){ //delete all or delete by movie id
            case MOVIE:
                // delete all the records of the table
                count = database.delete(TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                String id = uri.getLastPathSegment();	//gets the id
                count = database.delete( TABLE_NAME, MOVIE__ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    //update record fields
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case MOVIE:
                count = database.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case MOVIE_ID:
                count = database.update(TABLE_NAME, values, MOVIE__ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
