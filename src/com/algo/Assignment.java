package com.algo;

import java.util.*;

public class Assignment implements Cloneable {


    private HashMap<Variable, property> h = new HashMap<>();


    public Assignment(ArrayList<Variable> v) {
        for (Variable vv : v) {
            h.put(vv, property.NOTASSIGN);
        }
    }

    public Assignment() {

    }

    public Assignment(Assignment a) {
        h = (HashMap<Variable, property>) a.getH().clone();
    }


    public HashMap<Variable, property> getH() {
        return h;
    }

    @Override
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

    /**
     * act same as the function put in hashmap
     *
     * @param v variable
     * @param p property
     */
    public void changeValue(Variable v, property p) {
        h.put(v, p);
    }


    public property getValue(Variable v) {
        if (!this.h.containsKey(v)) {
            return property.NOTASSIGN;
        }
        return h.get(v);
    }

    public ArrayList<Variable> getVariableList() {
        ArrayList<Variable> v = new ArrayList<>(h.keySet());
        return v;
    }

    public property calculateClauseValue(Clause c) {
        property value = property.FALSE;
        ArrayList<Variable> notlist = c.getNotList();
        for (Variable v : c.getVariableList()) {
            if (notlist.contains(v)) {
                value = value.or(getValue(v).not()); // if k > 0 that means this cluase is true;
            } else {
                value = value.or(getValue(v));
            }
        }
        return value;
    }

    public property calculateClauseValueWithoutSet(Clause c, HashSet<Variable> vset) {
        property value = property.FALSE;
        ArrayList<Variable> notlist = c.getNotList();
        for (Variable v : c.getVariableList()) {
            if (!vset.contains(v)) {
                if (notlist.contains(v)) {
                    value = value.or(getValue(v).not()); // if k > 0 that means this cluase is true;
                } else {
                    value = value.or(getValue(v));
                }
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


    public boolean contains(Variable v) {
        return this.h.containsKey(v);
    }


    public void randomAssignment(Random rng) {
        Set<Map.Entry<Variable, property>> entrySet = h.entrySet();
        Iterator<Map.Entry<Variable, property>> iter = entrySet.iterator();
        while (iter.hasNext()) {
            Map.Entry<Variable, property> entry = iter.next();
            if (rng.nextBoolean()) {
                this.changeValue(entry.getKey(), property.TRUE);
            } else this.changeValue(entry.getKey(), property.FALSE);
        }
//        for (Variable v:this.h.keySet()){
//            if(rng.nextBoolean()){
//                this.changeValue(v,property.TRUE);
//            }
//            else this.changeValue(v,property.FALSE);
//        }
    }
}
