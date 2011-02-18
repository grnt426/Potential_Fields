package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 *
 * Description: Just a container class for the type of field being stored.
 *              Not sure if this is actually needed.
 */
public class FieldType implements Comparable<FieldType>{

    // vars
    private String name;
    private int range;

    public FieldType(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public int getRange(){
        return range;
    }

    public void setRange(int r){
        range = r;
    }

    /*
     * I wish Java's HashMap would call this function, rendering this class
     * useless.
     */
    public boolean equals(Object o){
        FieldType ft = (FieldType) o;
        return ft.toString().equals(name);
    }

    /*
     * I don't if Java calls this either, really, this class is COMPLETELY
     * useless without these functions...
     */
    public int compareTo(FieldType o) {
        return o.toString().compareTo(name);
    }
}
