package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;
import static com.example.apli.AdminSQLiteOpenHelper.TABLE_TUTORES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Dialog mDialog;
    private boolean mostrarBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        mostrarBanner = sharedPreferences.getBoolean("mostrarBanner", true);



    }
    @Override
    protected void onResume() {
        super.onResume();

        if (mostrarBanner) {
            //popup con explicacion de la app
            mDialog = new Dialog(this);
            mDialog.setContentView(R.layout.popup_inicio);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.show();
            // Cambiar el estado de mostrarBanner a false
            mostrarBanner = false;

            // Guardar el nuevo estado utilizando SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("mostrarBanner", mostrarBanner);
            editor.apply();
        }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que desea eliminar todos los datos?\n" +
                        "Eliminara Historial, Alumnos y tutores.")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(MainActivity.this);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        database.delete(TABLE_TUTORES, null, null);
                        database.delete(TABLE_ALUMNOS, null, null);
                        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TUTORES+"'");
                        database.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_ALUMNOS+"'");
                        database.close();
                        Toast.makeText(MainActivity.this,"DATOS ELIMINADOS", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cerrar el diálogo
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro de que desea salir de la aplicación?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar al presionar "Aceptar" (por ejemplo, cerrar la aplicación)
                        finish(); // Cerrar la actividad actual
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Acciones a realizar al presionar "Cancelar" (por ejemplo, mantener la actividad abierta)
                        dialog.dismiss(); // Cerrar el diálogo
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}