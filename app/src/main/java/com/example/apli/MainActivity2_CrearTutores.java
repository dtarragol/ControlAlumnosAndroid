package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_TUTORES;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2_CrearTutores extends AppCompatActivity {

    public EditText et_nombre_tutor, et_apellido1_tutor, et_apellido2_tutor, et_email_tutor, et_telefono_tutor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_crear_tutores);
        et_nombre_tutor = (EditText) findViewById(R.id.et_nombre_tutor);
        et_apellido1_tutor = (EditText) findViewById(R.id.et_apellido1_tutor);
        et_apellido2_tutor = (EditText) findViewById(R.id.et_apellido2_tutor);
        et_email_tutor = (EditText) findViewById(R.id.et_email_tutor);
        et_telefono_tutor = (EditText) findViewById(R.id.et_telefono_tutor);
    }
    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void GuardarTutor(View view){
        AdminSQLiteOpenHelper dbHelper = new AdminSQLiteOpenHelper(MainActivity2_CrearTutores.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues modificacion = new ContentValues();
        modificacion.put("nombre", et_nombre_tutor.getText().toString());
        modificacion.put("apellido", et_apellido1_tutor.getText().toString());
        modificacion.put("apellido2", et_apellido2_tutor.getText().toString());
        modificacion.put("email", et_email_tutor.getText().toString());
        modificacion.put("telefono", et_telefono_tutor.getText().toString());
        long newRowId = db.insert(TABLE_TUTORES,null, modificacion);
        if(newRowId==-1){
            Toast.makeText(this, "¡NO se ha guardado!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "¡TUTOR Guardado!", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}