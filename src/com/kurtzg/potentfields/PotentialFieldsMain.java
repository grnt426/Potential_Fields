package com.kurtzg.potentfields;

import javax.swing.*;
import java.util.Scanner;

/*
*
* Author:      Grant Kurtz
*
* Description: Initializes the Potential Field Map and supporting classes
*              through command line arguments.
*/
public class PotentialFieldsMain {

    public static void main(String[] args){

        // read in arguments
        // for now, they will be substituted with hard coded values
        int rows = 40, cols = 40;

        // create new map
        PFMap map = new PFMap(rows, cols);

        // create some sources
        FieldSource fs = new FieldSource("Test");
        fs.setCharge(40);
        fs.setRange(15);
        map.createSource(20, 20, fs);
        fs = new FieldSource("new test");
        fs.setCharge(20);
        fs.setRange(6);
        map.createSource(10, 10, fs);

        // instantiate paint class
        Painter p = new Painter();
        p.setPotentialFieldMap(map);
        JFrame frame = new JFrame();
        frame.setTitle("Potential Field Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(p);
        p.repaint();
        frame.pack();
        frame.setVisible(true);

        Scanner s = new Scanner(System.in);
        String str;
        while((str = s.nextLine()) != null){
            int x, y;
            String[] coords = str.split(" ");
            x = Integer.parseInt(coords[0]);
            y = Integer.parseInt(coords[1]);
            System.out.println(map.getNode(x, y).getTotalCharge());
        }
    }

    /*
     * Default Constructor
     */
    public PotentialFieldsMain(){

    }
}
