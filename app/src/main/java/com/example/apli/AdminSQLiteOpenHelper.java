package com.example.apli;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    private static final int DATABE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "aula.db";
    public static final String TABLE_ALUMNOS = "t_alumnos";
    public static final String TABLE_TUTORES = "t_tutores";

    public AdminSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_ALUMNOS+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "apellido2 TEXT NOT NULL," +
                "dni TEXT NOT NULL," +
                "observaciones TEXT NOT NULL," +
                "idTutor TEXT NOT NULL," +
                "nula INTEGER NOT NULL," +
                "parcial INTEGER NOT NULL," +
                "total INTEGER NOT NULL" +
                ")");
        db.execSQL("CREATE TABLE "+TABLE_TUTORES+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "apellido2 TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "telefono TEXT NOT NULL" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ALUMNOS);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TUTORES);
        onCreate(db);
    }
}
