package com.kurtzg.potentfields;

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

    private void moveSource(FieldSource fs, Dir d){

        int[] vector;

        vector = computeDirection(d);

        // move all the charges to their new locations
        for(Charge c : fs.getCharges()){

            // remove the charge from its parent block node
            c.removeSelf();

            // move the charge to new location
            int newX, newY;
            newX = c.getBlockX()+vector[0];
            newY = c.getBlockY()+vector[1];
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
                chargeValue = (1 - dist / range) * charge;

                // create charge
                Charge nodeCharge = new Charge(chargeValue, fs,
                        new FieldType("GroundUnit"));
                nodeCharge.addType(new FieldType("Team1"));
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

    public int[] getNextBlock(int x, int y){

        // vars
        int[] loc = new int[2];
        double highest = 0;

        // in case all positions around us are 0, set the "next" location to
        // our current location
        loc[0] = x;
        loc[1] = y;

        // search all around us for the highest block
        for(int i = x-1; i < x+2; ++i){
            for(int j = y-1; j<y+2; ++j){

                // make sure we are within the bounds of the map
                if(i < 0 || i > rows -1 || j < 0 || j > cols - 1)
                    continue;

                // otherwise grab the node's greatest charge
                if(nodes.get(i+ " " + j).getHighestCharge() > highest){
                    highest = nodes.get(i+ " " + j).getHighestCharge();
                    loc[0] = i;
                    loc[1] = j;
                }
            }
        }

        // set our goal location to the center of the block
        loc[0] = loc[0]*10 + 5;
        loc[1] = loc[1]*10 + 5;

        return loc;
    }

    private double computeDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
    }
    
    public void updateMap(Agent a){
        
        // grab the absolute/block location
        int x = a.getLocX(), y = a.getLocY();
        int bx = a.getBlockX(), by = a.getBlockY();
        
        // remove the last decimal place
        x = x/10;
        y = y/10;
        
        // check if we are in a different cell-block
        if(x != bx || y != by){
            
            // move the agent's field to the new location
            moveSource(a.getSource(), computeDirection(bx, by, x, y));
            a.setBlockX(x);
            a.setBlockY(y);
        }
    }
    
    private Dir computeDirection(int oldx, int oldy, int newx, int newy){
        
        // vars
        Dir d = Dir.CENTER;
        int x, y;
        x = newx - oldx;
        y = newy - oldy;
        
        if(x == 0 && y < 0)
            d = Dir.NORTH;
        else if(x > 0 && y < 0)
            d = Dir.NORTHEAST;
        else if(x > 0 && y == 0)
            d = Dir.EAST;
        else if(x > 0 && y > 0)
            d = Dir.SOUTHEAST;
        else if(x == 0 && y > 0)
            d = Dir.SOUTH;
        else if(x < 0 && y > 0)
            d = Dir.SOUTHWEST;
        else if(x < 0 && y == 0)
            d = Dir.WEST;
        else if(x < 0 && y < 0)
            d = Dir.NORTHWEST;
            
        return d;
    }
    
    private int[] computeDirection(Dir d){
        int[] vector = new int[2];
        vector[0] = 0;
        vector[1] = 0;
        
        switch(d){
            case NORTH:
                vector[1] = -1;
                break;
            case NORTHEAST:
                vector[0] = 1;
                vector[1] = -1;
                break;
            case EAST:
                vector[0] = 1;
                break;
            case SOUTHEAST:
                vector[0] = 1;
                vector[1] = 1;
                break;
            case SOUTH:
                vector[1] = 1;
                break;
            case SOUTHWEST:
                vector[0] = -1;
                vector[1] = 1;
                break;
            case WEST:
                vector[0] = -1;
                break;
            case NORTHWEST:
                vector[0] = -1;
                vector[1] = -1;
                break;
        }
        
        return vector;
    }
}
