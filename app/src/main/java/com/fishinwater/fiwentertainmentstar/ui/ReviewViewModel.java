package com.fishinwater.fiwentertainmentstar.ui;

import androidx.lifecycle.ViewModel;

import com.fishinwater.fiwentertainmentstar.ReviewDateSource;
import com.fishinwater.fiwentertainmentstar.persistance.Review;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class ReviewViewModel extends ViewModel {

    private final ReviewDateSource mDataSource;

    private Review review;

    public ReviewViewModel(ReviewDateSource dateSource) {
        this.mDataSource = dateSource;
    }

    public Flowable<List<Review>> getReviews() {
        return mDataSource.getReviews();
    }

    public Completable insertReview(Review review) {
//        review = review == null
//                ? new Review()
//                : new Review();
        return mDataSource.insertOrUpdateReview(review);
    }

    public Completable deleteReview(Review review) {
        return mDataSource.deleteReview(review);
    }
}
