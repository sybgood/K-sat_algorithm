package com.algo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Clause {
    private List<Variable> variableList = new ArrayList<Variable>();
    private int value = 0;
    private ArrayList<Variable> notList = new ArrayList<Variable>();
    private int limit = 0;

    public Clause(int k) {
        limit =  k;
    }


    /**
     *  Calculate the value of this clause.
     */
    public void calculateValue(){
        int k = 0;
        for (Variable v: variableList) {
            if (!notList.contains(v)) {
                k += v.getPoperty(); // if k > 0 that means this cluase is true;
            }
            else{
                if (v.getPoperty()>0){
                    k+=0;
                }
                else k+=1;
            }
        }

        if (k>0){
            this.value = 1;
        }
        else this.value = 0;
    }

    /**
     *
     * @param v add variable v to this clause, and then recompute the value of the clause.
     * @param isNot state whether the input variable is in negation.
     * @return A list that this clause contains.
     */
    public List<Variable> addVariable(Variable v, boolean isNot){
        if (this.checkInLimit()) {
            this.variableList.add(v);
            if (isNot) notList.add(v);
            this.calculateValue();
        }
        return  this.variableList;
    }

    /**
     * This function will calculate clause value first
     * @return the value of the clause.
     */
    public int getValue() {
        this.calculateValue();
        return value;
    }

    public boolean checkInLimit(){
        return variableList.size()<limit;
    }


    public boolean contains(Variable v){
        return variableList.contains(v);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Variable v: variableList){
            if(notList.contains(v)){
                s.append("¬");
            }
            s.append(v.getName());
            s.append(" ∨ ");
        }
        if(s.length()>3) return s.substring(0,s.length()-3);
        else return "";
    }
}
