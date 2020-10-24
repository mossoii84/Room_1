package com.example.room_1.data;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


//если я правильно понял то как раз ViewModel сохроняет состояние
//https://www.youtube.com/watch?v=JLwW5HivZg4&list=PLrnPJCHvNZuAPyh6nRXsvf5hF48SJWdJb&index=5
public class UserViewModel extends AndroidViewModel {

    //импортируем наш репозитори и LiveData- User
    private MeRepository meRepository;
    private LiveData<List<User>> mAllUser;

    //Application - ????
    public UserViewModel(@NonNull Application application) {
        super(application);
        meRepository = new MeRepository(application);
        mAllUser = meRepository.getAllUser();
    }

    //наши методы из Repository запускаем на выполнениe(методы в репозитории стали цветными)
    public void insert(User user){ meRepository.insert(user); }
    public void delete(User user){ meRepository.delete(user); }
//    public void deleteAll(User user){ meRepository.deleteAll(); }
    public LiveData<List<User>> getAllUser() { return mAllUser; }



}
