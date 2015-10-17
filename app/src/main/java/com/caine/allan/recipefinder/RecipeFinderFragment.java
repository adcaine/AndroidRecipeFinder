package com.caine.allan.recipefinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by allancaine on 2015-10-17.
 */
public class RecipeFinderFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static RecipeFinderFragment newInstance(){
        return new RecipeFinderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_finder, container, false);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recipe_finder_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
}
