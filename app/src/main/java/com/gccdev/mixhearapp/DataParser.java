package com.gccdev.mixhearapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class DataParser extends AsyncTask<String,Void,Song[]>{
    private final  String TAG = DataParser.class.getSimpleName();
    private static Context context;
    private View rootView;
    private static final String BASE_URL = "https://api.mixcloud.com";
    private CustomAdapter adapter;
    private ListView listView;
    private ProgressDialog progBar;

    private CustomCursorAdapter cursorAdapter;
    private SimpleCursorAdapter simpleCursorAdapter;

    private static Uri songUri;
    //COSTRUTTORE
    public DataParser(Context context, View rootView){
        this.context = context;
        this.rootView=rootView;
        progBar = new ProgressDialog(context);
        progBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progBar.setIndeterminate(true);
        progBar.setMessage("Caricamento dati ...");
        progBar.show();
    }





    @Override
    protected Song[] doInBackground(String... params) {
        Song[] songs = new Song[0];
        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(params[0])
                    .build();
            URL url = new URL(builtUri.toString());
            Log.v(TAG,"Built URI " + builtUri.toString());
            songs = getData();

        }catch (IOException e){
            Log.v(TAG,"Impossibile connettersi alla pagina web. Controllare la URL.");
        }
        return songs;
    }


    protected void onPostExecute(Song[] list) {
        listView = (ListView)rootView.findViewById(R.id.listview);
        adapter = new CustomAdapter(context);
        listView.setAdapter(adapter);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        progBar.dismiss();


    }



    public  static  Song[] getImageAndName(String JSONStr) throws JSONException{
        final String TITLE = "name";
        final String IMAGE_URL = "pictures";
        final String DIM_MEDIUM = "medium_mobile" ;
        final String DIM_LARGE ="large";
        final String CREATED ="created_time";
        final String LENGTH ="audio_length";
        final String SLUG ="slug";
        final String URL = "url";
        final String PLAY_COUNT ="play_count";
        final String FAVORITE_COUNT = "favorite_count";
        final String LISTENER_COUNT = "listener_count";
        final String REPOST_COUNT = "repost_count";
        final String UPDATED_TIME = "updated_time";
        final String COMMENT_COUNT ="comment_count";

        List<Song> result = new ArrayList<>();

        JSONObject res = new JSONObject(JSONStr);
        JSONArray data = res.getJSONArray("data");

        for(int i =0;i<data.length();i++) {
            try {

                JSONObject songs = data.getJSONObject(i);
                String title = songs.getString(TITLE);
                String created_time = songs.getString(CREATED);
               // String l = songs.getString(LENGTH);
               // String length = getDurationString(l);
                String slug = songs.getString(SLUG);
                String url = songs.getString(URL);
                int play_c = songs.getInt(PLAY_COUNT);
                int favorite_c = songs.getInt(FAVORITE_COUNT);
                int listener_c = songs.getInt(LISTENER_COUNT);
                int repost_c = songs.getInt(REPOST_COUNT);
                String up_time = songs.getString(UPDATED_TIME);
                int comment_c = songs.getInt(COMMENT_COUNT);
                JSONObject pic = songs.getJSONObject(IMAGE_URL);
                String imageUrlMedium = pic.getString(DIM_MEDIUM);
                String imageUrlLarge = pic.getString(DIM_LARGE);
                Song p = new Song(title, imageUrlMedium);
                result.add(p);
//                values.put(Contract.Songs.COLUMN_ID,title);
//                values.put(Contract.Songs.COLUMN_LENGTH,length);
//                songUri = context.getContentResolver().insert(
//                        Contract.Songs.CONTENT_URI,
//                        values
//                );

            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return result.toArray(new Song[result.size()]);
    }

        public static String getPreviousPage(String JSONStr) throws JSONException{
        JSONObject res = new JSONObject(JSONStr);
        JSONObject paging = res.getJSONObject("paging");
        return paging.getString("previous");

    }

    private String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        String JSONStr = null;
        final String TAG = DataParser.class.getSimpleName();
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            // invia la richiesta
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "Risposta: " + response);
            is = connection.getInputStream();
            //Converte InputStream in una stringa
            StringBuffer buffer = new StringBuffer();

            if (is == null)
                return null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0)
                return null;

            JSONStr = buffer.toString();
            Log.v(TAG, "JSON String :" + JSONStr);
        } catch (IOException e) {
            e.printStackTrace();
            //Assicuriamoci che l'InputStream venga chiuso
        } finally {
            if (is != null)
                is.close();
        }
        return JSONStr;
    }


    public Song[] getData() {
        Song[] result = new Song[0];
        String n = "new";
        try {
            if (result.length == 0) {
                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                       .appendPath(n)
                       .build();
                URL url = new URL(builtUri.toString());
                Log.v(TAG,"Built URI " + builtUri.toString());
                String recipeJsonStr = downloadUrl(builtUri.toString());
                result = getImageAndName(recipeJsonStr);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static  String getDurationString(String s) {
        int seconds = Integer.parseInt(s);
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    public static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
}


