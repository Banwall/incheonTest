package com.IncheonTest.qst.incheontest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartActivity extends AppCompatActivity {

    private Call<Map<String, List<PostResult>>> call;
    private BarChart firstBarChart, twoBarChart, thirdBarChart;
    private BarDataSet firstBarDataSet, twoBarDataSet, thirdBarDataSet;
    private BarData firstBarData, twoBarData, thirdBarData;
    private List<String> legendList = new ArrayList<>();

    /* 차트 관련 ****************************************************************/
    public List<String> dayList = new ArrayList<>(); // 일주일 날짜 배열
    public ArrayList<String> xArr = new ArrayList<>(); // 공통 x축

    public ArrayList<BarEntry> firstEntryChart = new ArrayList<>(); // 데이터를 담을 Arraylist
    public List<PostResult> firstResultList = new ArrayList<>();
    public List<String> firstResultChartDayList = new ArrayList<>();
    public List<String> firstResultSumValue = new ArrayList<>();

    public ArrayList<BarEntry> twoEntryChart = new ArrayList<>(); // 데이터를 담을 Arraylist
    public List<PostResult> twoResultList = new ArrayList<>();
    public List<String> twoResultChartDayList = new ArrayList<>();
    public List<String> twoResultSumValue = new ArrayList<>();

    public ArrayList<BarEntry> thirdEntryChart = new ArrayList<>(); // 데이터를 담을 Arraylist
    public List<PostResult> thirdResultList = new ArrayList<>();
    public List<String> thirdResultChartDayList = new ArrayList<>();
    public List<String> thirdResultSumValue = new ArrayList<>();

    public int[] rainbowColors = {
            Color.rgb(255, 0, 0), Color.rgb(255, 140, 0), Color.rgb(255, 255, 0),
            Color.rgb(0, 128, 0), Color.rgb(0, 0, 255), Color.rgb(75, 0, 130), Color.rgb(128, 0, 128)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Toolbar toolbar = findViewById (R.id.chartToolBar);
        setSupportActionBar (toolbar); //액티비티의 앱바(App Bar)로 지정
        toolbar.getMenu().findItem(R.id.dashboard).setVisible(false); //toolbar 메뉴 아이템 재정의 해줬어요! 이거때문에 에러났었습니다

        firstBarChart = findViewById(R.id.firstBarChart);
        twoBarChart = findViewById(R.id.twoBarChart);
        thirdBarChart = findViewById(R.id.thirdBarChart);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        dayList.add(format.format(cal.getTime())); // 오늘 날짜 배열에 집어넣기
        
        for(int i = 1; i <= 30; i++) {
            cal.add(Calendar.DAY_OF_MONTH, - 1);
            dayList.add(format.format(cal.getTime()));
        }

        Collections.reverse(dayList);

        call = Retrofit_client.getApiService().getChartData();
        call.enqueue(new Callback<Map<String, List<PostResult>>>() {
            //콜백 받는 부분
            @Override
            public void onResponse(Call<Map<String, List<PostResult>>> call, Response<Map<String, List<PostResult>>> response) {
                Map<String, List<PostResult>> result = response.body();

                firstResultList = result.get("firstResult");
                twoResultList = result.get("twoResult");
                thirdResultList = result.get("thirdResult");

                // result에 날짜를 빼와서 dayList랑 비교해서 없는날짜 넣어주고 없는날짜에는 값을 0을 넣어야함

                setChartData(); // 차트 만들어주는 함수 (코드 지저분해서 뺀거임 의미 X)
            }

            @Override
            public void onFailure(Call<Map<String, List<PostResult>>> call, Throwable t) {

            }
        });
    }

    // 메뉴 리소스 XML의 내용을 앱바(App Bar)에 반영
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_chart, menu);

        return true;
    }

    //앱바(App Bar)에 표시된 액션 또는 오버플로우 메뉴가 선택되면
    //액티비티의 onOptionsItemSelected() 메서드가 호출
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.dashboard:
                Intent intent = new Intent(this, DashBoardActivity.class);
                intent.putExtra("name", DashBoardActivity.toolBarName);
                startActivity(intent);
                finish();
            default:
                return super.onOptionsItemSelected (item);
        }
    }

    public void setChartData() {

        int firstTmpIdx = 0;
        int twoTmpIdx = 0;
        int thirdTmpIdx = 0;

        for(int i = 0; i < firstResultList.size(); i++) {
            firstResultChartDayList.add(firstResultList.get(i).getChartDay());
        }

        for(int i = 0; i < twoResultList.size(); i++) {
            twoResultChartDayList.add(twoResultList.get(i).getChartDay());
        }

        for(int i = 0; i < thirdResultList.size(); i++) {
            thirdResultChartDayList.add(thirdResultList.get(i).getChartDay());
        }

        for(int j = 0; j < dayList.size(); j++) {
            if(firstResultChartDayList.contains(dayList.get(j))) {
                firstResultSumValue.add(firstResultList.get(firstTmpIdx).getSumByDay());
                firstTmpIdx++;
            } else {
                firstResultSumValue.add(String.valueOf(0));
            }

            if(twoResultChartDayList.contains(dayList.get(j))) {
                twoResultSumValue.add(twoResultList.get(twoTmpIdx).getSumByDay());
                twoTmpIdx++;
            } else {
                twoResultSumValue.add(String.valueOf(0));
            }

            if(thirdResultChartDayList.contains(dayList.get(j))) {
                thirdResultSumValue.add(thirdResultList.get(thirdTmpIdx).getSumByDay());
                thirdTmpIdx++;
            } else {
                thirdResultSumValue.add(String.valueOf(0));
            }
        }

        for(int k = 0; k < dayList.size(); k++) {
            firstEntryChart.add(new BarEntry(k, Integer.parseInt(firstResultSumValue.get(k))));
            twoEntryChart.add(new BarEntry(k, Integer.parseInt(twoResultSumValue.get(k))));
            thirdEntryChart.add(new BarEntry(k, Integer.parseInt(thirdResultSumValue.get(k))));

            Integer getYear = Integer.parseInt(dayList.get(k).substring(0,4));
            Integer getMonth = Integer.parseInt(dayList.get(k).substring(5,7));
            Integer getDay = Integer.parseInt(dayList.get(k).substring(8,10));

            LocalDate date = LocalDate.of(getYear, getMonth, getDay);

            DayOfWeek dayOfWeek = date.getDayOfWeek();

            int dayOfWeekNumber = dayOfWeek.getValue(); // 월요일 1 ~ 일요일 7

            switch (dayOfWeekNumber) {
                case 1 :
                    xArr.add(dayList.get(k).substring(5,10));
                    legendList.add("월");
                    break;
                case 2:
                    xArr.add("");
                    legendList.add("화");
                    break;
                case 3:
                    xArr.add("");
                    legendList.add("수");
                    break;
                case 4:
                    xArr.add("");
                    legendList.add("목");
                    break;
                case 5:
                    xArr.add("");
                    legendList.add("금");
                    break;
                case 6:
                    xArr.add("");
                    legendList.add("토");
                    break;
                case 7:
                    xArr.add("");
                    legendList.add("일");
                    break;
            }
        }

        switch(legendList.get(0)) {
            case "화" :
                rainbowColors = new int[]{
                        Color.rgb(255, 140, 0), Color.rgb(255, 255, 0), Color.rgb(0, 128, 0), Color.rgb(0, 0, 255), Color.rgb(75, 0, 130), Color.rgb(128, 0, 128), Color.rgb(255, 0, 0)
                };
                break;
            case "수" :
                rainbowColors = new int[]{
                        Color.rgb(255, 255, 0), Color.rgb(0, 128, 0), Color.rgb(0, 0, 255), Color.rgb(75, 0, 130), Color.rgb(128, 0, 128), Color.rgb(255, 0, 0), Color.rgb(255, 140, 0)
                };
                break;
            case "목" :
                rainbowColors = new int[]{
                        Color.rgb(0, 128, 0), Color.rgb(0, 0, 255), Color.rgb(75, 0, 130), Color.rgb(128, 0, 128), Color.rgb(255, 0, 0), Color.rgb(255, 140, 0), Color.rgb(255, 255, 0)
                };
                break;
            case "금" :
                rainbowColors = new int[]{
                        Color.rgb(0, 0, 255), Color.rgb(75, 0, 130), Color.rgb(128, 0, 128), Color.rgb(255, 0, 0), Color.rgb(255, 140, 0), Color.rgb(255, 255, 0), Color.rgb(0, 128, 0)
                };
                break;
            case "토" :
                rainbowColors = new int[]{
                        Color.rgb(75, 0, 130), Color.rgb(128, 0, 128), Color.rgb(255, 0, 0), Color.rgb(255, 140, 0), Color.rgb(255, 255, 0), Color.rgb(0, 128, 0), Color.rgb(0, 0, 255)
                };
                break;
            case "일" :
                rainbowColors = new int[]{
                        Color.rgb(128, 0, 128), Color.rgb(255, 0, 0), Color.rgb(255, 140, 0), Color.rgb(255, 255, 0), Color.rgb(0, 128, 0), Color.rgb(0, 0, 255), Color.rgb(75, 0, 130)
                };
                break;
        }

        // legendList에 날짜별로 들어가니 List[0] 빨간색 List[1] 주황색 ........ List[6] 보라색 ToolBar에 세팅하기

        //getSupportActionBar().setTitle(요일별 색 지정해서 Text로 띄워주기);

        firstBarDataSet = new BarDataSet(firstEntryChart, "주차시간");
        twoBarDataSet = new BarDataSet(twoEntryChart, "주차시간");
        thirdBarDataSet = new BarDataSet(thirdEntryChart, "주차시간");

        firstBarDataSet.setColors(rainbowColors);
        twoBarDataSet.setColors(rainbowColors);
        thirdBarDataSet.setColors(rainbowColors);

        firstBarChart.setData(new BarData(firstBarDataSet)); // 차트에 위의 DataSet 을 넣는다.
        twoBarChart.setData(new BarData(twoBarDataSet)); // 차트에 위의 DataSet 을 넣는다.
        thirdBarChart.setData(new BarData(thirdBarDataSet)); // 차트에 위의 DataSet 을 넣는다.

        Description description = new Description();
        description.setText("");

        XAxis firstXAxis = firstBarChart.getXAxis();
        firstXAxis.setValueFormatter(new IndexAxisValueFormatter(xArr));
        firstXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        firstXAxis.setDrawGridLines(false);
        firstXAxis.setDrawAxisLine(false);
        firstXAxis.setGranularity(1f);
        firstXAxis.setLabelCount(dayList.size());

        XAxis twoXAxis = twoBarChart.getXAxis();
        twoXAxis.setValueFormatter(new IndexAxisValueFormatter(xArr));
        twoXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        twoXAxis.setDrawGridLines(false);
        twoXAxis.setDrawAxisLine(false);
        twoXAxis.setGranularity(1f);
        twoXAxis.setLabelCount(dayList.size());

        XAxis thirdXAxis = thirdBarChart.getXAxis();
        thirdXAxis.setValueFormatter(new IndexAxisValueFormatter(xArr));
        thirdXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        thirdXAxis.setDrawGridLines(false);
        thirdXAxis.setDrawAxisLine(false);
        thirdXAxis.setGranularity(1f);
        thirdXAxis.setLabelCount(dayList.size());

        firstBarChart.setDescription(description);
        twoBarChart.setDescription(description);
        thirdBarChart.setDescription(description);

        firstBarChart.getAxisLeft().setAxisMinimum(0);
        twoBarChart.getAxisLeft().setAxisMinimum(0);
        thirdBarChart.getAxisLeft().setAxisMinimum(0);

        firstBarChart.getAxisLeft().setAxisMaximum(24);
        twoBarChart.getAxisLeft().setAxisMaximum(24);
        thirdBarChart.getAxisLeft().setAxisMaximum(24);

        firstBarChart.getAxisRight().setEnabled(false);
        twoBarChart.getAxisRight().setEnabled(false);
        thirdBarChart.getAxisRight().setEnabled(false);

        firstBarChart.invalidate();
        twoBarChart.invalidate();
        thirdBarChart.invalidate();

        firstBarChart.setTouchEnabled(false);
        twoBarChart.setTouchEnabled(false);
        thirdBarChart.setTouchEnabled(false);
    }
}