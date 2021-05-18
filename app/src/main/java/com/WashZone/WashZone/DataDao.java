package com.WashZone.WashZone;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM Data01")
    List<Data01> getAll();

    @Insert
    void insert(Data01 data01);

    @Update
    void update(Data01 data01);

    @Delete
    void delete(Data01 data01);
}
