package com.WashZone.WashZone;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Data01.class}, version =  1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataDao DataDao();

}
