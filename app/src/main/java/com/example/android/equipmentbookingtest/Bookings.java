package com.example.android.equipmentbookingtest;

/**
 * Created by liyameng on 15/12/3.
 */
public class Bookings {
    private String device,time;

    public Bookings(String device,String time)
    {
        this.setDevice(device);
        this.setTime(time);
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
