package com.algo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CNF_sentence {
    private ArrayList<Clause> sentence;
    private property value = property.TRUE;
    private int k; //k-uniform CNF formula
    private int d; // each variable can appera at most d times.
    private ArrayList<Variable> VariableList;

    /**
     * Build function
     * @param k each clause can contains at most k variable
     * @param d each variable can appear at most d times
     */
    public CNF_sentence(int k ,int d, int seed){
        this.k = k;
        this.d = d;
        Sentence_generator a = new Sentence_generator(k,d,seed,20);
        sentence = a.generate();
        VariableList = a.getVariableList();
    }
    public  CNF_sentence(int k ,int d, int seed, int number_of_clauses){
        this.k = k;
        this.d = d;
        Sentence_generator a = new Sentence_generator(k,d,seed,number_of_clauses);
        sentence = a.generate();
        VariableList = a.getVariableList();
    }

    public ArrayList<Variable> getVariableList() {
        return VariableList;
    }

    public property getValue() {
        this.calculateValue();
        return value;
    }

    /**
     * function that calculate the total T/F value of the sentence.
     * @return  sentence property t/f/notassign.
     */
    public property calculateValue(){
        for (Clause c : this.sentence){
            value = value.and(c.calculateValue());
        }
        return  value;
    }

    public int getK() {
        return k;
    }


    public int getD() {
        return d;
    }


    public ArrayList<Clause> getSentence() {
        return sentence;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Clause c : this.sentence){
            s.append(c.toString());
            s.append("\n");
            s.append("∧\n");
        }
        return s.substring(0,s.length()-3);
    }
}
