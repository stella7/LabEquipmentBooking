package com.example.android.equipmentbookingtest;

/**
 * Created by liyameng on 15/12/2.
 */
public class Devices {
    private String name;

    public Devices(String name)
    {
        this.setName(name);
       // this.setDetails(details);
    }

   // public String getDetails() {
    //    return details;
   // }

   // public void setDetails(String details) {
       // this.details = details;
   // }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
