package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 */
public class Agent {

    // vars
    private int blockX, blockY;
    private int locx, locy;
    private FieldSource source;

    public Agent(){
        blockX = 5;
        blockY = 3;
        locx = 55;
        locy = 35;
        source = new FieldSource();
        source.setCharge(-30);
        source.setRange(2);
    }

    public Agent(boolean test){
        blockX = 7;
        blockY = 2;
        locx = 75;
        locy = 25;
        source = new FieldSource();
        source.setCharge(-30);
        source.setRange(2);
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
}
