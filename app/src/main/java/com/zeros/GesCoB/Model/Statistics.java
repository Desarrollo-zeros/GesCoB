package com.zeros.GesCoB.Model;

import java.util.ArrayList;
import java.util.List;

public class Statistics {


    private List<Daily> dailyList = new ArrayList<>();
    private List<Week> weekList = new ArrayList<>();
    private List<Month> monthList = new ArrayList<>();
    private List<Meta> metaList = new ArrayList<>();


    public Statistics(){

    }

    public Statistics(List<Daily> dailyList, List<Week> weekList, List<Month> monthList, List<Meta> metaList) {
        this.dailyList = dailyList;
        this.weekList = weekList;
        this.monthList = monthList;
        this.metaList = metaList;
    }

    public List<Daily> getDailyList() {
        return dailyList;
    }

    public void setDailyList(List<Daily> dailyList) {
        this.dailyList = dailyList;
    }

    public List<Week> getWeekList() {
        return weekList;
    }

    public void setWeekList(List<Week> weekList) {
        this.weekList = weekList;
    }

    public List<Month> getMonthList() {
        return monthList;
    }

    public void setMonthList(List<Month> monthList) {
        this.monthList = monthList;
    }

    public List<Meta> getMetaList() {
        return metaList;
    }

    public void setMetaList(List<Meta> metaList) {
        this.metaList = metaList;
    }
}

class  Daily{
    private String x;
    private double y;

    public Daily(String x, double y) {
        this.x = x;
        this.y = y;
    }

    public Daily(){

    }

}

class Week{
    private String x;
    private double y;

    public Week(String x, double y) {
        this.x = x;
        this.y = y;
    }

    public Week(){

    }
}


class Month{
    private String x;
    private double y;

    public Month(String x, double y) {
        this.x = x;
        this.y = y;
    }

    public Month(){
    }
}

class Meta{
    private double value;

    public Meta(){

    }

    public Meta(double value){
        this.value = value;
    }
}