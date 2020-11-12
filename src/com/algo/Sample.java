package com.algo;

import java.util.ArrayList;
import java.util.Random;

public class Sample {
    private CNF_sentence phi;
    private float epsilon;
    private ArrayList<Variable> markedVariableSet;
    private Random RNG;
    private int MixingTimes;
    private Assignment[] assignments;

    public Sample(CNF_sentence phi, float epsilon) {
        this.phi = phi;
        this.epsilon = epsilon;
        this.RNG = phi.getRNG();
        Moser_Tardos_A m = new Moser_Tardos_A(phi);
        markedVariableSet = m.Marking();
        MixingTimes = (int) Math.ceil(2 * phi.getVariableList().size() * Math.log(4 * phi.getVariableList().size() / epsilon));
        //System.out.println(MixingTimes);
        assignments = new Assignment[MixingTimes];
    }

    public void main_algorithm() {
        initialassign(1);
        for (int i = 0; i < MixingTimes; i++) {
            Variable v = phi.getVariableList().get(RNG.nextInt(phi.getVariableList().size()));

        }
    }


    protected void findConn(CNF_sentence phix) {
        ArrayList<ArrayList<Clause>> a = new ArrayList<>();
        ArrayList<Clause> sub_c;
        ArrayList<ArrayList<Variable>> b = new ArrayList<>();
        ArrayList<Variable> sub_v;
        while (!phix.getSentence().isEmpty()) {
            sub_c = new ArrayList<>();
            Clause c = phix.getSentence().get(0);
            sub_v = new ArrayList<>(c.getVariableList());
            sub_c.add(c);
            phix.sentence.remove(0);
            ArrayList<Clause> remove = new ArrayList<>();
/*            for (Variable v : sub_v){
                for(Clause cc : phix.sentence){
                    if(cc.contains(v)&&!sub_c.contains(cc)){
                        remove.add(cc);
                        sub_c.add(cc);
                        for (Variable vn : cc.getVariableList()){
                            if(!sub_v.contains(vn)){
                                sub_v.add(vn);
                            }
                        }
                    }
                }
            }*/
            int i = 0;
            int j = sub_v.size();
            while (i < j) {
                for (Clause cc : phix.sentence) {
                    if (cc.contains(sub_v.get(i)) && !sub_c.contains(cc)) {
                        remove.add(cc);
                        sub_c.add(cc);
                        for (Variable vn : cc.getVariableList()) {
                            if (!sub_v.contains(vn)) {
                                sub_v.add(vn);
                            }
                        }
                    }
                }
                i++;
                j = sub_v.size();
            }
            for (Clause u : remove) {
                phix.sentence.remove(u);
            }
            a.add(sub_c);
            b.add(sub_v);
        }
        System.out.println(a.size());
        System.out.println("wuhu!");
    }


    private Assignment subsample(double delta, Variable speical_v) {
        double eta = 0.25;
        CNF_sentence phix = this.simplify(0);
        this.findConn(phix);
        return null;
    }

    /**
     * @param j j-th assignment we are going to use to simplify
     * @return a simplify cnf sentence phi x
     */
    protected CNF_sentence simplify(int j) {
        ArrayList<Variable> s_vset = new ArrayList<>();
        ArrayList<Clause> s_c = new ArrayList<>();
        for (Variable v : phi.getVariableList()) {
            if (!markedVariableSet.contains(v)) {
                s_vset.add(v);
            }
        } //After forloop, we obtain the set V^x

        for (Clause c : phi.getSentence()) {
            if (assignments[j].calculateClauseValue(c) != property.TRUE) {
                Clause k = c.clone();
                for (Variable v : markedVariableSet) {
                    k.remove(v);
                }
                s_c.add(k);
            }
        }
        return new CNF_sentence(s_c, s_vset, phi.getK(), phi.getD(), phi.getRNG());
    }

    protected Assignment initialassign(int k) {
        // Uniformly assign.
        Assignment assignment = new Assignment(phi.getVariableList());
        for (Variable v : markedVariableSet) {
            if (RNG.nextDouble() < 0.5) {
                assignment.changeValue(v, property.TRUE);
            } else {
                assignment.changeValue(v, property.FALSE);
            }
        }
        //System.out.println(markedVariableSet.size());
        if (k != 0) { // Assign with moser-tardo algorithm.
            while (assignment.calculateSentenceValue(phi) != property.TRUE) {
                //System.out.println(assignment.calculateSentenceValue(phi).toString());
                for (Clause c : phi.getSentence()) {
                    if (assignment.calculateClauseValue(c) != property.TRUE) {
                        for (Variable v : c.getVariableList()) {
                            if (markedVariableSet.contains(v)) {
                                if (RNG.nextBoolean()) {
                                    assignment.changeValue(v, property.TRUE);
                                } else {
                                    assignment.changeValue(v, property.FALSE);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        this.assignments[0] = assignment;
        return assignment;
    }
}
