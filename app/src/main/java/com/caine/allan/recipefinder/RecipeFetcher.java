package com.caine.allan.recipefinder;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allancaine on 2015-10-17.
 */
public class RecipeFetcher {

    private static final String TAG = "RecipeFetcher";

    private static final String API_KEY = "38a6bccce5ca111197b7b2fd50142388";

    private static final String END_POINT = "http://www.food2fork.com/api/search";

    public byte[] getUrlBytes(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);

        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;

            byte[] buffer = new byte[1024];

            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public List<RecipeItem> fetchItems(String query){

        List<RecipeItem> items = new ArrayList<>();

        if(query == null){
            return items;
        }

        try {
            String url = Uri.parse(END_POINT)
                    .buildUpon()
                    .appendQueryParameter("key", API_KEY)
                    .appendQueryParameter("q", query)
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);

            parseItems(items, jsonBody);

        }catch(JSONException je){
            Log.e(TAG, "Failed to parse JSON " + je);
        }catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        }

        Log.i(TAG, "Array size " + items.size());

        return items;
    }

    private void parseItems(List<RecipeItem> items, JSONObject jsonBody) throws IOException, JSONException{
        JSONArray recipeArray = jsonBody.getJSONArray("recipes");

        for(int i = 0; i < recipeArray.length(); i++){
            JSONObject recipe = recipeArray.getJSONObject(i);
            items.add(new RecipeItem(recipe));
        }
    }
}
