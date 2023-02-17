package com.IncheonTest.qst.incheontest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView parkingListView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById (R.id.mainToolBar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정

        parkingListView = findViewById(R.id.parkingListView);
        
        List<String> list = new ArrayList<>();
        list.add("인천 주차장");
        list.add("강릉 주차장");
        list.add("서울 주차장");
        list.add("부산 주차장");

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        parkingListView.setAdapter(adapter);

        parkingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), DashBoardActivity.class);
                intent.putExtra("name", list.get(i));
                startActivity(intent);
                finish();
            }
        });
    }
}