package com.algo;

import java.util.ArrayList;
import java.util.Random;

// this is the first resamping algorithm
public class Moser_Tardos_A {
    private CNF_sentence sentence;
    private double kalpha,kbeta,alpha,beta;
    private ArrayList<Variable> markedVariable = new ArrayList<>();
    private  ArrayList<Variable> unmakerdVariable;
    private int k;
    private Random RNG;

    public Moser_Tardos_A(CNF_sentence sentence){
        this.sentence = sentence;
        unmakerdVariable = sentence.getVariableList();
        RNG = sentence.getRNG();
        k = sentence.getK();
        kalpha = 1; // should be 0.1133k with floor function
        alpha = kalpha/(double)k;
        kbeta = 2; //  should be 0.5097k with floor function
        beta = kbeta/(double)k;
    }
    // We first randomly mark approximately half variables.


    private void initalMarking (){
        double probability = (1+kalpha-kbeta)/2;
        for (Variable v : unmakerdVariable){
            if (RNG.nextDouble()<probability){
                unmakerdVariable.remove(v);
                markedVariable.add(v);
            }
        }
    }

    private void confirmMarked(){

    }


    public boolean badEvent(){
        for (Clause c:sentence.getSentence()){

        }
        return false;
    }

    public ArrayList<Variable> MoserTardoMarking(){
        return  null;
    }

}
