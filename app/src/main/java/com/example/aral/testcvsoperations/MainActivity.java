package com.example.aral.testcvsoperations;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight;
    private EditText etHeight;
    private EditText etDate;
    private DatePickerDialog.OnDateSetListener date;
    private Button csvButton;
    private Button getButton;

    final Calendar calendar = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = (EditText) findViewById(R.id.weightText);
        etHeight = (EditText) findViewById(R.id.heightText);
        etDate = (EditText) findViewById(R.id.dateText);
        csvButton = (Button) findViewById(R.id.csvButton);
        getButton = (Button) findViewById(R.id.getButton);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();

            }
        };

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        csvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileWriter mFileWriter;
                String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
                String fileName = "AnalysisData.txt";
                String filePath = baseDir + File.separator + fileName;


                Toast.makeText(MainActivity.this, filePath, Toast.LENGTH_LONG).show();
                try {

                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        File f = new File(filePath);
                        CSVWriter writer;
// File exist
                        if (f.exists() && !f.isDirectory())
                        {
                            mFileWriter = new FileWriter(filePath, true);
                            writer = new CSVWriter(mFileWriter);
                        }
                        else {
                            writer = new CSVWriter(new FileWriter(filePath));
                        }
                        String[] data = {"Ship Name", "Scientist Name"};

                        writer.writeNext(data);

                        writer.close();
                    }
                    else{

                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }
                }
                        catch(Exception e)
                    {
                        e.printStackTrace();
                    }



            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                CSVReader reader = new CSVReader(new FileReader("weight_log.csv"));
                String [] nextLine;

                    while ((nextLine = reader.readNext()) != null) {
                        // nextLine[] is an array of values from the line
                        Log.d("LOGGING",nextLine[0] + nextLine[1] + "etc...");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });




    }

    private void makeAndShowDialogBox(String message) {



        AlertDialog.Builder myDialogBox = new AlertDialog.Builder(this);

        // set message, title, and icon
        myDialogBox.setTitle("Done! ");
        myDialogBox.setMessage(message);

        // Set three option buttons
        myDialogBox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // whatever should be done when answering "YES" goes
                        // here

                    }
                });

        // myDialogBox.setNegativeButton("NO",
        // new DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int whichButton) {
        // // whatever should be done when answering "NO" goes here
        //
        // }
        // });

        // myDialogBox.setNeutralButton("Cancel",
        // new DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int whichButton) {
        // // whatever should be done when answering "NO" goes here
        //
        // }
        // });

        myDialogBox.create();
        myDialogBox.show();
    }


    private void updateDate() {


        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);



        etDate.setText(sdf.format(calendar.getTime()));

    }

}




