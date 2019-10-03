package com.fishinwater.fiwentertainmentstar;

import com.fishinwater.fiwentertainmentstar.persistance.Review;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface ReviewDateSource {

    Flowable<List<Review>> getReviews();

    Completable insertOrUpdateReview(Review review);

    Completable deleteReview(Review review);

}
