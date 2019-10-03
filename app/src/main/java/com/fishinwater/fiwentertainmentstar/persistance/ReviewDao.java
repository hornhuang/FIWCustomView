package com.fishinwater.fiwentertainmentstar.persistance;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * @author fishinwater-1999
 */
@Dao
public interface ReviewDao {

    @Query("SELECT * FROM reviews")
    Flowable<List<Review>> getReviews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertReview(Review review);

    @Delete()
    Completable deleteReview(Review review);
}
