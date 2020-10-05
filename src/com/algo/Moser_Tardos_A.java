package com.algo;

import java.util.ArrayList;
// this is the first resamping algorithm
public class Moser_Tardos_A {
    private CNF_sentence sentence;
    private double kalpha;
    private double kbeta;
    private ArrayList<Variable> markedVariable = new ArrayList<>();
    private  ArrayList<Variable> unmakerdVariable;


    public Moser_Tardos_A(CNF_sentence sentence){
        this.sentence = sentence;
        unmakerdVariable = sentence.getVariableList();
    }

    private void marking (){

    }
}
