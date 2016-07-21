package com.gccdev.mixhearapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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


public class DataParser extends AsyncTask<String,Void,Void>{
    private final  String TAG = DataParser.class.getSimpleName();
    private static Context context;
    private View rootView;
    private static final String BASE_URL = "https://api.mixcloud.com";
    private static CustomAdapter adapter;
    private ListView listView;
    private static ProgressDialog progBar;


    private static final String TITLE = "name";
    private static final String IMAGE_URL = "pictures";
    private static final String DIM_MEDIUM = "medium_mobile" ;
    private static final String DIM_LARGE ="large";
    private static final String CREATED ="created_time";
    private static final String LENGTH ="audio_length";
    private static final String SLUG ="slug";
    private static final String URL = "url";
    private static final String PLAY_COUNT ="play_count";
    private static final String FAVORITE_COUNT = "favorite_count";
    private static final String LISTENER_COUNT = "listener_count";
    private static final String REPOST_COUNT = "repost_count";
    private static final String UPDATED_TIME = "updated_time";
    private static final String COMMENT_COUNT ="comment_count";

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
    protected Void doInBackground(String... params) {

        getData(params[0]);

        return null;


    }


    protected void onPostExecute() {
//        listView = (ListView)rootView.findViewById(R.id.listview);
//        adapter = new CustomAdapter(context);
//        listView.setAdapter(adapter);
//        adapter.setData(list);
//        adapter.notifyDataSetChanged();
//        progBar.dismiss();
//
//
    }



    public  static  void parseStr(String JSONStr) throws JSONException{


      //  List<Song> result = new ArrayList<>();
        List<String> urls = new ArrayList<>();

        JSONObject res = new JSONObject(JSONStr);
        JSONArray data = res.getJSONArray("data");

        ContentValues[] arrayValues = new ContentValues[data.length()];

        ContentResolver resolver = context.getContentResolver();

        resolver.delete(Contract.Songs.CONTENT_URI,null,null);

        for(int i =0;i<data.length();i++) {
            try {

                JSONObject songs = data.getJSONObject(i);
                String title = songs.getString(TITLE);
                String created_time = songs.getString(CREATED);

                String l = songs.optString(LENGTH);
                String length = getDurationString(l);
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

                urls.add(new String(imageUrlMedium));
                String imageUrlLarge = pic.getString(DIM_LARGE);

              //  Song p = new Song(title, imageUrlMedium);
               // result.add(p);

                arrayValues[i] = new ContentValues();
                arrayValues[i].put(Contract.Songs.COLUMN_ID,i);
                arrayValues[i].put(Contract.Songs.COLUMN_TITLE,title);
                arrayValues[i].put(Contract.Songs.COLUMN_LENGTH,length);
                arrayValues[i].put(Contract.Songs.COLUMN_PIC_LARGE,imageUrlLarge);



            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        resolver.bulkInsert(Contract.Songs.CONTENT_URI,arrayValues);
        progBar.dismiss();

      //adapter.setData(urls.toArray(new Song[result.size()]));
    }

//        public static String getPreviousPage(String JSONStr) throws JSONException{
//        JSONObject res = new JSONObject(JSONStr);
//        JSONObject paging = res.getJSONObject("paging");
//        return paging.getString("previous");
//
//    }

    private String downloadUrl(String myUrl) throws IOException {
        InputStream is = null;
        String JSONStr = null;
        final String TAG = DataParser.class.getSimpleName();
        try {
            URL url = new URL(myUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(20000);
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


    public void getData(String param) {
       // Song[] result = new Song[0];
        String n = param;
        try {

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                       .appendPath(n)
                       .build();
                URL url = new URL(builtUri.toString());
                Log.v(TAG,"Built URI " + builtUri.toString());
                String recipeJsonStr = downloadUrl(builtUri.toString());
                parseStr(recipeJsonStr);


        }catch(Exception e){
            e.printStackTrace();
        }
       // return result;
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


