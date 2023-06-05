package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;
import static com.example.apli.AdminSQLiteOpenHelper.TABLE_TUTORES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2_ModificarAlumno extends AppCompatActivity {
    public EditText et_observaciones;
    public Spinner spinner;
    int posicion_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_modificar_alumno);
        et_observaciones = (EditText) findViewById(R.id.txt_observaciones);
        spinner = (Spinner) findViewById(R.id.spinner_lista_alumnos);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM " + TABLE_ALUMNOS;
        Cursor consulta = BD.rawQuery(c, null);
        if (consulta.moveToFirst()) {
            List<String> opciones = new ArrayList<>();
            do {
                String temp_id = consulta.getString(0);
                String temo_nombre = consulta.getString(1);
                String temp_apellido = consulta.getString(2);
                String temp_apellido2 = consulta.getString(3);
                String temp_dni = consulta.getString(4);
                String temp_obs = consulta.getString(5);
                opciones.add(temp_id + ": " + temo_nombre + " " + temp_apellido + " " + temp_apellido2);
            } while (consulta.moveToNext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            BD.close();
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    posicion_spinner = position;
                    AdminSQLiteOpenHelper a = new AdminSQLiteOpenHelper(MainActivity2_ModificarAlumno.this);
                    SQLiteDatabase BaseDeDatos = a.getReadableDatabase();
                    String c = "SELECT * FROM " + TABLE_ALUMNOS + " Where id=" + (position + 1);
                    Cursor con = BaseDeDatos.rawQuery(c, null);
                    if (con.moveToFirst()) {
                        do {
                            String temp_obs = con.getString(5);
                            et_observaciones.setText(temp_obs);
                        } while (con.moveToNext());
                    }
                    BaseDeDatos.close();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Este método se llama cuando no se selecciona ningún elemento en el Spinner
                }
            });

        }
    }
    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void ModificarAlumno(View view){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(MainActivity2_ModificarAlumno.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("observaciones", et_observaciones.getText().toString());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(posicion_spinner+1)};

        long newRowId = db.update(TABLE_ALUMNOS,modificacion, whereClause, whereArgs);
        if(newRowId==-1){
            Toast.makeText(this, "¡NO se ha modificado!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "¡Observaciones modificadas!", Toast.LENGTH_LONG).show();
        }
        db.close();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}