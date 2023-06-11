package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity2_HistorialAsistencias extends AppCompatActivity {
    LinearLayout lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_historial_asistencias);
        lista = findViewById(R.id.glay);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getReadableDatabase();
        String c = "SELECT * FROM "+ TABLE_ALUMNOS;
        Cursor consulta = BD.rawQuery(c, null);
        if(consulta.moveToFirst()) {
            do {

                String temp_id = consulta.getString(0);
                String temp_nombre = consulta.getString(1);
                String temp_apellido = consulta.getString(2);
                String temp_apellido2 = consulta.getString(3);
                String temp_dni = consulta.getString(4);
                String temp_observaciones = consulta.getString(5);
                String temp_idTutor = consulta.getString(6);
                String temp_AsistenciaNula = consulta.getString(7);
                String temp_AsistenciaParcial = consulta.getString(8);
                String temp_AsistenciaTotal = consulta.getString(9);
                String A = temp_id + ": " + temp_nombre + " " + temp_apellido + " " + temp_apellido2 + "\nFALTA 100% "+temp_AsistenciaNula+" días, FALTA 50% "+temp_AsistenciaParcial+" dias";
                LinearLayout rowLayout = new LinearLayout(this);
                rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                rowLayout.setOrientation(LinearLayout.HORIZONTAL);
                TextView tv = new TextView(this);
                tv.setText(A);
                tv.setTextColor(Color.BLACK);
                LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                textViewParams.weight = 1; // Establecer peso para que el botón ocupe espacio restante
                tv.setLayoutParams(textViewParams);
                rowLayout.addView(tv);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rowLayout.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, 20); // Establecer margen inferior de 20 píxeles
                rowLayout.setLayoutParams(layoutParams);

                lista.addView(rowLayout);

            }while (consulta.moveToNext());
        }
        BD.close();
    }
    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}