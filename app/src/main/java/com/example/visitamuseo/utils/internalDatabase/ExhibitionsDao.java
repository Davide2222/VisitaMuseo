package com.example.visitamuseo.utils.internalDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.model.SliderViewInterface;
import com.example.visitamuseo.model.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ExhibitionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExhibition(Exhibition... exhibition);

    @Query("SELECT * FROM Exhibition")
    List<Exhibition> getAll();

    @Query("DELETE FROM Exhibition")
    void delete();
}
