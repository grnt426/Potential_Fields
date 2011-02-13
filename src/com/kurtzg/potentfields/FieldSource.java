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
    private int blockX, blockY;

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

    public ArrayList<Charge> getCharges(){
        return charges;
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
        if(node != null)
            node.removeSource(this);
        node = null;
    }

    public void setBlockLocation(int x, int y){
        blockX = x;
        blockY = y;
    }
    
    public void removeCharges(){
        for(Charge c : charges)
            c.removeSelf();
    }

    public void removeCharge(Charge c){
        charges.remove(c);
    }

    public int getBlockX() {
        return blockX;
    }

    public int getBlockY() {
        return blockY;
    }
}
