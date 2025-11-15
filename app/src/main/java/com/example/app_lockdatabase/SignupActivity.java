package com.example.app_lockdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

/**
 * SignupActivity — экран регистрации нового пользователя.
 *
 * Логика работы:
 *  1. Пользователь вводит логин и пароль.
 *  2. При нажатии на кнопку "Зарегистрироваться" вызывается метод addUser().
 *  3. Если логин уникален — пользователь добавляется в SQLite и выводится сообщение об успехе.
 *  4. Если логин уже существует — выводится ошибка.
 *
 * Данные сохраняются в локальной базе данных SQLite в таблице users.
 */
public class SignupActivity extends AppCompatActivity {

    /**
     * Элементы интерфейса:
     * etUsername — поле ввода логина
     * etPassword — поле ввода пароля
     * btnSignup  — кнопка регистрации
     * dbHelper — объект для работы с SQLite
     */
    private EditText etUsername, etPassword;
    private Button btnSignup;
    private DatabaseHelper dbHelper;

    /**
     * Метод жизненного цикла Activity.
     * Вызывается при создании экрана регистрации.
     *
     * @param savedInstanceState — сохранённые данные (если Activity пересоздаётся)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Загружаем XML-разметку activity_signup.xml
        setContentView(R.layout.activity_signup);

        // Создаём экземпляр DatabaseHelper для работы с локальной БД
        dbHelper = new DatabaseHelper(this);

        // Привязка элементов интерфейса к XML
        etUsername = findViewById(R.id.etSignupUsername);
        etPassword = findViewById(R.id.etSignupPassword);
        btnSignup = findViewById(R.id.btnDoSignup);

        /**
         * Обработчик нажатия на кнопку "Зарегистрироваться".
         * Выполняет регистрацию пользователя.
         */
        btnSignup.setOnClickListener(v -> {

            // Считываем значения из полей ввода
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString();

            // Проверяем, чтобы поля были заполнены
            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
                return;
            }

            // Пытаемся добавить пользователя в базу
            long id = dbHelper.addUser(u, p);

            /**
             * Метод addUser() возвращает:
             *  - ID новой записи (0,1,2,3...) — если успешно
             *  - -1 — если логин уже существует
             */
            if (id != -1) {
                Toast.makeText(this, "Регистрация успешна! ID = " + id, Toast.LENGTH_LONG).show();

                // Закрываем Activity после успешной регистрации
                finish();
            } else {
                // Ошибка — логин уже есть в таблице users
                Toast.makeText(this, "Логин уже занят", Toast.LENGTH_LONG).show();
            }
        });
    }
}
