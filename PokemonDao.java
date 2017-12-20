package com.example.blalonde9489.projectapp;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPokemon(Pokemon trophy);

    @Query("SELECT * FROM pokemon WHERE userId=:userId")
    List<Pokemon> findPokemonForUser(int userId);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTrophy(Pokemon trophy);

    @Query("delete from pokemon where id = :id")
    void delete(long id);

}