package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;
import static com.example.apli.AdminSQLiteOpenHelper.TABLE_TUTORES;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2_VerAlumnos extends AppCompatActivity {

    private Spinner spinner;
    private String idTutor;
    private TextView txt_id, txt_nombre, txt_apellido, txt_apellido2, txt_dni, txt_observaciones;
    Button popupBTN;
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_ver_alumnos);

        txt_id= findViewById(R.id.txt_id);
        txt_nombre= findViewById(R.id.txt_nombre);
        txt_apellido= findViewById(R.id.txt_apellido);
        txt_apellido2= findViewById(R.id.txt_apellido2);
        txt_dni= findViewById(R.id.txt_dni);
        txt_observaciones= findViewById(R.id.txt_observaciones);

        popupBTN= findViewById(R.id.btn_ver_tutor);

        spinner = findViewById(R.id.spinner_lista_alumnos);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM "+ TABLE_ALUMNOS;
        Cursor consulta = BD.rawQuery(c, null);
        if(consulta.moveToFirst()){
            List<String> opciones = new ArrayList<>();
            do{
                String temp_id = consulta.getString(0);
                String temo_nombre = consulta.getString(1);
                String temp_apellido = consulta.getString(2);
                String temp_apellido2 = consulta.getString(3);
                String temp_dni = consulta.getString(4);
                String temp_obs = consulta.getString(5);
                idTutor = consulta.getString(6);
                opciones.add(temp_id+": "+temo_nombre+" "+temp_apellido+" "+temp_apellido2);
            } while (consulta.moveToNext());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            BD.close();

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AdminSQLiteOpenHelper a = new AdminSQLiteOpenHelper(MainActivity2_VerAlumnos.this);
                    SQLiteDatabase BaseDeDatos = a.getReadableDatabase();
                    String c = "SELECT * FROM "+ TABLE_ALUMNOS+ " Where id="+(position+1);
                    Cursor con = BaseDeDatos.rawQuery(c, null);
                    if(con.moveToFirst()) {
                        do {
                            String ID = con.getString(0);
                            String nom = con.getString(1);
                            String ape = con.getString(2);
                            String ape2 = con.getString(3);
                            String temp_dni = con.getString(4);
                            String temp_obs = con.getString(5);
                            idTutor = con.getString(6);
                            txt_id.setText(ID);
                            txt_nombre.setText(nom);
                            txt_apellido.setText(ape);
                            txt_apellido2.setText(ape2);
                            txt_dni.setText(temp_dni);
                            txt_observaciones.setText(temp_obs);
                        } while (con.moveToNext());
                    }
                    BaseDeDatos.close();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Acción a realizar cuando no se selecciona nada
                }
            });
        }
        mDialog = new Dialog(this);
        popupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String A,B;
                if(buscarNombreTutor()!=""&&buscarDatosTutor()!=""){
                    A=buscarNombreTutor();
                    B=buscarDatosTutor();
                }else{
                    A="Sin tutor registrado";
                    B="";
                }
                mDialog.setContentView(R.layout.popup_tutor);
                TextView tn = mDialog.findViewById(R.id.username);
                TextView ts = mDialog.findViewById(R.id.descripcion);
                tn.setText(A);
                ts.setText(B);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                mDialog.show();
            }
        });
    }

    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private String buscarNombreTutor(){
        String resultado="";

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity2_VerAlumnos.this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM "+ TABLE_TUTORES + " WHERE ID="+idTutor;
        Cursor consulta = BD.rawQuery(c, null);
        if(consulta.moveToFirst()) {
            String nombre = consulta.getString(1);
            String apellido = consulta.getString(2);
            String apellido2 = consulta.getString(3);
            resultado = "Nombre: "+nombre+" "+apellido+" "+apellido2;
            BD.close();
        }
        return resultado;
    }
    private String buscarDatosTutor(){
        String resultado="";

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(MainActivity2_VerAlumnos.this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM "+ TABLE_TUTORES + " WHERE ID="+idTutor;
        Cursor consulta = BD.rawQuery(c, null);
        if(consulta.moveToFirst()) {
            String email = consulta.getString(4);
            String telefono = consulta.getString(5);
            resultado = "Email: "+email+"\nTelefono: "+telefono;
            BD.close();
        }
        return resultado;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}