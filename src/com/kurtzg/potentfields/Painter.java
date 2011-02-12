package com.kurtzg.potentfields;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 */
public class Painter extends JPanel {

    // instance vars
    private PFMap pfm;
    private int rows, cols;
    private Dimension size;

    public Painter(){
        size = new Dimension(400, 400);
    }

    public void paint(Graphics g){

        super.paintComponent(g);
        setBackground(Color.white);

        // draw grid
        int width = 10;
        for(int r = 0; r < rows; ++r){
            for(int c = 0; c < cols; ++c){
                int sx, sy;
                FieldNode fn = pfm.getNode(r, c);
                sx = r*10;
                sy = c*10;
                int val = (int)(255*(1-fn.getTotalCharge()/100.0));
                g.setColor(new Color(val, val, 255));
                g.fillRect(sx, sy, width, width);
            }
        }
    }

    public void setPotentialFieldMap(PFMap pfm){
        this.pfm = pfm;
        rows = pfm.getRows();
        cols = pfm.getCols();
    }

    public Dimension getPreferredSize(){
        return size;
    }
}
