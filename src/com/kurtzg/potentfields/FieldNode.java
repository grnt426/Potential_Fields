package com.kurtzg.potentfields;

import java.util.*;

/**
 * Author:      Grant Kurtz
 *
 * Description: A representational cell that contains all the charges applied
 *              to this cell, the sources stationed here, and a cache of the
 *              highest charges and highest charge field type.
 */
public class FieldNode {

    // instance variables
    private ArrayList<FieldSource> sources;
    private ArrayList<Charge> charges;
    private HashMap<String, Double> typeValues;
    private double totalCharge, highestCharge;
    private FieldType highestFieldType;

    /*
     * Creates an empty source array with no charges, types, etc.
     */
    public FieldNode(){
        sources = new ArrayList<FieldSource>();
        charges = new ArrayList<Charge>();
        typeValues = new HashMap<String, Double>();
        totalCharge = 0.0;
    }

    /*
     * Returns the total (summed) default charge of all fields on this node,
     * also guarantees the value is non-negative
     */
    public double getTotalCharge(){
        return totalCharge < 0 ? 0 : totalCharge;
    }

    public void addSource(FieldSource fs){
        sources.add(fs);
    }

    /*
     * Tracks the added charge, and then adds its value to all pertinent field
     * types.  Also re-caches the highest charge.
     *
     * @param       c       The charge object that is being added
     */
    public void addCharge(Charge c){

        // store our charge, and let the charge know where its stored
        charges.add(c);
        c.setNode(this);
        double charge = c.getCharge();

        // cache the total charge of all types
        totalCharge += charge;

        // update the cached result of current charge values for each type this
        // charge applies to
        for(FieldType t : c.getTypes()){
            double add = 0;

            // check if this field type already exists in this node
            if(typeValues.containsKey(t.toString()))
                add = typeValues.get(t.toString());

            // check if this field type is the new highest
            if(add+Math.abs(charge) > highestCharge){
                highestCharge = add+charge;
                highestFieldType = t;
            }

            // finally, cache this result
            typeValues.put(t.toString(), add+charge);
        }
    }

    /*
     * Removes the charge from the globally tracked node list, and also
     * removes its value from all related field types.
     *
     * @param       c       The charge object to remove
     */
    public void removeCharge(Charge c){

        // remove this charge from the globally tracked list
        charges.remove(c);
        double charge = c.getCharge();
        totalCharge -= charge;

        // unstore charge for all other types
        for(FieldType t : c.getTypes()){
            double current = 0;

            // check if there are types related to our charge that also need
            // to be removed
            if(typeValues.containsKey(t.toString())){
                current = typeValues.get(t.toString());
                typeValues.put(t.toString(), current-charge);
            }
        }
    }

    public ArrayList<Charge> getCharges(){
        return charges;
    }

    /*
     * Returns the highest cached result for the type of charge with the
     * largest summed value.
     *
     * @return                  the cached value containing the highest charge
     */
    public double getHighestCharge(){
        return highestCharge;
    }

    /*
     * Given a list of modifiers, it recomputes the highest values.
     * Please, fix this....this is a horrible mess
     * Also, on rare occasions ConcurrentModificationExceptions occur when
     * accessing the keySet() values.
     *
     * @param:      a       The agent searching for the next highest charge
     */
    public double getHighestCharge(Agent a){

        // vars
        double highest = 0;


        /*
        // TODO: consider synchronizing on this objects
        Object[] types = typeValues.keySet().toArray();
        Object[] values = typeValues.values().toArray();

        // loop through all the types and apply the relevant modifier
        for(int i = 0; i < values.length; ++i){
            double val = (Double) values[i];

            // apply the field modifier
            if(a.getFieldModifiers().containsKey((String)types[i]))
                val = val * a.getFieldModifiers().get((String)types[i]);

            // do a special check for ourselves
            // TODO: in the future, don't do this (not very flexible)
            if(((String)types[i]).substring(0, 3).equals("ID:"))
                val = val * 100;

            // check if this is the highest (or lowest) value
            if(Math.abs(val) > Math.abs(highest))
                highest = val;
        }

        */

        // loop through all the charges and obtain their modified values
        for(Charge c : charges){

            double val;

            
        }

        return highest;
    }

    public FieldType getHighestChargeType(){
        return highestFieldType;
    }

    public void removeSource(FieldSource fs){
        sources.remove(fs);
    }
}
