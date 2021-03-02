package com.example.tp3_exo6.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Planning {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "slot1")
    public String slot1;

    @ColumnInfo(name = "slot2")
    public String slot2;

    @ColumnInfo(name = "slot3")
    public String slot3;

    @ColumnInfo(name = "slot4")
    public String slot4;


    public Planning(String slot1, String slot2, String slot3, String slot4) {
        this.slot1 = slot1;
        this.slot2 = slot2;
        this.slot3 = slot3;
        this.slot4 = slot4;
    }

    public String getSlot1() {
        return this.slot1;
    }
    public String getSlot2() { return this.slot2; }
    public String getSlot3() { return this.slot3; }
    public String getSlot4() { return this.slot4; }

    public void setSlot1(String value) { this.slot1 = value; }
    public void setSlot2(String value) { this.slot2 = value; }
    public void setSlot3(String value) { this.slot3 = value; }
    public void setSlot4(String value) { this.slot4 = value; }
}
