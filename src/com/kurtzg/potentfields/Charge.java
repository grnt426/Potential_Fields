package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 */
public class Charge {

    // instance vars
    private double charge;
    private FieldSource source;

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
}
