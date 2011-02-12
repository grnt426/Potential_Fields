package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 */
public class FieldSource {

    private String fieldColor;
    private double charge, range;

    public FieldSource(){
        fieldColor = "NoName";
    }
    public FieldSource(String name){
        fieldColor = name;
    }

    public void setCharge(double c){
        charge = c;
    }

    public void setRange(double r){
        range = r;
    }

    public double getCharge() {
        return charge;
    }

    public double getRange() {
        return range;
    }

    public String getName(){
        return fieldColor;
    }
}
