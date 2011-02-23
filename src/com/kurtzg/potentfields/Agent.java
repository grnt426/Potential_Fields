package com.kurtzg.potentfields;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Author:      Grant Kurtz
 *
 * Description: Base class necessary for the PFMap class and supporting
 *              classes to analyze and allow any inheriting classes to interact
 *              with the generated potential fields.
 */
public class Agent {

    // vars
    private int blockX, blockY;
    private int locx, locy, agent_id;
    private FieldSource source;
    private static int ID = 0;
    private HashMap<String, Double> fieldModifiers;

    /*
     * Default Constructor, initializes an agent in a random location,
     * provides a field for the agent to repel other objects, and tells the
     * agent to ignore it's own field
     */
    public Agent(){

        // vars
        Random generator = new Random();

        // UID info
        agent_id = ID;
        ID++;

        // location information
        blockX = generator.nextInt(40);
        blockY = generator.nextInt(40);
        locx = blockX*10+5;
        locy = blockY*10+5;

        // setup some sources
        source = new FieldSource("ID:"+agent_id);
        source.setCharge(-10);
        source.setRange(3);
        source.addType(new FieldType("ID:" + agent_id));
        source.setBlockLocation(blockX, blockY);
        fieldModifiers = new HashMap<String, Double>();
        fieldModifiers.put("ID:"+agent_id, 0.0);
    }

    public Agent(int x, int y){

        // determine block location
        blockX = x/10+5;
        blockY = y/10+5;
        this.locx = x;
        this.locy = y;

        // setup some sources
        source = new FieldSource("ID:"+agent_id);
        source.setCharge(-10);
        source.setRange(2);
        source.addType(new FieldType("ID:" + agent_id));
        source.setBlockLocation(blockX, blockY);
        fieldModifiers = new HashMap<String, Double>();
        fieldModifiers.put("ID:"+agent_id, 0.0);
    }

    public int getBlockX() {
        return blockX;
    }

    public void setBlockX(int blockX) {
        this.blockX = blockX;
    }

    public int getBlockY() {
        return blockY;
    }

    public void setBlockY(int blockY) {
        this.blockY = blockY;
    }

    public int getLocX() {
        return locx;
    }

    public void setLocX(int locx) {
        this.locx = locx;
    }

    public int getLocY() {
        return locy;
    }

    public void setLocY(int locy) {
        this.locy = locy;
    }

    public FieldSource getSource() {
        return source;
    }

    public void setSource(FieldSource fs){
        this.source = fs;
    }

    public HashMap<String, Double> getFieldModifiers(){
        return fieldModifiers;
    }

    public void addType(Charge c, FieldType ft){
        
    }
}
