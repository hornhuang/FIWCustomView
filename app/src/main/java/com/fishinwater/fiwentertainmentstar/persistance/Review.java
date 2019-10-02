package com.fishinwater.fiwentertainmentstar.persistance;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

import io.reactivex.annotations.NonNull;

/**
 * 待复习表
 *
 * @author fishinwater-1999
 */
@Entity(tableName = "reviews")
public class Review {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mReviewId;

    @ColumnInfo(name = "movie")
    private String mMovieUrl;

    @ColumnInfo(name = "article")
    private String mAticleUrl;

    @ColumnInfo(name = "name")
    private String mReviewName;

    @ColumnInfo(name = "date")
    private Date mPublishDate;

    @Ignore
    public Review(String mMovieUrl, String mAticleUrl, String mReviewName, Date mPublishDate) {
        this.mReviewId = UUID.randomUUID().toString();
        this.mMovieUrl = mMovieUrl;
        this.mAticleUrl = mAticleUrl;
        this.mReviewName = mReviewName;
        this.mPublishDate = mPublishDate;
    }

    public Review(String mReviewId, String mMovieUrl, String mAticleUrl, String mReviewName, Date mPublishDate) {
        this.mReviewId = mReviewId;
        this.mMovieUrl = mMovieUrl;
        this.mAticleUrl = mAticleUrl;
        this.mReviewName = mReviewName;
        this.mPublishDate = mPublishDate;
    }

    public String getmReviewId() {
        return mReviewId;
    }

    public String getmMovieUrl() {
        return mMovieUrl;
    }

    public String getmAticleUrl() {
        return mAticleUrl;
    }

    public String getmReviewName() {
        return mReviewName;
    }

    public Date getmPublishDate() {
        return mPublishDate;
    }
}
