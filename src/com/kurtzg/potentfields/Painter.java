package com.kurtzg.potentfields;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Author:      Grant Kurtz
 *
 * Description: Support class used to paint the Potential Field Map generated
 *              by all sources
 */
public class Painter extends JPanel {

    // instance vars
    private PFMap pfm;
    private int rows, cols;
    private Dimension size;
    private ArrayList<Agent> agents;

    /*
     * Default constructor, just initializes the panels size
     */
    public Painter(){
        size = new Dimension(400, 400);
        agents = new ArrayList<Agent>();
    }

    public void paint(Graphics g){

        // let the panel have a reference of our painted picture
        super.paintComponent(g);
        setBackground(Color.white);

        // draw grid
        int width = 10;
        for(int r = 0; r < rows; ++r){
            for(int c = 0; c < cols; ++c){

                // vars
                int sx, sy;
                FieldNode fn = pfm.getNode(r, c);
                sx = r*width;
                sy = c*width;

                // compute the charge value as a percentage
                int val = (int)(255*(1-fn.getTotalCharge()/100.0));

                // compute the charge
                g.setColor(new Color(val, val, 255));

                // draw the cell
                g.fillRect(sx, sy, width, width);
            }
        }

        // draw all our agents
        for(Agent a : agents){

            // set the color of the agent
            g.setColor(Color.black);

            // setup vars for drawing
            int x, y, radius;
            radius = 4;
            x = a.getLocX()-2;
            y = a.getLocY()-2;

            // finally, draw our little agent
            g.fillOval(x, y, radius, radius);
        }
    }

    /*
     * Stores a reference to the map and the rows/cols the map represents
     *
     * @param       pfm         the map itself
     */
    public void setPotentialFieldMap(PFMap pfm){
        this.pfm = pfm;
        rows = pfm.getRows();
        cols = pfm.getCols();
    }

    public void setAgents(ArrayList<Agent> agents){
        this.agents = agents;
    }

    public Dimension getPreferredSize(){
        return size;
    }
}
