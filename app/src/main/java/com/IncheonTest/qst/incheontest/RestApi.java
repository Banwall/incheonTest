package com.IncheonTest.qst.incheontest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RestApi {
    // @GET( EndPoint-자원위치(URI) )
    @GET("api/getParkingData")
    Call<Map<String, List<PostResult>>> getParkingData();

    @GET("api/getChartData")
    Call<Map<String, List<PostResult>>> getChartData();
}