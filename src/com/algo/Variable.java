package com.algo;


import java.util.ArrayList;
import java.util.List;

public class Variable {
    private property p = property.NOTASSIGN;
    private String name = "p";
    private int no;
    protected ArrayList<Integer> clauseList;

    public void setName(int a) {
        this.name = name + a;
    }

    public Variable(int no){
        setName(no);
        this.no = no;
    }

    public void setPoperty(property a){
        this.p = a;
    }

    public property getPoperty() {
        return p;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}
