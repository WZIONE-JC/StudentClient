package com.example.studentclient;

import org.litepal.crud.LitePalSupport;

public class CallTable extends LitePalSupport {
    private int callNo;
    private long startTime;
    private long deadTime;
    private String courseNo;

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public int getCallNo() {
        return callNo;
    }

    public void setCallNo(int callNo) {
        this.callNo = callNo;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDeadTime() {
        return deadTime;
    }

    public void setDeadTime(long deadTime) {
        this.deadTime = deadTime;
    }
}
