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

public class MainActivity2_CrearAlumnos extends AppCompatActivity {

    public EditText et_nombre, et_apellido1, et_apellido2, et_dni, et_observaciones;
    public Spinner spinner;
    int posicion_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_crear_alumnos);
        et_nombre = (EditText) findViewById(R.id.et_nombre_tutor);
        et_apellido1 = (EditText) findViewById(R.id.et_apellido1_tutor);
        et_apellido2 = (EditText) findViewById(R.id.et_apellido2_tutor);
        et_dni = (EditText) findViewById(R.id.et_email_tutor);
        et_observaciones = (EditText) findViewById(R.id.et_observa);
        spinner = (Spinner) findViewById(R.id.spinner_tutores);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM "+ TABLE_TUTORES;
        Cursor consulta = BD.rawQuery(c, null);
        if(consulta.moveToFirst()) {
            List<String> opciones = new ArrayList<>();
            do {
                String temp_id = consulta.getString(0);
                String temp_nombre = consulta.getString(1);
                String temp_apellido = consulta.getString(2);
                String temp_apellido2 = consulta.getString(3);
                opciones.add(temp_id + ": " + temp_nombre + " " + temp_apellido + " " + temp_apellido2);
            } while (consulta.moveToNext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            BD.close();
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion_spinner=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Este método se llama cuando no se selecciona ningún elemento en el Spinner
            }
        });

    }
    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void GuardarAlumno(View view){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(MainActivity2_CrearAlumnos.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues modificacion = new ContentValues();
        modificacion.put("nombre", et_nombre.getText().toString());
        modificacion.put("apellido", et_apellido1.getText().toString());
        modificacion.put("apellido2", et_apellido2.getText().toString());
        modificacion.put("dni", et_dni.getText().toString());
        modificacion.put("observaciones", et_observaciones.getText().toString());
        modificacion.put("idTutor", posicion_spinner+1);
        modificacion.put("nula", 0);
        modificacion.put("parcial", 0);
        modificacion.put("total", 0);
        long newRowId = db.insert(TABLE_ALUMNOS,null, modificacion);
        if(newRowId==-1){
            Toast.makeText(this, "¡NO se ha guardado!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "¡ALUMNO Guardado!", Toast.LENGTH_LONG).show();
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