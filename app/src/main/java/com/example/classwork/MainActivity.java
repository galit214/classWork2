package com.example.classwork;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnSelectDate, btnSelectGender, btnSave;
    SharedPreferences sp,sp2;
    Dialog d;
    RadioButton rbMale, rbFemale;
    EditText edName, edLastName, edCitiy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        btnSelectDate.setOnClickListener(this);
        btnSelectGender = (Button) findViewById(R.id.btnSelectGender);
        btnSelectGender.setOnClickListener(this);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);
        edName = (EditText) findViewById(R.id.edName);
        edLastName = (EditText) findViewById(R.id.edLastName);
        edCitiy = (EditText) findViewById(R.id.edCitiy);
        edName.setOnClickListener(this);
        edLastName.setOnClickListener(this);
        edCitiy.setOnClickListener(this);




        sp = getSharedPreferences("details1", MODE_PRIVATE);
        sp2=getSharedPreferences("details2",MODE_PRIVATE);
        showSp();


    }

    @Override
    public void onClick(View view) {
        if (view == btnSelectDate) {
            Calendar systemCalnder = Calendar.getInstance();
            int year = systemCalnder.get(Calendar.YEAR);
            int month = systemCalnder.get(Calendar.MONTH);
            int day = systemCalnder.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new SetDate(), year, month, day);
            datePickerDialog.show();
        }
        if (view == btnSelectGender) {
            setBtnSelectDateDialog();
        }
        if (view == btnSave) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("are you sure you want to save?");
            builder.setCancelable(true);
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    activate();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("fName", edName.getText().toString());
                    editor.putString("lName", edLastName.getText().toString());
                    editor.putString("citiy", edCitiy.getText().toString());

                    editor.commit();

                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deActivate();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public class SetDate implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfyear, int dayOfmonth) {
            monthOfyear = monthOfyear + 1;
            String str = "you selected " + dayOfmonth + "/" + monthOfyear + "/" + year;
            Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG).show();
            btnSelectDate.setText(str);

        }
    }

    private void setBtnSelectDateDialog() {
        d = new Dialog(this);
        d.setContentView(R.layout.selectgender_dialog);
        d.setCancelable(true);
        rbMale = (RadioButton) d.findViewById(R.id.rbMale);
        rbFemale = (RadioButton) d.findViewById(R.id.rbFemale);
        d.show();

    }

    private void activate() {
        Log.d("tag", "yes");
    }

    private void deActivate() {
        Log.d("tag", "no");
    }

    private void showSp() {
        String fname = sp.getString("fName", null);
        String lname = sp.getString("lName", null);
        String citiy = sp.getString("citiy", null);
        if ((fname != null) && (lname != null) && (citiy != null)) {
            edName.setText(fname);
            edLastName.setText(lname);
            edCitiy.setText(citiy);
        }


    }
}
