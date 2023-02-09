package com.IncheonTest.qst.incheontest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Call<List<PostResult>> call;
    TextView parkingStartTime, parkingEndTime, isEV, numPlate, greenVoid, parkingStartTime2, parkingEndTime2, isEV2, numPlate2, greenVoid2;

    ImageView parkingPic1, parkingPic2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parkingStartTime = (TextView) findViewById(R.id.parking_StartTime);
        parkingEndTime = (TextView) findViewById(R.id.parking_EndTime);
        isEV = (TextView) findViewById(R.id.isEV);
        numPlate = (TextView) findViewById(R.id.numPlate);
        greenVoid = (TextView) findViewById(R.id.green_void);

        parkingStartTime2 = (TextView) findViewById(R.id.parking_StartTime2);
        parkingEndTime2 = (TextView) findViewById(R.id.parking_EndTime2);
        isEV2 = (TextView) findViewById(R.id.isEV2);
        numPlate2 = (TextView) findViewById(R.id.numPlate2);
        greenVoid2 = (TextView) findViewById(R.id.green_void2);

        parkingPic1 = findViewById(R.id.parkingPic1);
        parkingPic2 = findViewById(R.id.parkingPic2);

        String imageStr = "http://192.168.200.44:8081/image/view?fileName=back1.jpg";
        Glide.with(this).load(imageStr).into(parkingPic1);

        String imageStr2 = "http://192.168.200.44:8081/image/view?fileName=back2.jpg";
        Glide.with(this).load(imageStr2).into(parkingPic2);

        call = Retrofit_client.getApiService().getPosts();
        call.enqueue(new Callback<List<PostResult>>() {
            //콜백 받는 부분
            @Override
            public void onResponse(Call<List<PostResult>> call, Response<List<PostResult>> response) {
                List<PostResult> result = response.body();

                //str1
                parkingStartTime.setText(result.get(0).getCdt());
                parkingEndTime.setText(result.get(0).getMdt());
                isEV.setText((result.get(0).getLpr().equals("0000") ? "전기차":
                              result.get(0).getLpr().equals("0001") ? "일반차":"차량 없음"));
                numPlate.setText(isEV.getText().equals("차량 없음") ? "-" : result.get(0).getLprnumber());
                greenVoid.setBackgroundColor(isEV.getText().equals("차량 없음") ? Color.parseColor("#00aa00"):Color.parseColor("#aa0000"));

                //str2
                parkingStartTime2.setText(result.get(1).getCdt());
                parkingEndTime2.setText(result.get(1).getMdt());
                isEV2.setText("전기차");
                numPlate2.setText("12아 3567");
                greenVoid2.setBackgroundColor(Color.parseColor("#aa0000"));
            }

            @Override
            public void onFailure(Call<List<PostResult>> call, Throwable t) {

            }
        });
    }
}