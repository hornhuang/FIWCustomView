package com.fishinwater.fiwentertainmentstar.persistance;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.sql.Date;
import java.util.UUID;

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
    private String mId;

    @ColumnInfo(name = "movie")
    private String mMovie;

    @ColumnInfo(name = "article")
    private String mArticle;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "date")
    private String mDate;

    @Ignore
    public Review(String mMovie, String mArticle, String mName, String mDate) {
        this.mId    = UUID.randomUUID().toString();
        this.mMovie = mMovie;
        this.mArticle = mArticle;
        this.mName = mName;
        this.mDate = mDate;
    }

    public Review(@NonNull String mId, String mMovie, String mArticle, String mName, String mDate) {
        this.mId = mId;
        this.mMovie = mMovie;
        this.mArticle = mArticle;
        this.mName = mName;
        this.mDate = mDate;
    }

    public String getId() {
        return mId;
    }

    public String getMovie() {
        return mMovie;
    }

    public String getArticle() {
        return mArticle;
    }

    public String getName() {
        return mName;
    }

    public String getDate() {
        return mDate;
    }

}
