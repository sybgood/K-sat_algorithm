package com.algo;

public class Variable {
    private int poperty = 0;
    private String name = "p";
    private int no;


    public void setName(int a) {
        this.name = name + a;
    }

    public Variable(int no){
        setName(no);
        this.no = no;
    }

    public void setPoperty(int a){
        this.poperty = a;
    }

    public int getPoperty() {
        return poperty;
    }

    public int getNo() {
        return no;
    }

    public String getName() {
        return name;
    }
}
