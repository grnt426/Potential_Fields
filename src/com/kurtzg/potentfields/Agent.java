package com.kurtzg.potentfields;

import com.sun.xml.internal.fastinfoset.tools.FI_DOM_Or_XML_DOM_SAX_SAXEvent;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Author:      Grant Kurtz
 */
public class Agent {

    // vars
    private int blockX, blockY;
    private int locx, locy, agent_id;
    private FieldSource source;
    private static int ID = 0;
    private HashMap<String, Double> fieldModifiers;

    public Agent(){

        Random generator = new Random();


        agent_id = ID;
        ID++;
        blockX = generator.nextInt(80);
        blockY = generator.nextInt(80);
        locx = blockX*5+2;
        locy = blockY*5+2;
        source = new FieldSource("ID:"+agent_id);
        source.setCharge(-10);
        source.setRange(2);
        source.addType(new FieldType("ID:" + agent_id));
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

    public HashMap<String, Double> getFieldModifiers(){
        return fieldModifiers;
    }
}
