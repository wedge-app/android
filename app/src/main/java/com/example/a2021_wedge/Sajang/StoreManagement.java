package com.example.a2021_wedge.Sajang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2021_wedge.R;
import com.example.a2021_wedge.RecyclerView.CustomAdapter;
import com.example.a2021_wedge.RecyclerView.Dictionary;

import java.util.ArrayList;

public class StoreManagement extends AppCompatActivity {

    Button buttonTable;
    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;


    ListView listView1;
    ArrayAdapter<String> adapter;
    ArrayList<String> listItem;

    Button button_1, button_2, button_3, button_4;

    EditText editText1;
    Button button_menu;

    Button timePickerDialogButton1, timePickerDialogButton2;
    int alarmHour=0, alarmMinute=0;

    TextView textView_open, textView_close;
    TextView textView_1, textView_2, textView_3, textView_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_management);

        textView_close = findViewById(R.id.textView_close);
        textView_close.setVisibility(View.INVISIBLE);

        button_1 = findViewById(R.id.button_1);
        button_1.setOnClickListener(view -> {
                button_1.setVisibility(View.INVISIBLE);
        });

        button_2 = findViewById(R.id.button_2);
        button_2.setOnClickListener(view -> {
                button_2.setVisibility(View.INVISIBLE);
                textView_close.setVisibility(View.VISIBLE);

        });

        button_3 = findViewById(R.id.button_3);
        button_3.setOnClickListener(view -> {
                button_3.setVisibility(View.INVISIBLE);
        });

        button_4 = findViewById(R.id.button_4);
        button_4.setOnClickListener(view -> {
                button_4.setVisibility(View.INVISIBLE);
        });

        textView_1 = findViewById(R.id.textView_1);
        textView_2 = findViewById(R.id.textView_2);
        textView_3 = findViewById(R.id.textView_3);
        textView_4 = findViewById(R.id.textView_4);

        textView_1.setOnClickListener(view -> {
            button_1.setVisibility(View.VISIBLE);
        });

        textView_2.setOnClickListener(view -> {
            button_2.setVisibility(View.VISIBLE);
            textView_close.setVisibility(View.INVISIBLE);
        });

        textView_3.setOnClickListener(view -> {
            button_3.setVisibility(View.VISIBLE);
        });

        textView_4.setOnClickListener(view -> {
            button_4.setVisibility(View.VISIBLE);

        });




        /////////////////// 오픈 시간 설정
        timePickerDialogButton1 = findViewById(R.id.timePickerDialogButton1);
        textView_open = findViewById(R.id.textView_open);

        timePickerDialogButton1.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (StoreManagement.this, (view, hourOfDay, minute) -> {
                        textView_open.setText("오픈 : " + hourOfDay + "시 " + minute + "분");
                    },alarmHour, alarmMinute, false);
            timePickerDialog.show();
        });

        ////////////////// 마감 시간 설정
        timePickerDialogButton2 = findViewById(R.id.timePickerDialogButton2);


        timePickerDialogButton2.setOnClickListener(v -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog
                    (StoreManagement.this, (view, hourOfDay, minute) -> {
                        textView_close.setText("마감 : " + hourOfDay + "시 " + minute + "분");
                    },alarmHour, alarmMinute, false);
            timePickerDialog.show();
        });



        ////////////////////메뉴 설정
        editText1 = findViewById(R.id.editText1);
        button_menu = findViewById(R.id.button_menu);

        listItem = new ArrayList<String>();

        button_menu.setOnClickListener(view -> {

            listItem.add(editText1.getText().toString());
            adapter.notifyDataSetChanged(); // 변경되었음을 어답터에 알려준다.
            editText1.setText("");
        });

        adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listItem);
        listView1 = findViewById(R.id.listView1);
        listView1.setAdapter(adapter);

        // 각 아이템 클릭시 해당 아이템 삭제한다.
        // 콜백매개변수는 순서대로 어댑터뷰, 해당 아이템의 뷰, 클릭한 순번, 항목의 아이디
        listView1.setOnItemClickListener((adapterView, view, i, l) -> {

            Toast.makeText(getApplicationContext(),listItem.get(i).toString() + " 삭제되었습니다.",Toast.LENGTH_SHORT).show();

            listItem.remove(i);
            adapter.notifyDataSetChanged();
        });


        /////////테이블 수 입력
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rcy_1);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();
        mAdapter = new CustomAdapter( mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(StoreManagement.this);
        buttonTable = findViewById(R.id.button_table);
        // 1. 화면 아래쪽에 있는 데이터 추가 버튼을 클릭하면
        buttonTable.setOnClickListener(v -> {


            // 2. 레이아웃 파일 edit_box.xml 을 불러와서 화면에 다이얼로그를 보여줍니다.



            View view = LayoutInflater.from(StoreManagement.this)
                    .inflate(R.layout.sample, null, false);
            builder.setView(view);

            final Button ButtonInsert = view.findViewById(R.id.button_insert);
            final EditText EditPerson = view.findViewById(R.id.edittext_person);
            final EditText EditTable = view.findViewById(R.id.edittext_table);


            ButtonInsert.setText("확인");

            final AlertDialog dialog = builder.create();

            ButtonInsert.setOnClickListener(view1 -> {

                String strPerson = EditPerson.getText().toString();
                String strTable = EditTable.getText().toString();

                Dictionary tbl = new Dictionary(strPerson, strTable);
                mArrayList.add(0, tbl);

                mAdapter.notifyItemInserted(0);

                dialog.dismiss();

            });

            dialog.show();

        });



    }




}