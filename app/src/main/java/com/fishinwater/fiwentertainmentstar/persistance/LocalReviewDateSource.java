package com.fishinwater.fiwentertainmentstar.persistance;

import com.fishinwater.fiwentertainmentstar.ReviewDateSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalReviewDateSource implements ReviewDateSource {

    private final ReviewDao mReviewDao;

    public LocalReviewDateSource(ReviewDao reviewDao) {
        this.mReviewDao = reviewDao;
    }

    @Override
    public Flowable<List<Review>> getReviews() {
        return mReviewDao.getReviews();
    }

    @Override
    public Completable insertOrUpdateReview(Review review) {
        return mReviewDao.insertReview(review);
    }

    @Override
    public Completable deleteReview(Review review) {
        return mReviewDao.deleteReview(review);
    }
}
