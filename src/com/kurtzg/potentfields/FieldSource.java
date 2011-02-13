package com.kurtzg.potentfields;

import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class FieldSource {

    private String fieldColor;
    private double charge, range;
    private ArrayList<Charge> charges;
    private FieldNode node;

    public FieldSource(){
        fieldColor = "NoName";
        charges = new ArrayList<Charge>();
    }
    public FieldSource(String name){
        fieldColor = name;
        charges = new ArrayList<Charge>();
    }

    public void addChargeNode(Charge c){
        charges.add(c);
    }

    public void setCharge(double c){
        charge = c;
    }

    public void setRange(double r){
        range = r;
    }

    public void setFieldNode(FieldNode node){
        this.node = node;
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

    public void removeSelf(){
        node.removeSource(this);
    }

    public void removeCharges(){
        for(Charge c : charges)
            c.removeSelf();
    }
}
