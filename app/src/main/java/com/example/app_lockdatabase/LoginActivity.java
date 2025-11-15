package com.example.app_lockdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

/**
 * LoginActivity — экран авторизации пользователя.
 * Позволяет проверить, существует ли пользователь с указанными
 * логином и паролем в локальной базе данных SQLite.
 *
 * Логика работы:
 *  1. Пользователь вводит логин и пароль.
 *  2. При нажатии на кнопку "Войти" данные передаются в метод checkUser() DatabaseHelper.
 *  3. Если пользователь найден — выводится сообщение "Вход выполнен".
 *  4. Если логин или пароль неверные — показывается ошибка.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Поля ввода:
     * etUsername — поле для логина
     * etPassword — поле для пароля
     *
     * Кнопка:
     * btnLogin — подтверждение авторизации
     *
     * dbHelper — объект для работы с базой данных SQLite
     */
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    /**
     * Метод жизненного цикла Activity.
     * Вызывается при создании экрана авторизации.
     *
     * @param savedInstanceState — сохранённое состояние
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Подключаем разметку activity_login.xml
        setContentView(R.layout.activity_login);

        // Создаём экземпляр DatabaseHelper для работы с SQLite
        dbHelper = new DatabaseHelper(this);

        // Привязка полей ввода и кнопки к XML-элементам
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnDoLogin);

        /**
         * Обработчик нажатия на кнопку "Войти".
         * Срабатывает, когда пользователь нажимает кнопку.
         */
        btnLogin.setOnClickListener(v -> {

            // Получаем текст из полей ввода
            String u = etUsername.getText().toString().trim();
            String p = etPassword.getText().toString();

            // Проверка: поля не должны быть пустыми
            if (u.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            /**
             * Проверяем логин и пароль в базе данных:
             * dbHelper.checkUser(u, p)
             * возвращает true, если найдена строка в таблице users
             * с таким логином и паролем.
             */
            if (dbHelper.checkUser(u, p)) {
                // Успешный вход
                Toast.makeText(this, "Вход выполнен", Toast.LENGTH_LONG).show();

                // Закрываем Activity и возвращаемся на главный экран
                finish();
            } else {
                // Ошибка: пользователь не найден
                Toast.makeText(this, "Ошибка: неверные данные", Toast.LENGTH_LONG).show();
            }
        });
    }
}
