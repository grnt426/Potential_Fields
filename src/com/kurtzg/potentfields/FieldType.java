package com.kurtzg.potentfields;

/**
 * Author:      Grant Kurtz
 */
public class FieldType implements Comparable<FieldType>{

    // vars
    private String name;

    public FieldType(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }

    public boolean equals(Object o){

        System.out.print("dfsdf");

        FieldType ft = (FieldType) o;
        return ft.toString().equals(name);
    }

    public int compareTo(FieldType o) {
        return o.toString().compareTo(name);
    }
}
