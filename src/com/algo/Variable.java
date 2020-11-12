package com.algo;


public class Variable {
//    private property p = property.NOTASSIGN;
    private String name = "p";
    private int no;
    protected int[] clauseList;

    public void setName(int a) {
        this.name = name + a;
    }

    public Variable(int no){
        setName(no);
        this.no = no;
    }
/*
    public void setPoperty(property a){
        this.p = a;
    }

    public property getPoperty() {
        return p;
    }*/

    public int getNo() {
        return no;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }
}
