package com.example.app_lockdatabase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;

/**
 * Главная Activity приложения.
 * Показывает два действия:
 *  - переход на экран авторизации (LoginActivity)
 *  - переход на экран регистрации (SignupActivity)
 *
 * Данная Activity является стартовой точкой приложения
 * (запускается первой благодаря настройкам в AndroidManifest).
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Кнопки интерфейса:
     * btnLogin  — открывает форму входа
     * btnSignup — открывает форму регистрации
     */
    private Button btnLogin, btnSignup;

    /**
     * Метод жизненного цикла Activity.
     * Вызывается при создании экрана.
     *
     * @param savedInstanceState — сохранённое состояние (если приложение пересоздаётся).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Подключаем layout разметку activity_main.xml
        setContentView(R.layout.activity_main);

        // Инициализация кнопок через их ID из XML
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignUp);

        /**
         * Обработчик нажатия на кнопку "Войти".
         * Переходит в LoginActivity для проверки логина и пароля.
         */
        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, LoginActivity.class))
        );

        /**
         * Обработчик нажатия на кнопку "Регистрация".
         * Переходит в SignupActivity для создания аккаунта нового пользователя.
         */
        btnSignup.setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class))
        );
    }
}
