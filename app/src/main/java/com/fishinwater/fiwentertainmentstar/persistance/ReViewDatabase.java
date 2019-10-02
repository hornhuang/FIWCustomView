package com.fishinwater.fiwentertainmentstar.persistance;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Review.class}, version = 1, exportSchema = false)
public abstract class ReViewDatabase extends RoomDatabase {

    private static volatile ReViewDatabase INSTANCE;

    public abstract ReviewDao reviewDao();

    public static ReViewDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ReViewDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReViewDatabase.class, "entertainmentstar.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
