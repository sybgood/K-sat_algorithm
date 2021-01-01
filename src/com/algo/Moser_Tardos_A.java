package com.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// this is the first resamping algorithm
public final class Moser_Tardos_A {
    private CNF_sentence sentence;
    private double kalpha, kbeta, alpha, beta;
    private ArrayList<Variable> markedVariable = new ArrayList<>();
    private final double probability;
    private int k;
    private Random RNG;
    private ArrayList<Variable> unmakerdVariable;


    public Moser_Tardos_A(CNF_sentence sentence) {
        this.sentence = sentence;
        unmakerdVariable = new ArrayList<>(sentence.getVariableList());
        RNG = sentence.getRNG();
        k = sentence.getK();
        kalpha = 2; // should be 0.1133k with floor function
        alpha = kalpha / (double) k;
        kbeta = 1; //  should be 0.5097k with floor function
        beta = kbeta / (double) k;
        probability = (1 + 0.1133 - 0.5097) / 2; // (1+alpha-beta)/2
        //System.out.println(probability);
    }
    // We first randomly mark approximately half variables.


    private void initalMarking (){
        for (Variable v : sentence.getVariableList()){
            if (RNG.nextDouble()<probability){
                unmakerdVariable.remove(v);
                markedVariable.add(v);
                // Record number of marked variables in each clause.
                markVariableAdd(v);
            }
        }
    }


    private void markVariableAdd(Variable v){
        for (int i = 0; i < v.clauseList.length; i++) {
            int clauseOrder = v.clauseList[i];
            if (clauseOrder != -1) {
                this.sentence.sentence.get(clauseOrder).addNumber();
            }
        }
    }


    private void markVariableMinus(Variable v){
        for (int i = 0; i < v.clauseList.length; i++) {
            int clausorder = v.clauseList[i];
            if (clausorder != -1) {
                this.sentence.sentence.get(clausorder).minusNumber();
            }
        }
    }


    public List<Integer> badEvent(){
        ArrayList<Integer> n = new ArrayList<>();
        for (int i = 0; i< sentence.sentence.size(); i++){
            Clause c = sentence.sentence.get(i);
            if (c.NumberOfMarkedVariable < kalpha || k - c.NumberOfMarkedVariable < kbeta) n.add(i); // add bad event
        }
        return n;
    }

    public ArrayList<Variable> Marking(){
        initalMarking();

        List<Integer> badE = badEvent();
        int m = 0;
        while (!badE.isEmpty()){
            Clause c = sentence.sentence.get(badE.get(RNG.nextInt(badE.size())));
            for(Variable v:c.getVariableList()){

                if(unmakerdVariable.contains(v)){
                    if (RNG.nextDouble()<probability){
                        unmakerdVariable.remove(v);
                        markedVariable.add(v);
                        markVariableAdd(v);
                    }
                } else {
                    if (RNG.nextDouble() > probability) {
                        markedVariable.remove(v);
                        unmakerdVariable.add(v);
                        markVariableMinus(v);
                    }
                }
            }
            if (m == 100000) {
                System.out.println("danger");
            }
            System.out.println(m++);
            badE = badEvent();
            System.out.println("finding bad event, bad event count" + badE.size());
        }
        return  markedVariable;
    }

}
