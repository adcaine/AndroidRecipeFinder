package com.caine.allan.recipefinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by allancaine on 2015-10-17.
 */
public class RecipeFinderFragment extends Fragment {

    private static final String TAG = "RecipeFinderFragment";

    private RecyclerView mRecyclerView;

    private List<RecipeItem> mRecipeItems = new ArrayList<>();

    public static RecipeFinderFragment newInstance(){
        return new RecipeFinderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchRecipesTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_finder, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recipe_finder_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setupAdapter();
        return v;
    }

    private void setupAdapter(){
        if(isAdded()){
            mRecyclerView.setAdapter(new RecipeAdapter(mRecipeItems));
        }
    }

    private class RecipeHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public RecipeHolder(View itemView) {
            super(itemView);

            mTextView = (TextView)itemView;

        }

        public void bindRecipe(RecipeItem item){
            mTextView.setText(item.toString());
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{

        private List<RecipeItem> mItems;

        public RecipeAdapter(List<RecipeItem> items){
            mItems = items;
        }

        @Override
        public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecipeHolder(new TextView(getActivity()));
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position) {
            RecipeItem item = mItems.get(position);
            holder.bindRecipe(item);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }



    private class FetchRecipesTask extends AsyncTask<Void, Void, List<RecipeItem> >{

        @Override
        protected List<RecipeItem> doInBackground(Void... params) {

            return new RecipeFetcher().fetchItems();

        }

        @Override
        protected void onPostExecute(List<RecipeItem> items) {
            mRecipeItems = items;
            setupAdapter();
        }
    }
}
