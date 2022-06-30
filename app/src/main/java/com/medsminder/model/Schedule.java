package com.medsminder.model;

public class Schedule {

    private String scheduleTime;
    private int medsConsumptionStatus;

    public Schedule() {
    }

    public Schedule(String scheduleTime, int medsConsumptionStatus) {
        this.scheduleTime = scheduleTime;
        this.medsConsumptionStatus = medsConsumptionStatus;
    }

    public String getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(String scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public int getMedsConsumptionStatus() {
        return medsConsumptionStatus;
    }

    public void setMedsConsumptionStatus(int medsConsumptionStatus) {
        this.medsConsumptionStatus = medsConsumptionStatus;
    }
}
