package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 *
 * Description: Allows a unit to implement this class instead of its parent
 *              when the distance to the source needs to diminish after a set
 *              range.
 */
public class RangedFieldModifier extends FieldModifier{

    // vars
    private double range;

    public RangedFieldModifier(FieldType ft, double mult, double range){
        super(ft, mult);
    }

    public double getRange(){
        return range;
    }

    public void setRange(double range){
        this.range = range;
    }

    /*
     * Overrides the calculation to compute the modified charge value by
     * creating a diminished charge value the closer the distance the charge
     * is in relation to a set range.
     *
     * @param       c       the charge used in computing the new field value
     *
     * @returns             a value that accounts for the physical distance
     *                      between the source and this charge along with the
     *                      standard multiplier
     */
    public double getModifiedValue(Charge c){

        // vars
        double mod, dist;
        dist = c.getDistance() - range;

        // we only care when we are too close to the enemy
        if(dist < 0){
            dist = Math.abs(dist) / range;

            // calculate the charge based on our distance to the enemy
            mod = (dist * multiplier) * c.getCharge();
        }
        else
            mod = multiplier * c.getCharge();

        return mod;
    }
}
