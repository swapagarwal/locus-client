package com.justiceleague.locus.model;

import android.graphics.Movie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vishal.kumar1 on 28/08/16.
 */
public class Allcontact implements Serializable {

    @SerializedName("all_contacts")
    private List<contact> contacts;

    public Allcontact(List<contact> contacts){
        this.contacts=contacts;
    }

    public void setContacts(){
        this.contacts=contacts;
    }

    public List<contact> getContacts(){
        return contacts;
    }
}
