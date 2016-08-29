package com.justiceleague.locus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class User implements Serializable {

    @SerializedName("mobile_number")
    private long mobileNumber;

    @SerializedName("password")
    private String password;

    public User(long mobileNumber, String password){
            this.mobileNumber=mobileNumber;
            this.password=password;
    }

    public void setMobileNumber(long mobileNumber){
        this.mobileNumber=mobileNumber;
    }
    public void setPassword(String password){
        this.password=password;
    }

    public long getMobileNumber(){
        return mobileNumber;
    }

    public String getPassword(){
        return password;
    }
}
