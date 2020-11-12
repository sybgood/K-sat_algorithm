package com.algo;

import java.util.ArrayList;
import java.util.List;

public class Clause implements Cloneable {
    private List<Variable> variableList = new ArrayList<>();
    private ArrayList<Variable> notList = new ArrayList<>();
    private int limit;
    protected int NumberOfMarkedVariable = 0;

    public Clause(int k) {
        limit = k;
    }


    public void addNumber() {
        this.NumberOfMarkedVariable++;
    }


    public void minusNumber() {
        this.NumberOfMarkedVariable--;
    }


    public List<Variable> getVariableList() {
        return variableList;
    }


    public void remove(Variable v) {
        variableList.remove(v);
        notList.remove(v);
    }

    public Clause clone() {
        Clause o = null;
        try {
            o = (Clause) super.clone();
            o.variableList = new ArrayList<>(this.variableList);
            o.notList = new ArrayList<>(this.notList);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
    /**
     *  Calculate the value of this clause.
     */
   /* public property calculateValue(){
        value = property.FALSE;
        for (Variable v: variableList) {
            if (!notList.contains(v)) {
                value = value.or(v.getPoperty()); // if k > 0 that means this cluase is true;
            }
            else{
                value = value.or(v.getPoperty().not());
            }
        }
            return value;
    }*/

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
            //this.calculateValue();
        }
        return  this.variableList;
    }

    /**
     * This function will calculate clause value first
     * @return the value of the clause.
     */
/*    public property getValue() {
        this.calculateValue();
        return value;
    }*/
    public boolean checkInLimit() {
        return variableList.size() < limit;
    }

    public ArrayList<Variable> getNotList() {
        return notList;
    }

    public boolean contains(Variable v) {
        return variableList.contains(v);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Variable v : variableList) {
            if (notList.contains(v)) {
                s.append("¬");
            }
            s.append(v.getName());
            s.append(" ∨ ");
        }
        if(s.length()>3) return s.substring(0,s.length()-3);
        else return "";
    }
}
