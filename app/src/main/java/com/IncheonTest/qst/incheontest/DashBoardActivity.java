package com.IncheonTest.qst.incheontest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {

    Call<Map<String, List<PostResult>>> call;
    TextView parkingStartTime1, parkingEndTime1, isEv1, isNotEv1, isNotCar1, lprNumber1;
    TextView parkingStartTime2, parkingEndTime2, isEv2, isNotEv2, isNotCar2, lprNumber2;
    TextView parkingStartTime3, parkingEndTime3, isEv3, isNotEv3, isNotCar3, lprNumber3;
    ImageView parkingPic1, parkingPic2, parkingPic3;
    public String now = "";

    /** 이미지 관련 *****************************************************/
    public String firstImg = "";
    public String secondImg = "";
    public String thirdImg = "";

    public static String toolBarName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();

        toolBarName = intent.getExtras().getString("name");

        Toolbar toolbar = findViewById (R.id.dashBoardToolBar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정

        getSupportActionBar().setTitle(toolBarName);

        parkingStartTime1 =  findViewById(R.id.parking_StartTime1);
        parkingEndTime1 =  findViewById(R.id.parking_EndTime1);
        isEv1 =  findViewById(R.id.isEv1);
        isNotEv1 =  findViewById(R.id.isNotEv1);
        isNotEv1 =  findViewById(R.id.isNotEv1);
        isNotEv1 =  findViewById(R.id.isNotEv1);
        isNotCar1 =  findViewById(R.id.isNotCar1);
        lprNumber1 =  findViewById(R.id.lprNumber1);

        parkingStartTime2 =  findViewById(R.id.parking_StartTime2);
        parkingEndTime2 =  findViewById(R.id.parking_EndTime2);
        isEv2 =  findViewById(R.id.isEv2);
        isNotEv2 =  findViewById(R.id.isNotEv2);
        isNotEv2 =  findViewById(R.id.isNotEv2);
        isNotEv2 =  findViewById(R.id.isNotEv2);
        isNotCar2 =  findViewById(R.id.isNotCar2);
        lprNumber2 =  findViewById(R.id.lprNumber2);

        parkingStartTime3 =  findViewById(R.id.parking_StartTime3);
        parkingEndTime3 =  findViewById(R.id.parking_EndTime3);
        isEv3 =  findViewById(R.id.isEv3);
        isNotEv3 =  findViewById(R.id.isNotEv3);
        isNotEv3 =  findViewById(R.id.isNotEv3);
        isNotEv3 =  findViewById(R.id.isNotEv3);
        isNotCar3 =  findViewById(R.id.isNotCar3);
        lprNumber3 =  findViewById(R.id.lprNumber3);

        parkingPic1 = findViewById(R.id.parkingPic1);
        parkingPic2 = findViewById(R.id.parkingPic2);
        parkingPic3 = findViewById(R.id.parkingPic3);

        LocalDateTime nowDate = LocalDateTime.now();
        now = nowDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        call = Retrofit_client.getApiService().getParkingData();
        call.enqueue(new Callback<Map<String, List<PostResult>>>() {
            //콜백 받는 부분
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Map<String, List<PostResult>>> call, Response<Map<String, List<PostResult>>> response) {
                Map<String, List<PostResult>> resultMap = response.body();

                List<PostResult> result = resultMap.get("parkingDataList");
                List<PostResult> camera1 = resultMap.get("camera1");
                List<PostResult> camera2 = resultMap.get("camera2");
                List<PostResult> camera3 = resultMap.get("camera3");

                firstImg = "http://qst-s.iptime.org:18092/image/view?fileName=" + result.get(0).getFileName();
                secondImg = "http://qst-s.iptime.org:18092/image/view?fileName=" + result.get(1).getFileName();
                thirdImg = "http://qst-s.iptime.org:18092/image/view?fileName=" + result.get(2).getFileName();

                Glide.with(DashBoardActivity.this).load(firstImg).into(parkingPic3);
                Glide.with(DashBoardActivity.this).load(secondImg).into(parkingPic2);
                Glide.with(DashBoardActivity.this).load(thirdImg).into(parkingPic1);

                if(camera1.size() > 0) {
                    parkingStartTime1.setText("\t입차시간 : " + camera1.get(0).getCdt());
                    parkingEndTime1.setText("\t주차시간 : " + (camera1.get(0).getMdt() == null ? camera1.get(0).getMdtNow() : camera1.get(0).getMdt()));
                } else {
                    parkingStartTime1.setText("\t입차시간 : " + now);
                    parkingEndTime1.setText("\t주차시간 : ");
                }

                if(camera2.size() > 0) {
                    parkingStartTime2.setText("\t입차시간 : " + camera2.get(0).getCdt());
                    parkingEndTime2.setText("\t주차시간 : " + (camera2.get(0).getMdt() == null ? camera2.get(0).getMdtNow() : camera2.get(0).getMdt()));
                } else {
                    parkingStartTime2.setText("\t입차시간 : " + now);
                    parkingEndTime2.setText("\t주차시간 : ");
                }

                if(camera3.size() > 0) {
                    parkingStartTime3.setText("\t입차시간 : " + camera3.get(0).getCdt());
                    parkingEndTime3.setText("\t주차시간 : " + (camera3.get(0).getMdt() == null ? camera3.get(0).getMdtNow() : camera3.get(0).getMdt()));
                } else {
                    parkingStartTime3.setText("\t입차시간 : " + now);
                    parkingEndTime3.setText("\t주차시간 : ");
                }

                //parkingStartTime1.setText("\t입차시간 : " + (camera1.get(0).getCdt().isEmpty() ? now : camera1.get(0).getCdt()));
                //parkingStartTime2.setText("\t입차시간 : " + (camera2.get(0).getCdt().isEmpty() ? now : camera2.get(0).getCdt()));
                //parkingStartTime3.setText("\t입차시간 : " + (camera3.get(0).getCdt().isEmpty() ? now : camera3.get(0).getCdt()));

                //str3
                switch (result.get(0).getVehicle()) {
                    case "0000" :
                        isEv3.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    case "0001" :
                        isNotEv3.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    default :
                        isNotCar3.setBackgroundColor(Color.parseColor("#00FF00"));
                }
                lprNumber3.setText(result.get(0).getLprnumber().equals("9999") ? "" : result.get(0).getLprnumber());

                //str2
                switch (result.get(1).getVehicle()) {
                    case "0000" :
                        isEv2.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    case "0001" :
                        isNotEv2.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    default :
                        isNotCar2.setBackgroundColor(Color.parseColor("#00FF00"));
                }
                lprNumber2.setText(result.get(1).getLprnumber().equals("9999") ? "" : result.get(1).getLprnumber());

                //str1
                switch (result.get(2).getVehicle()) {
                    case "0000" :
                        isEv1.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    case "0001" :
                        isNotEv1.setBackgroundColor(Color.parseColor("#FF0000"));
                        break;
                    default :
                        isNotCar1.setBackgroundColor(Color.parseColor("#00FF00"));
                }
                lprNumber1.setText(result.get(2).getLprnumber().equals("9999") ? "" : result.get(2).getLprnumber());
            }

            @Override
            public void onFailure(Call<Map<String, List<PostResult>>> call, Throwable t) {
                Log.d("실패", t.getLocalizedMessage());
            }
        });
    }

    // 메뉴 리소스 XML의 내용을 앱바(App Bar)에 반영
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_dashboard, menu);

        return true;
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면
    //액티비티의 onOptionsItemSelected() 메서드가 호출
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.chart:
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if(keycode ==KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return false;
    }
}