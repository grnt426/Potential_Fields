package com.kurtzg.potentfields;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author:      Grant Kurtz
 */
public class PFMap {

    // instance variables
    private int rows, cols;
    private HashMap<String, FieldNode> nodes;

    public PFMap(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        nodes = new HashMap<String, FieldNode>();

        // fill the map with empty nodes
        for(int r = 0; r < rows; ++r){
            for(int c = 0; c < cols; ++c){
                nodes.put(r+" "+c, new FieldNode());
            }
        }
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public FieldNode getNode(int x, int y){
        return getNode(x+"", y+"");
    }

    public FieldNode getNode(char x, char y){
        return getNode(x+"", y+"");
    }

    public FieldNode getNode(String x, String y){
        return nodes.get(x + " " + y);
    }

    public void removeSource(FieldSource fs){

        // tell the source to remove itself from the parent node
        fs.removeSelf();

        // secondly, tell the source to pull its charges from their respective
        // nodes
        fs.removeCharges();
    }

    public void moveSource(FieldSource fs){

        // move all the charges to their new locations
        for(Charge c : fs.getCharges()){

            // remove the charge from its parent block node
            c.removeSelf();

            // move the charge to new location
            int newX, newY;
            newX = c.getBlockX();
            newY = c.getBlockY()+1;
            c.setBlockLocation(newX, newY);

            if(newX > rows-1 || newX < 0 || newY > cols-1 || newY < 0){
                continue;
            }

            // update the field node with the new charge
            nodes.get(newX + " " + newY).addCharge(c);
        }

        // move the source itself to its new location
        fs.removeSelf();
        fs.setBlockLocation(fs.getBlockX(), fs.getBlockY()+1);
        nodes.get(fs.getBlockX() + " " + fs.getBlockY());
    }

    public void createSource(int x, int y, FieldSource fs){

        // add the source to the location that it spawned at
        nodes.get(x+" "+y).addSource(fs);
        fs.setFieldNode(nodes.get(x+" "+y));
        fs.setBlockLocation(x, y);

        // vars
        double range = fs.getRange(), charge = fs.getCharge();
        int xmin, xmax, ymin, ymax;

        // compute min/max range for affect
        xmin = (int)(x-range);
        ymin = (int)(y-range);
        xmax = (int)(x+range);
        ymax = (int)(y+range);
        if(xmin < 0)
            xmin = 0;
        if(ymin < 0)
            ymin = 0;
        if(xmax > cols-1)
            xmax = cols-1;
        if(ymax > rows-1)
            ymax = rows-1;

        // add to the surrounding nodes the charge value
        for(int r = -(int)range; r < range; ++r){
            for(int c = -(int)range; c < range; ++c){

                // make sure the combined values are not over our range
                // (to make a "circle")
                if(Math.abs(r)+Math.abs(c) > range)
                    continue;

                // compute charge
                double chargeValue;
                double dist = computeDistance(r+x, x, c+y, y);
                chargeValue = Math.abs((1 - dist / range) * charge);

                // create charge
                Charge nodeCharge = new Charge(chargeValue, fs);
                nodeCharge.setBlockLocation(r+x, c+y);
                fs.addChargeNode(nodeCharge);
                
                // check if we are out of range (this is the lazy way to do it
                // I know)
                if(r+x < xmin || r+x > xmax || c+y < ymin || c+y > ymax)
                    continue;

                nodes.get((r+x)+" "+(c+y)).addCharge(nodeCharge);

            }
        }
    }

    public double computeDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
}
