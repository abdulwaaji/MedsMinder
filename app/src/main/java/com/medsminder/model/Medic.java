package com.medsminder.model;

import android.text.TextUtils;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "medics_table")
public class Medic {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String Name;
    public String disease;
    public String consumingQuantity;
    public String medicColourID;
    public String schedule;
    public int availableStock;

    public Medic() {
    }

    public Medic(String name, String disease, String consumingQuantity, String medicColourID, List schedule, int availableStock) {
        Name = name;
        this.disease = disease;
        this.consumingQuantity = consumingQuantity;
        this.medicColourID = medicColourID;
        setSchedule(schedule);
        this.availableStock = availableStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getConsumingQuantity() {
        return consumingQuantity;
    }

    public void setConsumingQuantity(String consumingQuantity) {
        this.consumingQuantity = consumingQuantity;
    }

    public String getMedicColourID() {
        return medicColourID;
    }

    public void setMedicColourID(String medicColourID) {
        this.medicColourID = medicColourID;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }


    public ArrayList<String> getSchedule() {
        String[] arrays = TextUtils.split(schedule, ",");
        return new ArrayList<>(Arrays.asList(arrays));
    }

    public void setSchedule(List<String> list) {
        this.schedule = TextUtils.join(",", list);


    }

}
