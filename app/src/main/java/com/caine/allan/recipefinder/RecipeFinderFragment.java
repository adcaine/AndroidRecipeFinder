package com.caine.allan.recipefinder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        updateItems();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.recipe_finder_fragment, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_item_search);

        final SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                QueryPreferences.setStoredQuery(getActivity(),query);
                updateItems();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_item_clear:
                QueryPreferences.setStoredQuery(getActivity(), null);
                updateItems();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateItems() {
        String query = QueryPreferences.getStoredQuery(getActivity());
        new FetchRecipesTask().execute(query);
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



    private class FetchRecipesTask extends AsyncTask<String, Void, List<RecipeItem> >{

        @Override
        protected List<RecipeItem> doInBackground(String... params) {

            return new RecipeFetcher().fetchItems(params[0]);

        }

        @Override
        protected void onPostExecute(List<RecipeItem> items) {
            mRecipeItems = items;
            setupAdapter();
        }
    }
}
