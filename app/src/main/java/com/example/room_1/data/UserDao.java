package com.example.room_1.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

    @Dao
    public interface UserDao {

        // Получение всех user из бд
        @Query("SELECT * FROM user")
        LiveData<List<User>> getAll();

        @Query("SELECT * FROM user WHERE id IN (:userIds)")
        List<User> loadAllByIds(int[] userIds);

        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        User findByName(String first, String last);

        // Добавление User в бд
        @Insert
        void insert(User user);
        @Insert
        void insertAll(User users);

        @Update
        void update(User user);

        @Delete
        void delete(User user);
        // методы которые не сушествуют в стандарном варианте(стандартные = которые тут без @Query написаны)
        // мы пишем сами в SQL через @Query
        @Query("DELETE FROM User")
        void deleteAll();


    }
