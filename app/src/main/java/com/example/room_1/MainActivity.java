package com.example.room_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.room_1.adapterRecycler.OnItemClickListener;
import com.example.room_1.adapterRecycler.RoomAdapter;
import com.example.room_1.data.User;
import com.example.room_1.data.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    // Для RecyclerView
    private ArrayList<User> users = new ArrayList<>();
    private RecyclerView recyclerView;
    private RoomAdapter  roomAdapter;

    //импортируем наш ViewModel - типо @Autowarid в сприг
    private UserViewModel userViewModel;

    private static final int Add_Users_Activity = 1;
    private static final int Edit_Users_Activity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //переходим по нажатию на вторую странцу для Add User,
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //в интент пишем наш Активити и какой запустить второй
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                //startActivityForResult- означает что в первом активити ты вызываешь второе активити, и его результат
                //setResult(RESULT_OK, myData); =RESULT_OK - просто означает что операция законсилась успешно, а  вот
                // = myData передает из второго активити какие то данные
                startActivityForResult(intent, Add_Users_Activity); //этот метод реализуем ниже
            }
        });


        // делаем добавление данных insert
//        addSave();

        //подключаем наш recyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // создаем адаптер
        roomAdapter = new RoomAdapter(this); //this - появилось после listener
        // устанавливаем для списка адаптер
        recyclerView.setAdapter(roomAdapter);
        roomAdapter.updateList(users);


        // ROOM
        // Получаем наш ViewModel и прописываем как он работает через ViewModelProvider
        //ViewModelProvider - нужен потому что через него можно вызвать Observeble (LiveData прописана в файле UserViewModel)
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                roomAdapter.updateList(users); //тут мы можем написать любой метод в адаптере (не стандартный, сами создаем),например setAllUser
                // и вызвать его с notifyDataSetChanged(); чтобы было обновление
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
            }
        });

        //ПЕРЕТАСКИВАНИЕ для удаления
        //удаление items c помошью перетаскивания (строки card из recyclerView)
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);
    }
    //ПЕРЕТАСКИВАНИЕ обьектов (за область экрана для удаления)
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback
            (0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false; }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            //тут вызывается метод delete из репозитория (который так же прописан и в userViewModel )
            // Суть в том что нам нужно удалить строку из нашего userViewModel и соответственно через репозиторий из БД

                 //тоже самая запись, только более нагрядно для понимания!
                int myPosition = viewHolder.getAdapterPosition();
                RoomAdapter adapter = (RoomAdapter) recyclerView.getAdapter();
                userViewModel.delete(adapter.getUserPosition(myPosition));
                    //тоже самое только в одну строку
            //  userViewModel.delete(roomAdapter.getUserPosition(viewHolder.getAdapterPosition()));

            Toast.makeText(MainActivity.this, "Строка Удалена", Toast.LENGTH_SHORT).show();
        }
    };



    public void onActivityResult(int requestCode, int resultCode, Intent myData) {
        super.onActivityResult(requestCode, resultCode, myData);
        if (requestCode == Add_Users_Activity && resultCode == RESULT_OK) {
            String firstname = myData.getStringExtra(AddUserActivity.EXTRA_Firstname);
            String lastname = myData.getStringExtra(AddUserActivity.EXTRA_Lastname);
            User user = new User(firstname,lastname);
            userViewModel.insert(user);
            Toast.makeText(MainActivity.this, "Сохранено в БД", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "Не Сохранено", Toast.LENGTH_LONG).show();
        }
    }


        // При клике на строку(item) RecyclerView
        @Override
        public void onItemClick(User user) {
            Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
            intent.putExtra(AddUserActivity.EXTRA_Firstname, user.getFirstName());
            intent.putExtra(AddUserActivity.EXTRA_Lastname, user.getLastName());
            startActivityForResult(intent, Edit_Users_Activity);

            Toast.makeText(MainActivity.this, user.getFirstName(), Toast.LENGTH_SHORT).show();
        }








}









   //  добавляем данные в главном Активити (ниже вариант если есть второе активити или фрагмент он более используемый!!!)
//private void addSave(){
    //Работет но с удалением пробелемы
//            input_firstname = findViewById(R.id.input_firstName); //в главном активити
//            input_lastname = findViewById(R.id.input_lastName);
//
//        buttonInput = findViewById(R.id.insertButton);
//        buttonInput.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View view) {
//            final String firstname =  input_firstname.getEditText().getText().toString();
//            final String lastname =  input_lastname.getEditText().getText().toString();
//            users.add(new User(firstname,lastname));
//}










//  // Тест добавления user   При втором Активити или фрагменте
//    private void metodAddUser(){
//     final String lastname = input_lastname.getEditText().getText().toString();
//     final String firstname =  input_firstname.getEditText().toString();
//            buttonInput = findViewById(R.id.insertButton);
//
//        buttonInput.setOnClickListener(new View.OnClickListener() {
//        public void onClick(View view) {
//
// //   Намерение (Intent) - это механизм для описания одной операции - выбрать фотографию, отправить письмо, сделать звонок,
// //   запустить браузер и перейти по указанному адресу. В Android-приложениях многие операции работают через намерения.
// //   Наиболее распространённый сценарий использования намерения - запуск другой активности в своём приложении.
//            Intent myData = new Intent(); // так делают если есть вторая стр или фрагмент
////                if (TextUtils.isEmpty(input_lastname.getEditText())) {  //TextUtils  - стандартный класс
////                    setResult(RESULT_CANCELED, replyIntent); // RESULT_CANCELED - стандартная штука
////                } else {
//            myData.putExtra(EXTRA_Lastname, lastname);
//            myData.putExtra(EXTRA_Firstname, firstname);
//            setResult(RESULT_OK, myData);  //RESULT_OK - стандартная штука, есть еще RESULT_CANCELED и тд
//
//            Toast.makeText(MainActivity.this, "Добавил", Toast.LENGTH_SHORT).show();


//                }
//            finish(); // закрваем нашу всплывающую(вторую активити) активити
//        }
//    });
//}






