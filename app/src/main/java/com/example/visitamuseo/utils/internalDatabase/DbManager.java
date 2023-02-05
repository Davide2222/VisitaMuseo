package com.example.visitamuseo.utils.internalDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version  = 2)
public abstract class DbManager extends RoomDatabase {

    public abstract UserDao userDao();

    private static DbManager INSTANCE;

    public static DbManager getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DbManager.class, "User")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
