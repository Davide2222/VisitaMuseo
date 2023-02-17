package com.example.visitamuseo.utils.internalDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.visitamuseo.model.User;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User... users);

    @Query("SELECT COUNT(*) FROM User")
    int numberLoggedUser();

    @Query("SELECT password FROM User")
    String nickname();

    @Query("SELECT type FROM User")
    String type();

    @Query("UPDATE User SET password = :newPassword")
    void updatePassword(String newPassword);

    @Query("DELETE FROM User")
    void delete();
}
