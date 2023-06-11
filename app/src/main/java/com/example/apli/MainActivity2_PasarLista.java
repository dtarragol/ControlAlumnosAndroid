package com.example.apli;

import static com.example.apli.AdminSQLiteOpenHelper.TABLE_ALUMNOS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2_PasarLista extends AppCompatActivity {

    LinearLayout lista;
    RadioGroup RG;
    int numeroAlumno=1;
    List<Integer> IDS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2_pasar_lista);
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
                String A = temp_id + ": " + temp_nombre + " " + temp_apellido + " " + temp_apellido2;
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
                LinearLayout checkBoxLayout = new LinearLayout(this);
                checkBoxLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                checkBoxLayout.setOrientation(LinearLayout.HORIZONTAL);

                RG = new RadioGroup(this);
                RG.setOrientation(LinearLayout.HORIZONTAL);
                RG.setId(View.generateViewId());
                int radiogroupid= RG.getId();
                IDS.add(radiogroupid);

                RadioButton radioButton1 = new RadioButton(this);
                radioButton1.setText("Nula");
                radioButton1.setTextColor(Color.BLACK);
                RadioButton radioButton2 = new RadioButton(this);
                radioButton2.setText("Parcial");
                radioButton2.setTextColor(Color.BLACK);
                RadioButton radioButton3 = new RadioButton(this);
                radioButton3.setText("Total");
                radioButton3.setTextColor(Color.BLACK);
                RG.addView(radioButton1);
                RG.addView(radioButton2);
                RG.addView(radioButton3);

                rowLayout.addView(tv);
                rowLayout.addView(RG);

                lista.addView(rowLayout);
                numeroAlumno++;
            } while (consulta.moveToNext());
        }
        BD.close();
    }
    public void btnVolver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void enviarDatosAsistencias(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        SQLiteDatabase BD = admin.getReadableDatabase();

        for(int i = 1;i<numeroAlumno;i++) {
            RadioGroup RG= findViewById(IDS.get(i-1));
            int selectedRadioButtonId = RG.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedOption = selectedRadioButton.getText().toString();
                if(selectedOption.equals("Nula")){
                    String updateQuery = "UPDATE "+TABLE_ALUMNOS+" SET nula = nula + ? WHERE id = "+i;
                    BD.execSQL(updateQuery, new Object[]{1});
                }
                if(selectedOption.equals("Parcial")){
                    String updateQuery = "UPDATE "+TABLE_ALUMNOS+" SET parcial = parcial + ? WHERE id = "+i;
                    BD.execSQL(updateQuery, new Object[]{1});
                }
                if(selectedOption.equals("Total")){
                    String updateQuery = "UPDATE "+TABLE_ALUMNOS+" SET total = total + ? WHERE id = "+i;
                    BD.execSQL(updateQuery, new Object[]{1});
                }
            }
        }
        Toast.makeText(this, "Se han actualizado los datos.", Toast.LENGTH_LONG).show();
        BD.close();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}