package com.caine.allan.recipefinder;

import android.support.v4.app.Fragment;

/**
 * Created by allancaine on 2015-10-17.
 */
public class RecipeFinderActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return RecipeFinderFragment.newInstance();
    }
}
