package com.example.speedbreaker;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProviderDao {


        @Query("SELECT * FROM PROVIDER ORDER BY ID")
        List<Provider> loadAllProviders();

        @Insert
        void insertProvider(Provider provider);

        @Update
        void updateProvider(Provider provider);

        @Delete
        void delete(Provider provider);

        @Query("SELECT * FROM PROVIDER WHERE id = :id")
        Provider loadProviderById(int id);
}
