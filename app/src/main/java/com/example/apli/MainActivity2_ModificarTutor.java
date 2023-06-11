package com.example.apli;

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

public class MainActivity2_ModificarTutor extends AppCompatActivity {
    public EditText et_nombre_tutor, et_apellido1_tutor, et_apellido2_tutor, et_email_tutor, et_telefono_tutor;
    public Spinner sp_tutores;
    int posicion_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_modificar_tutor);
        et_nombre_tutor = (EditText) findViewById(R.id.et_nombre_tutor);
        et_apellido1_tutor = (EditText) findViewById(R.id.et_apellido1_tutor);
        et_apellido2_tutor = (EditText) findViewById(R.id.et_apellido2_tutor);
        et_email_tutor = (EditText) findViewById(R.id.et_email_tutor);
        et_telefono_tutor = (EditText) findViewById(R.id.et_telefono_tutor);
        sp_tutores = (Spinner) findViewById(R.id.spinner_tutor_modificar);
        inicializar();

    }
    public void inicializar(){
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
            sp_tutores.setAdapter(adapter);
            BD.close();
        }
        sp_tutores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicion_spinner=position;
                int i =0;
                AdminSQLiteOpenHelper a = new AdminSQLiteOpenHelper(MainActivity2_ModificarTutor.this);
                SQLiteDatabase bd = a.getReadableDatabase();
                String co = "SELECT * FROM "+ TABLE_TUTORES;
                Cursor consult = bd.rawQuery(co, null);
                if(consult.moveToFirst()) {
                    List<String> opciones = new ArrayList<>();
                    do {
                        String temp_id = consult.getString(0);
                        String temp_nombre = consult.getString(1);
                        String temp_apellido = consult.getString(2);
                        String temp_apellido2 = consult.getString(3);
                        String temp_email= consult.getString(4);
                        String temp_telefono = consult.getString(5);
                        opciones.add(temp_id + ": " + temp_nombre + " " + temp_apellido + " " + temp_apellido2);

                        if(i==posicion_spinner){
                            et_nombre_tutor.setText(temp_nombre);
                            et_apellido1_tutor.setText(temp_apellido);
                            et_apellido2_tutor.setText(temp_nombre);
                            et_email_tutor.setText(temp_email);
                            et_telefono_tutor.setText(temp_telefono);
                        }
                        i++;
                    } while (consult.moveToNext());
                    bd.close();
                }
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
    public void ModificarTutor(View view){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(MainActivity2_ModificarTutor.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("nombre", et_nombre_tutor.getText().toString());
        modificacion.put("apellido", et_apellido1_tutor.getText().toString());
        modificacion.put("apellido2", et_apellido2_tutor.getText().toString());
        modificacion.put("email", et_email_tutor.getText().toString());
        modificacion.put("telefono", et_telefono_tutor.getText().toString());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(posicion_spinner+1)};
        long newRowId = db.update(TABLE_TUTORES, modificacion,whereClause,whereArgs);
        if(newRowId==-1){
            Toast.makeText(this, "¡NO se ha modificado!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "¡TUTOR Modificado!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}