package com.justiceleague.locus.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.io.StringReader;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class contact implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("number")
    private String number;

    public contact(String name, String number){
        this.name=name;
        this.number=number;
    }

    public void setname(String name){
        this.name=name;
    }

    public void setNumber(String number){
        this.number=number;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }
}
