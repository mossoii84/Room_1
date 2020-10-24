package com.example.room_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddUserActivity extends AppCompatActivity {

    // Тест добавления User
    private TextInputEditText add_lastname;
    private TextInputEditText add_firstname;
    private Button buttonInput;
    //Создали сами строковую константу EXTRA_REPLY в качестве ключа и переменную word в качестве значения.
    //Это установит EXTRA_REPLY на word в пределах вашего Intent, чтобы вы могли прочитать его во всем, что Activity обрабатывает ваш результат.
    //Хорошей практикой является определение ключей для ваших намерений как констант,
    // чтобы вы с меньшей вероятностью допустили ошибки при обращении к ним в других классах.
    //Это мы делаем если у нас есть сторое Активити или фрагмент
    public static final String EXTRA_Firstname = "com.codingflow.architecturexample.add_firstname";
    public static final String EXTRA_Lastname = "com.example.android.wordlistsql.add_lastName";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setTitle("Add User");
        //кнопка возврата "назад" к странице родителя
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        metodAddUser();
    }




  // Тест добавления user   При втором Активити или фрагменте
    public void metodAddUser(){
        add_firstname = findViewById(R.id.add_firstName);
        add_lastname = findViewById(R.id.add_lastName);

        buttonInput = findViewById(R.id.addButton);
        buttonInput.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
 //   Намерение (Intent) - это механизм для описания одной операции - выбрать фотографию, отправить письмо, сделать звонок,
 //   запустить браузер и перейти по указанному адресу. В Android-приложениях многие операции работают через намерения.
 //   Наиболее распространённый сценарий использования намерения - запуск другой активности в своём приложении.
            Intent myData = new Intent(); // так делают если есть вторая стр или фрагмент
//                if (TextUtils.isEmpty(input_lastname.getEditText())) {  //TextUtils  - стандартный класс
//                    setResult(RESULT_CANCELED, replyIntent); // RESULT_CANCELED - стандартная штука
//                } else {

            String firstname =  add_firstname.getText().toString();
            String lastname = add_lastname.getText().toString();

            myData.putExtra(EXTRA_Firstname, firstname);
            myData.putExtra(EXTRA_Lastname, lastname);
            setResult(RESULT_OK, myData);  //RESULT_OK - стандартная штука, есть еще RESULT_CANCELED и тд

            Toast.makeText(AddUserActivity.this, "myData", Toast.LENGTH_SHORT).show();
                 finish(); // закрваем нашу всплывающую(вторую активити) и редиректит на первую активити
        }
    });
    }















}