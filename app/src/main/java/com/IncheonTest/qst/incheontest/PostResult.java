package com.IncheonTest.qst.incheontest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class PostResult {

    @SerializedName("fileName")
    @Expose
    private String fileName;

    @SerializedName("eventDt")
    @Expose
    private String eventDt;

    @SerializedName("input")
    @Expose
    private String input;

    @SerializedName("angle")
    @Expose
    private String angle;

    @SerializedName("crack")
    @Expose
    private String crack;

    @SerializedName("lpr")
    @Expose
    private String lpr;


    @SerializedName("vehicle")
    @Expose
    private String vehicle;

    @SerializedName("lprnumber")
    @Expose
    private String lprnumber;


    @SerializedName("output")
    @Expose
    private String output;

    @SerializedName("cdt")
    @Expose
    private String cdt;


    @SerializedName("mdt")
    @Expose
    private String mdt;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("sumByDay")
    @Expose
    private String sumByDay;

    @SerializedName("chartDay")
    @Expose
    private String chartDay;

    @SerializedName("mdtNow")
    @Expose
    private String mdtNow;

    @SerializedName("lastFileName")
    @Expose
    private String lastFileName;

    @SerializedName("postResultMap")
    @Expose
    private Map<String, List<PostResult>> postResultMap;

    public Map<String, List<PostResult>> getPostResultMap() {
        return postResultMap;
    }

    /*@SerializedName("postMainResultMap")
    @Expose
    private Map<String, List<PostResult>> postMainResultMap;

    public Map<String, List<PostResult>> getPostMainResultMap() {
        return postMainResultMap;
    }*/

    public String getFileName() {
        return fileName;
    }

    public String getEventDt() {
        return eventDt;
    }

    public String getInput() {
        return input;
    }

    public String getAngle() {
        return angle;
    }

    public String getCrack() {
        return crack;
    }

    public String getLpr() {
        return lpr;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getLprnumber() {
        return lprnumber;
    }

    public String getOutput() {
        return output;
    }

    public String getCdt() {
        return cdt;
    }

    public String getMdt() {
        return mdt;
    }

    public String getDistance() {
        return distance;
    }

    public String getSumByDay() {
        return sumByDay;
    }

    public String getChartDay() {
        return chartDay;
    }

    public String getMdtNow() { return mdtNow; }

    public String getLastFileName() { return lastFileName; }

    @Override
    public String toString() {
        return "PostResult{" +
                "fileName='" + fileName + '\'' +
                ", eventDt='" + eventDt + '\'' +
                ", input='" + input + '\'' +
                ", angle='" + angle + '\'' +
                ", crack='" + crack + '\'' +
                ", lpr='" + lpr + '\'' +
                ", vehicle='" + vehicle + '\'' +
                ", lprnumber='" + lprnumber + '\'' +
                ", output='" + output + '\'' +
                ", cdt='" + cdt + '\'' +
                ", mdt='" + mdt + '\'' +
                ", distance='" + distance + '\'' +
                ", sumByDay='" + sumByDay + '\'' +
                ", chartDay='" + chartDay + '\'' +
                ", mdtNow='" + mdtNow + '\'' +
                ", lastFileName='" + lastFileName + '\'' +
                '}';
    }
}