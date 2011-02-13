package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 */
public class Charge {

    // instance vars
    private double charge;
    private FieldSource source;
    public FieldNode node;
    private int blockX, blockY;

    public Charge(){
        charge = 0.0;
    }

    public Charge(double c){
        charge = c;
    }

    public Charge(double c, FieldSource s){
        source = s;
        charge = c;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public FieldSource getSource() {
        return source;
    }

    public void setSource(FieldSource source) {
        this.source = source;
    }

    public void setNode(FieldNode fn){
        node = fn;
    }

    public void setBlockLocation(int x, int y){
        blockX = x;
        blockY = y;
    }

    public void removeSelf(){
        if(node != null)
            node.removeCharge(this);
        node = null;
    }
    
    public int getBlockX(){
        return blockX;
    }

    public int getBlockY(){
        return blockY;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }
}
