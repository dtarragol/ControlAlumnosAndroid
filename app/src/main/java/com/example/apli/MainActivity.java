package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;
import static com.example.apli.AdminSQLiteOpenHelper.TABLE_TUTORES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void MenuCrearTutor(View view){
        Toast.makeText(this, "Menu - Inscribir tutor.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_CrearTutores.class);
        startActivity(intent);
        finish();
    }
    public void MenuCrearAlumno(View view){
        Toast.makeText(this, "Menu - Inscribir alumno.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_CrearAlumnos.class);
        startActivity(intent);
        finish();
    }
    public void MenuVerAlumno(View view){
        Toast.makeText(this, "Menu - Listado de alumnos.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_VerAlumnos.class);
        startActivity(intent);
        finish();
    }
    public void MenuModificarTutor(View view){
        Toast.makeText(this, "Menu - Moficiar/borrar tutor.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_ModificarTutor.class);
        startActivity(intent);
        finish();
    }
    public void MenuModificarAlumno(View view){
        Toast.makeText(this, "Menu - Modificar/borrar alumno.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_ModificarAlumno.class);
        startActivity(intent);
        finish();
    }
    public void MenuListar(View view){
        Toast.makeText(this, "Menu - Pasar lista diaria.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_PasarLista.class);
        startActivity(intent);
        finish();
    }
    public void MenuHistorial(View view){
        Toast.makeText(this, "Menu - Ver historial asistencias.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity2_HistorialAsistencias.class);
        startActivity(intent);
        finish();
    }
    public void BorrarDatos(View view){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(TABLE_TUTORES, null, null);
        database.delete(TABLE_ALUMNOS, null, null);
        //database.delete(TABLE_ASISTENCIAS, null, null);
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TUTORES+"'");
        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_ALUMNOS+"'");
        //database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_ASISTENCIAS+"'");
        database.close();
        Toast.makeText(this,"DATOS ELIMINADOS", Toast.LENGTH_LONG).show();

    }

}