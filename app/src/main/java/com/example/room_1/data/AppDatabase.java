package com.example.room_1.data;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.google.android.material.textfield.TextInputEditText;

//Создание базы данных - @Database
//в entities - пишем все наши entity классы которые существуют
// https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7
//exportSchema = false - это не используем миграцию
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    //я так понимаю это нужно чтобы создать обьект - db, который ниже
    //Используется паттерн синглтон для одного экземпляра класса базы данных-???
    //Чтобы избежать создания нескольких экземпляров AppDatabase нужно использовать
    // паттерн singleton используя companion object
    private static volatile AppDatabase INSTANCE;

    //получаем доступ к БД, тут указываем все наши классы c Dao
    public abstract UserDao userDao();

    // Чтобы использовать свою базу данных, вам нужно будет создать ее экземпляр в своем приложении.
    static AppDatabase getDatabase(final Context context){
        if(INSTANCE ==null){
            synchronized (AppDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"my_database")
                            .fallbackToDestructiveMigration() //миграция нужна для пересоздания Бд тоже?!
                            .addCallback(roomcallback) //добавили созданый нами классы ниже
                            .build();
                } } }
        return INSTANCE;
    }





    //Этот код НЕ ОТНОСИТСЯ к коду из Репозитория!! это нужно для настроект при запуске программы



//    Чтобы удалить весь контент и повторно заполнить базу данных при запуске приложения, вы создаете
//    RoomDatabase.Callbackи переопределяете onOpen() метод.
  //  https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#12
//    Примечание. Если вы хотите заполнить базу данных только при первом запуске приложения,
//    вы можете переопределить onCreate()метод в RoomDatabase.Callback.


      //после прописания всех методов в Repository создаем эти классы
    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback(){
           @Override
           public void onOpen (@NonNull SupportSQLiteDatabase db){
               super.onOpen(db); // или можно onCreate() = super.onCreate(db);
               new PopulateOpenAsyncTask(INSTANCE).execute();
           }
      };

    ////    AsyncTask создает фоновый поток и выполняет в нем код, содержащийся в методе doInBackground(…).
    // метод onOpen (данный метод который запускается выше) работает хорошо, но постоянное переписывание БД возможно не очонь корректно
   // хотя это как сервер и не будет запускаться каждый раз как я запускаю
    private static class PopulateOpenAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDao userDao;
        private PopulateOpenAsyncTask(AppDatabase db){
            userDao = db.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids){
            userDao.deleteAll(); //удаляем все через наш метод Dao и пишем сново через insert
//            userDao.insert(new User("Nina","Ivanova"));  //эти инсерты работают!
            return null;
        }
    }





}
