package com.example.visitamuseo.utils.internalDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.model.User;

@Database(entities = {User.class, Exhibition.class, Art.class}, version  = 3)
public abstract class DbManager extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract ExhibitionsDao exhibitionsDao();
    public abstract ArtDao artDao();

    private static DbManager INSTANCE;

    public static DbManager getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DbManager.class, "Database")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
