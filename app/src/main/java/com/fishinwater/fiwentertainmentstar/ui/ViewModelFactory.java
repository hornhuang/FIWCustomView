package com.fishinwater.fiwentertainmentstar.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.fishinwater.fiwentertainmentstar.ReviewDateSource;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ReviewDateSource mDataSource;

    public ViewModelFactory(ReviewDateSource dateSource) {
        this.mDataSource = dateSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReviewViewModel.class)) {
            return (T) new ReviewViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ReviewViewModel class");
    }
}
