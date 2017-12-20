package com.example.blalonde9489.projectapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addItem(Inventory item);

    @Query("SELECT * FROM inventory WHERE userId=:userId")
    List<Inventory> findItems(int userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateInventory(Inventory item);

    @Query("delete from inventory where id = :id")
    void delete(long id);

}