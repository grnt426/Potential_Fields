package com.kurtzg.potentfields;

import java.util.*;

/**
 * Author:      Grant Kurtz
 */
public class FieldNode {

    // instance variables
    private ArrayList<FieldSource> sources;
    private ArrayList<Charge> charges;
    private HashMap<String, Double> typeValues;
    private double totalCharge, highestCharge;
    private FieldType highestFieldType;

    // creates an empty source array
    public FieldNode(){
        sources = new ArrayList<FieldSource>();
        charges = new ArrayList<Charge>();
        typeValues = new HashMap<String, Double>();
        totalCharge = 0.0;
    }

    public double getTotalCharge(){
        return totalCharge < 0 ? 0 : totalCharge;
    }

    public void addSource(FieldSource fs){
        sources.add(fs);
    }

    public void addCharge(Charge c){
        charges.add(c);
        c.setNode(this);
        double charge = c.getCharge();
        totalCharge += charge;

//        System.out.println("Adding Charge with types: " + c.getTypes().toString());

        // store charge for all other types
        for(FieldType t : c.getTypes()){
            double add = 0;
            if(typeValues.containsKey(t.toString()))
                add = typeValues.get(t.toString());
            if(add+Math.abs(charge) > highestCharge){
                highestCharge = add+charge;
                highestFieldType = t;
            }
//            System.out.println(t.toString());
            typeValues.put(t.toString(), add+charge);
        }
    }

    public void removeCharge(Charge c){
        charges.remove(c);
        double charge = c.getCharge();
        totalCharge -= charge;

        // unstore charge for all other types
        for(FieldType t : c.getTypes()){
            double current = 0;
            if(typeValues.containsKey(t.toString())){
                current = typeValues.get(t.toString());
                typeValues.put(t.toString(), current-charge);
            }
        }
    }

    public ArrayList<Charge> getCharges(){
        return charges;
    }

    public double getHighestCharge(){
        System.out.println("Values: ");
        for(String s : typeValues.keySet())
            System.out.println(s);
        for(Double d : typeValues.values())
            System.out.println(d);

        return highestCharge;
    }

    public double getHighestCharge(Agent a){

        // vars
        double highest = 0;
        Object[] types = typeValues.keySet().toArray();
        Object[] values = typeValues.values().toArray();

        for(int i = 0; i < values.length; ++i){
            double val = (Double) values[i];

            // apply the field modifier
            if(a.getFieldModifiers().containsKey((String)types[i]))
                val = val * a.getFieldModifiers().get((String)types[i]);

            // do a special check
            // TODO: in the future, don't do this
            if(((String)types[i]).substring(0, 3).equals("ID:"))
                val = val * 100;

//            System.out.println("Highest Calc: " + types[i] + ": " + val);

            // check if this is the highest (or lowest) value
            if(Math.abs(val) > Math.abs(highest))
                highest = val;
        }

//        System.out.println("Highest Val: " + highest);
//        System.out.println();
        return highest;
    }

    public FieldType getHighestChargeType(){
        return highestFieldType;
    }

    public void removeSource(FieldSource fs){
        sources.remove(fs);
    }
}
