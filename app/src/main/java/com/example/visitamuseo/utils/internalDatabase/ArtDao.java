package com.example.visitamuseo.utils.internalDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.visitamuseo.model.Art;
import com.example.visitamuseo.model.Exhibition;
import com.example.visitamuseo.model.SliderViewInterface;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ArtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArt(Art... art);

    @Query("SELECT * FROM Art WHERE Art.nameExhibition=:exhibitionName")
    List<Art> getArts(String exhibitionName);

    @Query("DELETE FROM Art")
    void delete();
}
