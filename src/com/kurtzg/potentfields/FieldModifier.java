package com.kurtzg.potentfields;

/*
 * Author:      Grant Kurtz
 *
 * Description:
 */
public class FieldModifier {

    protected FieldType type;
    protected double multiplier;

    public FieldModifier(FieldType ft, double mult){
        type = ft;
        multiplier = mult;
    }

    public FieldType getType(){
        return type;
    }

    public double getMultiplier(){
        return multiplier;
    }

    /*
     * Computes the modified field value after the multiplier is applied
     *
     * @param      c       the charge value to be manipulated
     *
     * @return             the modified field charge value
     */
    public double getModifiedValue(Charge c){
        return c.getCharge()*multiplier;
    }
}
