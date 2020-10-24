package com.example.room_1.data;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.room.Entity;

import com.example.room_1.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;


// Repository  - нужен для структурирования доступа к данным, он передает данные из БД в ViewModel(и так liveData- observeble)
//https://www.youtube.com/watch?v=HhmA9S53XV8&list=PLrnPJCHvNZuAPyh6nRXsvf5hF48SJWdJb&index=4
public class MeRepository{

       private UserDao userDao;
        private LiveData<List<User>> mAllUser;
        
        public MeRepository(Application application) {
            AppDatabase database = AppDatabase.getDatabase(application);
            userDao = database.userDao();
            mAllUser = userDao.getAll();
        }

        //Создаем методы
        public LiveData<List<User>> getAllUser() { return mAllUser; }

        //прописываем наши методы в репозитории (и в UserViewModel)
        public void insert(User user){          //этот класс написан ниже
            new InsertUserAsyncTask(userDao).execute(user);
        }
        //точно нак же и другие методы делаем
       //update у нас пока в dao нет!
//        public void updete(User user){}
//
//        // внимательно с delete там будет чуть по другому так как на экран выведится иначе строки
    public void delete(User user){
            new DeleteNoteAsyncTask(userDao).execute(user); } //прописываем в репозитории (и в UserViewModel)
    public void deleteAll(){
        new DeleteAllNoteAsyncTask(userDao).execute();
    }





    //НЕ ПОЙМУ ТОЛИ ТУТ insert (тут не работают) ТО ли В APPDATABASE
    //    AsyncTask создает фоновый поток и выполняет в нем код, содержащийся в методе doInBackground(…).
    //это для Insert
private static class InsertUserAsyncTask extends AsyncTask<User,Void,Void>{
            private UserDao userDao;
            private InsertUserAsyncTask(UserDao userDao){
                this.userDao=userDao;
            }
            @Override
            protected Void doInBackground(User... user){
//             userDao.insert(new User(1,"firstname","lastname")); //эти инсерты не работают!
                userDao.insert(user[0]);
                return null;
            }
    }

//            //это для Delete
private static class DeleteNoteAsyncTask extends AsyncTask<User,Void,Void>{
                private UserDao userDao;
                private DeleteNoteAsyncTask(UserDao userDao){
                    this.userDao=userDao;
                }
                @Override
                protected Void doInBackground(User... user){
                    userDao.delete(user[0]);
                    return null;
                }
             }

        //это для DeleteAll чуть иначе
        private static class DeleteAllNoteAsyncTask extends AsyncTask<Void,Void,Void>{
            private UserDao userDao;
            private DeleteAllNoteAsyncTask(UserDao userDao){
                this.userDao=userDao;
            }
            @Override
            protected Void doInBackground(Void... voids){
                userDao.deleteAll();
                return null;
            }
        }


}
