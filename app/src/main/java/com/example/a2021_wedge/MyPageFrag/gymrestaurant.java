package com.example.a2021_wedge.MyPageFrag;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.a2021_wedge.R;

public class gymrestaurant extends AppCompatActivity {

    private Spinner spinner;
    private TextView textView11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gymrestaurant);

        spinner = (Spinner)findViewById(R.id.spinner);
        textView11 = (TextView)findViewById(R.id.textView11);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textView11.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}