package com.kurtzg.potentfields;

import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class FieldNode {

    // instance variables
    private ArrayList<FieldSource> sources;
    private ArrayList<Charge> charges;
    private double totalCharge;

    // creates an empty source array
    public FieldNode(){
        sources = new ArrayList<FieldSource>();
        charges = new ArrayList<Charge>();
        totalCharge = 0.0;
    }

    public double getTotalCharge(){
        return totalCharge;
    }

    public void addSource(FieldSource fs){
        sources.add(fs);
    }

    public void addCharge(Charge c){
        charges.add(c);
        c.setNode(this);
        totalCharge += c.getCharge();
        if(totalCharge < 0)
            totalCharge = 0;
    }

    public void removeCharge(Charge c){
        charges.remove(c);
        totalCharge -= c.getCharge();
    }

    public ArrayList<Charge> getCharges(){
        return charges;
    }

    public void removeSource(FieldSource fs){
        sources.remove(fs);
    }
}
