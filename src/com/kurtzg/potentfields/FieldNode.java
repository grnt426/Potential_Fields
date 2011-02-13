package com.kurtzg.potentfields;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:      Grant Kurtz
 */
public class FieldNode {

    // instance variables
    private ArrayList<FieldSource> sources;
    private ArrayList<Charge> charges;
    private HashMap<FieldType, Double> typeValues;
    private double totalCharge, highestCharge;
    private FieldType highestFieldType;

    // creates an empty source array
    public FieldNode(){
        sources = new ArrayList<FieldSource>();
        charges = new ArrayList<Charge>();
        typeValues = new HashMap<FieldType, Double>();
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
        double charge = c.getCharge();
        totalCharge += charge;

        // store charge for all other types
        for(FieldType t : c.getTypes()){
            double add = 0;
            if(typeValues.get(t) != null)
                add = typeValues.get(t);
            if(add+charge > highestCharge){
                highestCharge = add+charge;
                highestFieldType = t;
            }
            typeValues.put(t, add+charge);
        }

        // don't let the value fall below 0
        // TODO: in the future, allow negative values (requires better handling
        // TODO: in paint class)
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

    public double getHighestCharge(){
        return highestCharge;
    }

    public FieldType getHighestChargeType(){
        return highestFieldType;
    }

    public void removeSource(FieldSource fs){
        sources.remove(fs);
    }
}
