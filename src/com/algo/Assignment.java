package com.algo;

import java.util.ArrayList;
import java.util.HashMap;

public class Assignment implements Cloneable {


    private HashMap<Variable, property> h = new HashMap<>();


    public Assignment(ArrayList<Variable> v) {
        for (Variable vv : v) {
            h.put(vv, property.NOTASSIGN);
        }
    }


    public Assignment(Assignment a) {
        h = (HashMap<Variable, property>) a.getH().clone();
    }


    public HashMap<Variable, property> getH() {
        return h;
    }


    public Assignment clone() {
        Assignment o = null;
        try {
            o = (Assignment) super.clone();
            o.h = (HashMap<Variable, property>) this.h.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }


    public void changeValue(Variable v, property p) {
        h.put(v, p);
    }


    public property getValue(Variable v) {
        return h.get(v);
    }

    public ArrayList<Variable> getVariableList() {
        ArrayList<Variable> v = new ArrayList<>();
        v.addAll(h.keySet());
        return v;
    }

    public property calculateClauseValue(Clause c) {
        property value = property.FALSE;
        ArrayList<Variable> notlist = c.getNotList();
        for (Variable v : c.getVariableList()) {
            if (notlist.contains(v)) {
                value = value.or(h.get(v).not()); // if k > 0 that means this cluase is true;
            } else {
                value = value.or(h.get(v));
            }
        }
        return value;
    }


    private void printassignment() {
        for (Variable v : h.keySet()) {
            if (h.get(v) != property.NOTASSIGN) {
                System.out.println(v.getName() + " " + h.get(v));
            }
        }
    }


    public property calculateSentenceValue(CNF_sentence s) {
        property value = property.TRUE;
        for (Clause c : s.getSentence()) {
            value = value.and(this.calculateClauseValue(c));
            if (value == property.FALSE) {
                //System.out.println(c.toString());
                //this.printassignment();
                return value;
            }
        }
        return value;
    }
}
