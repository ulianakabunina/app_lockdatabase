package com.example.app_lockdatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Класс-помощник для работы с SQLite-базой данных.
 * Содержит методы создания, обновления базы и операции с таблицей пользователей.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Название базы данных.
     * Используется final, чтобы значение было неизменяемым.
     * Используется private для инкапсуляции — чтобы другие классы не могли изменить константу.
     */
    private static final String DATABASE_NAME = "UserDB";

    /**
     * Версия базы данных.
     * При изменении структуры таблиц – увеличиваем версию.
     * Вызовет onUpgrade().
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Название таблицы для хранения пользователей.
     */
    private static final String TABLE_USERS = "users";

    /**
     * Названия столбцов таблицы.
     */
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    /**
     * Строка SQL для создания таблицы users.
     * Выполняется один раз при первом создании базы.
     */
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT" +
                    ")";

    /**
     * Конструктор DatabaseHelper.
     * @param context Контекст приложения.
     *
     * Вместо параметра context можно передавать:
     * - this (в Activity)
     * - MainActivity.this
     * - getApplicationContext()
     * - requireContext() (в Fragment)
     *
     * Контекст нужен SQLiteOpenHelper для доступа к файловой системе, где хранится БД.
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Вызывается при первом создании базы данных.
     * @param db Объект SQLiteDatabase (физическая база данных).
     *
     * Сюда отправляется SQL-команда создания таблиц.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаём таблицу users
        db.execSQL(CREATE_TABLE);
    }

    /**
     * Вызывается при обновлении версии базы данных (увеличении DATABASE_VERSION).
     *
     * @param db Объект базы данных.
     * @param oldVersion Старая версия базы.
     * @param newVersion Новая версия базы.
     *
     * Если структура таблицы изменилась — старая таблица удаляется,
     * затем создаётся заново.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Удаляем таблицу, если она существует
        // DROP TABLE IF EXISTS users;
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Создаём таблицу заново
        onCreate(db);
    }

    /**
     * Добавляет пользователя в базу данных.
     *
     * @param username — логин пользователя
     * @param password — пароль пользователя
     *
     * @return long — ID вставленной записи.
     *         Вернёт -1 если вставка не удалась.
     */
    public long addUser(String username, String password) {

        // Получаем базу данных для записи
        SQLiteDatabase db = this.getWritableDatabase();

        // Объект для хранения значений столбцов
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);   // логин
        values.put(COLUMN_PASSWORD, password);   // пароль

        // Вставляем запись и возвращаем её ID
        return db.insert(TABLE_USERS, null, values);
    }

    /**
     * Проверяет, существует ли пользователь с указанным логином и паролем.
     *
     * @param username — введённый логин
     * @param password — введённый пароль
     *
     * @return true — если пользователь найден
     *         false — если ни одной строки не найдено
     */
    public boolean checkUser(String username, String password) {

        // Получаем доступ к базе данных для чтения
        SQLiteDatabase db = this.getReadableDatabase();

        // Какие столбцы будем получать из выборки
        String[] columns = { COLUMN_ID };

        // Создаём условие WHERE
        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";

        // Значения для подстановки в ?
        String[] selectionArgs = { username, password };

        /**
         * Выполняемый SQL-запрос эквивалентен:
         *
         * SELECT id
         * FROM users
         * WHERE username = 'user' AND password = '1234';
         */
        Cursor cursor = db.query(
                TABLE_USERS,    // таблица
                columns,        // какие столбцы возвращать
                selection,      // условие WHERE
                selectionArgs,  // аргументы
                null,           // GROUP BY
                null,           // HAVING
                null            // ORDER BY
        );

        // Сколько строк удовлетворяют условию
        int count = cursor.getCount();

        cursor.close();

        // Если count = 0 → пользователь не найден → вернёт false
        return count > 0;
    }

    /**
     * Проверяет успешность добавления пользователя.
     *
     * @return true — если addUser() вернул НЕ -1
     */
    public boolean isUserAddedSuccessfully(String username, String password) {
        return addUser(username, password) != -1;
    }
}

