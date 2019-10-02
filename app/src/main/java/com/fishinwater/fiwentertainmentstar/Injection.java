package com.fishinwater.fiwentertainmentstar;

import android.content.Context;

import com.fishinwater.fiwentertainmentstar.persistance.LocalReviewDateSource;
import com.fishinwater.fiwentertainmentstar.persistance.ReViewDatabase;
import com.fishinwater.fiwentertainmentstar.ui.ViewModelFactory;

public class Injection {

    public static ReviewDateSource provideReviewDataSource(Context context) {
        ReViewDatabase database = ReViewDatabase.getInstance(context);
        return new LocalReviewDateSource(database.reviewDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ReviewDateSource dateSource = provideReviewDataSource(context);
        return new ViewModelFactory(dateSource);
    }

}
