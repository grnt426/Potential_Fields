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
        FieldSource fs1 = new FieldSource("Test");
        fs1.setCharge(40);
        fs1.setRange(10);
        map.createSource(20, 20, fs1);
        FieldSource fs;
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
            if(str.equals("r")){
                map.removeSource(fs1);
                p.repaint();
            }
            else{
                String[] coords = str.split(" ");
                System.out.println(map.getNode(coords[0],
                        coords[1]).getTotalCharge());
            }
        }
    }

    /*
     * Default Constructor
     */
    public PotentialFieldsMain(){

    }
}
