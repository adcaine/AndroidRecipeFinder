package com.caine.allan.recipefinder;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by allancaine on 2015-10-17.
 */
public class RecipeItem {

    public static final String TAG = "RecipeItem";

    private String mPublisher;
    private String mf2fURL;
    private String mTitle;
    private String mSourceURL;
    private String mRecipeId;
    private String mImageURL;
    private double mSocialRank;
    private String mPublisherURL;

    public RecipeItem(JSONObject jsonObject){
        try {
            mPublisher = jsonObject.getString("publisher");
            mf2fURL = jsonObject.getString("f2f_url");
            mTitle = jsonObject.getString("title");
            mSourceURL = jsonObject.getString("source_url");
            mRecipeId = jsonObject.getString("recipe_id");
            mImageURL = jsonObject.getString("image_url");
            mSocialRank = jsonObject.getDouble("social_rank");
            mPublisherURL = jsonObject.getString("publisher_url");
        }catch (JSONException je){
            Log.e(TAG, "Could not read JSON object");
        }
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getMf2fURL() {
        return mf2fURL;
    }

    public void setMf2fURL(String mf2fURL) {
        this.mf2fURL = mf2fURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSourceURL() {
        return mSourceURL;
    }

    public void setSourceURL(String sourceURL) {
        mSourceURL = sourceURL;
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(String recipeId) {
        mRecipeId = recipeId;
    }

    public String getImageURL() {
        return mImageURL;
    }

    public void setImageURL(String imageURL) {
        mImageURL = imageURL;
    }

    public double getSocialRank() {
        return mSocialRank;
    }

    public void setSocialRank(double socialRank) {
        mSocialRank = socialRank;
    }

    public String getPublisherURL() {
        return mPublisherURL;
    }

    public void setPublisherURL(String publisherURL) {
        mPublisherURL = publisherURL;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}
