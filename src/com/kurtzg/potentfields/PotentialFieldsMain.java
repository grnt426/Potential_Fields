package com.kurtzg.potentfields;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

/*
*
* Author:      Grant Kurtz
*
* Description: Initializes the Potential Field Map, test Agents, a painter
*               class for debugging the field, and other classes for testing
*/
public class PotentialFieldsMain {

    // instance vars
    JFrame frame;
    Painter p;
    ArrayList<Agent> agents;
    PFMap map;

    public static void main(String[] args){

        // read in arguments
        // for now, they will be substituted with hard coded values
        int rows = 40, cols = 40;

        new PotentialFieldsMain(rows, cols);
    }

    /*
     * Default Constructor
     *
     * @param:      rows        number of PF cells horizontally in the whole
     *                          map
     * @param:      cols        number of PF cells vertically in the whole map
     */
    public PotentialFieldsMain(int rows, int cols){

        // create new map
        map = new PFMap(rows, cols);
        agents = new ArrayList<Agent>();

        // create some agents
        for(int i = 0; i < 20; ++i){
            Agent a = new Agent();
            agents.add(a);
            map.addAgent(a);
        }

        // create some sources
        FieldSource fs1 = new FieldSource("Test");
        fs1.setCharge(62);
        fs1.setRange(40);
        fs1.setBlockLocation(20, 20);
        fs1.addType(new FieldType("Team2"));
        //Agent a = new Agent()
        map.createSource(fs1);
        FieldSource fs;
        fs = new FieldSource("new test");
        fs.setBlockLocation(7, 8);
        fs.setCharge(20);
        fs.setRange(6);
        fs.addType(new FieldType("Team2"));
        map.createSource(fs);

        // instantiate paint class
        p = new Painter();
        p.setPotentialFieldMap(map);
        p.setAgents(agents);

        // create the window
        frame = new JFrame();
        frame.setTitle("Potential Field Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(p);
        p.repaint();
        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

        // set our agents moving
        Thread t = new Thread(new frameKeeper());
        t.start();
        for(Agent a : agents){
            t = new Thread(new agentMover(a));
            t.start();
        }

        /*
         * The below is used for debugging
         */
        Scanner s = new Scanner(System.in);
        String str;
        while((str = s.nextLine()) != null){
            if(str.equals("r")){
                //map.removeSource(fs1);
                p.repaint();
            }
            else if(str.substring(0, 3).equals("dir")){
                Dir d;
                String dir = str.substring(4, str.length());
                if(dir.equalsIgnoreCase("UP"))
                    d = Dir.NORTH;
                else if(dir.equalsIgnoreCase("NE"))
                    d = Dir.NORTHEAST;
                else if(dir.equalsIgnoreCase("RIGHT"))
                    d = Dir.EAST;
                else if(dir.equalsIgnoreCase("SE"))
                    d = Dir.SOUTHEAST;
                else if(dir.equalsIgnoreCase("DOWN"))
                    d = Dir.SOUTH;
                else if(dir.equalsIgnoreCase("SW"))
                    d = Dir.SOUTHWEST;
                else if(dir.equalsIgnoreCase("LEFT"))
                    d = Dir.WEST;
                else
                    d = Dir.NORTHWEST;

                //map.moveSource(fs1, d);
                p.repaint();
            }
            else if(str.substring(0, 4).equals("near")){
                int x, y;
                x = Integer.parseInt(str.substring(5, 7));
                y = Integer.parseInt(str.substring(8, 10));
                //int[] newLoc = map.getNextBlock(x, y);
                //System.out.println("Goto: " + newLoc[0] + ", " + newLoc[1]);
            }
            else{
                String[] coords = str.split(" ");
                System.out.println(map.getNode(coords[0],
                        coords[1]).getTotalCharge());
            }
        }
    }

    /*
     * A test class used for moving agents across the field
     */
    public class agentMover implements Runnable{

        // vars
        Agent a;
        int goalX, goalY;

        /*
         * Default constructor
         */
        public agentMover(Agent a){
            this.a = a;
            goalX = a.getLocX();
            goalY = a.getLocY();
        }

        public void run(){

            while(true){

                // artificially slow the agent so we can see what is going on
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException ie){

                }

                // check if we have reached the goal
                if(a.getLocX() == goalX && a.getLocY() == goalY)
                        getNewGoalLocation();

                // grab the agent's current location
                int locx = a.getLocX(), locy = a.getLocY();

                // move our agent by one pixel along the x/y axis
                if(locx < goalX)
                    a.setLocX(locx + 1);
                else if(locx > goalX)
                    a.setLocX(locx - 1);
                if(locy < goalY)
                    a.setLocY(locy + 1);
                else if(locy > goalY)
                    a.setLocY(locy - 1);

                // update our potential field (as needed)
                map.updateMap(a);
            }
        }

        /*
         * Asks the PFMap class for information on where this agent should
         * move to next
         */
        public void getNewGoalLocation(){
            int[] newloc = map.getNextBlock(a);
            goalX = newloc[0];
            goalY = newloc[1];
        }
    }

    /*
     * Just asks the painter class to repaint every ~24FPS
     */
    public class frameKeeper implements Runnable{

        public void run(){
            while(true){
                try{
                    Thread.sleep(42);
                    p.repaint();
                }
                catch(InterruptedException ie){

                }
            }
        }
    }
}
