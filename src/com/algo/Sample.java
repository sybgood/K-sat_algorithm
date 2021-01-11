package com.algo;

import java.util.*;

public final class Sample {
    private final CNF_sentence phi;
    private float epsilon;
    private final int MixingTimes;
    private Random RNG;
    private final int phiLength;
    protected ArrayList<Variable> markedVariableSet;
    private Assignment assignments;
    private ArrayList<ArrayList<Clause>> hyperEdge;
    private ArrayList<ArrayList<Variable>> hyperVertex;
    private int sb = 0;

    /**
     * Construct function, will first mark variables fist.
     *
     * @param phi     phi is a CNF-sentence.
     * @param epsilon For now it is a parameter, but in fact it should be calculated by some equations in paper
     */
    public Sample(CNF_sentence phi, float epsilon, Random RNG) {
        this.phi = phi;
        this.epsilon = epsilon;
        this.RNG = RNG;
        phi.setRNG(RNG);
        Moser_Tardos_A m = new Moser_Tardos_A(phi); // First we need to mark variable.
        markedVariableSet = m.Marking();
        phiLength = phi.getVariableList().size();
        //MixingTimes = 651;
        MixingTimes = (int) Math.ceil(2 * phiLength * (Math.log(4 * phiLength / epsilon) / Math.log(2)));
        System.out.println(MixingTimes);
        //System.out.println(MixingTimes);
        /* for space saving, we are not going to record all the assignments*/
        //assignments = new Assignment[MixingTimes+1];
        assignments = new Assignment();
    }

    public final void main_algorithm() {
        initialassign(false);//initialise X_0(M)
        System.out.println("finish initial assign");
        Assignment a;
        ArrayList<Variable> vset;
        Variable v;
        for (int i = 1; i <= MixingTimes; i++) {
            v = this.markedVariableSet.get(RNG.nextInt(this.markedVariableSet.size()));
            vset = new ArrayList<>(); // choose v from M uniformly at random.
            vset.add(v);
            a = subsample(this.epsilon / (4 * (this.MixingTimes + 1)), vset, 0);
            for (Variable vv : a.getVariableList()) {
                assignments.changeValue(vv, a.getValue(vv));
            }
        }
        // the last time we sample X_v\m
        ArrayList<Variable> unmarkedVariable = new ArrayList<>();
        for (Variable vv : this.phi.getVariableList()) {
            if (!markedVariableSet.contains(vv)) {
                unmarkedVariable.add(vv);
            }
        }
        // NEED FIGURE OUT
        a = subsample(this.epsilon / (4 * (this.MixingTimes + 1)),/*SPECIAL V*/unmarkedVariable, MixingTimes);
        for (Variable vv : a.getVariableList()) {
            assignments.changeValue(vv, a.getValue(vv));
        }
        System.out.println("woohoo!");
        System.out.println(MixingTimes);
        System.out.println(assignments.calculateSentenceValue(phi));
    }

    /**
     * Find the connected components of given CNF-sentence,
     *
     * @param phix CNF sentence
     */
    protected final void Conn(CNF_sentence phix, ArrayList<Variable> special_V) {
        ArrayList<ArrayList<Clause>> a = new ArrayList<>(); // a is the hyper edge set
        ArrayList<ArrayList<Variable>> b = new ArrayList<>(); // b is the vertices for each hyper graph
        int i = 0;
        while (!phix.getSentence().isEmpty()) { // Dfs approach
            ArrayList<Clause> sub_c = new ArrayList<>();
            ArrayList<Variable> sub_v = new ArrayList<>();
            Clause c = phix.getSentence().get(0);
            HashSet<Variable> temp_v = new HashSet(c.getVariableList());
            sub_c.add(c);
            phix.getSentence().remove(0);

            ArrayList<Clause> remove = new ArrayList<>();
            do {
                for (Clause cc : remove) {
                    phix.getSentence().remove(cc);
                }
                remove.clear();
                if (phix.getSentence().isEmpty()) break;

                for (Clause cc : phix.getSentence()) {
                    for (Variable v : temp_v) {
                        if (cc.getVariableList().contains(v)) {
                            sub_c.add(cc);
                            temp_v.addAll(cc.getVariableList());
                            remove.add(cc);
                            break;
                        }
                    }
                }
            } while (!remove.isEmpty());

            for (Variable v : special_V) {
                if (temp_v.contains(v)) {
                    sub_v.addAll(temp_v);
                    b.add(sub_v);
                    a.add(sub_c);
                    break;
                }
            }
            i += temp_v.size();
        }
        sb++;
        this.hyperEdge = a;
        this.hyperVertex = b;
        System.out.println(sb);
        // System.out.println("size "+ i);
    }


    protected void findConn_without_v(CNF_sentence phix) {
        ArrayList<ArrayList<Clause>> a = new ArrayList<>(); // a is the hyper edge set
        ArrayList<Clause> sub_c;
        ArrayList<ArrayList<Variable>> b = new ArrayList<>(); // b is the vertices for each hyper graph
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
        this.hyperEdge = a;
        this.hyperVertex = b;
        System.out.println(a.size());
    }


    /**
     * This is the implementation of algorithm 4, the subroutine.
     *
     * @param delta     used for length check
     * @param speical_v selected variable
     * @param t         the t-th iteration
     * @return an assignment with size |special_v|
     */
    private Assignment subsample(double delta, ArrayList<Variable> speical_v, int t) {
        double eta = 0.25;
        HashSet<Variable> h = new HashSet<>(speical_v);
        CNF_sentence phix = this.simplify(t, speical_v);
        this.Conn(phix, speical_v);
        if (this.hyperEdge.size() == 0) {
            Assignment a = new Assignment(speical_v);
            a.randomAssignment(this.RNG);
            return a;
        }
        double s = phi.getD() * phi.getK() * (Math.log(phiLength / delta) / Math.log(2));
        for (ArrayList<Clause> c : this.hyperEdge) {
            if (c.size() > s) {
                Assignment a = new Assignment(speical_v);
                a.randomAssignment(this.RNG);
                return a;
            }
        }
        System.out.println("In");
        Assignment a = new Assignment(speical_v);
        a.randomAssignment(this.RNG);
        for (int i = 0; i < this.hyperEdge.size(); i++) {
            CNF_sentence newC = new CNF_sentence(this.hyperEdge.get(i), this.hyperVertex.get(i),
                    phi.getK(), phi.getD(), phi.getRNG());
            Assignment Y_i = rejectionSampling(newC);

            Set<Map.Entry<Variable, property>> entrySet = Y_i.getH().entrySet();
            Iterator<Map.Entry<Variable, property>> iter = entrySet.iterator();
            while (iter.hasNext()) {
                Map.Entry<Variable, property> entry = iter.next();
                if (h.contains(entry.getKey())) {
                    a.changeValue(entry.getKey(), entry.getValue());
                }
            }
//            for(Variable v :Y_i.getH().keySet()){
//                Y.changeValue(v,Y_i.getValue(v));
//            }
        }
        return a;
    }

    private Assignment rejectionSampling(CNF_sentence phix) {
        property p = property.FALSE;
        Assignment a = new Assignment(phix.getVariableList());
        int i = 0;
        while (p != property.TRUE) {
            ++i;
            a.randomAssignment(this.RNG);
            p = a.calculateSentenceValue(phix);
            if (i > 100000) {
                System.out.println("over");
                break;
            }
        }
        return a;
    }

    /**
     * This function simplies the given CNF-sentence
     * The input assignment is X(M\{v}) Thus need to take care about special_v
     *
     * @param j         Simplify the sentence based on j-th X_j
     * @param special_v this is the random selected variable set.
     * @return a simplify cnf sentence phi x
     */
    protected final CNF_sentence simplify(int j, ArrayList<Variable> special_v) {
        //System.out.println(j+"-th iteration");
        HashSet<Variable> h = new HashSet<>(special_v);
        ArrayList<Variable> s_vset = new ArrayList<>();
        ArrayList<Clause> s_c = new ArrayList<>();
        HashSet<Variable> assignments_variable = new HashSet<>(assignments.getVariableList());
        for (Variable v : phi.getVariableList()) {
            if (!assignments_variable.contains(v) || h.contains(v)) {
                s_vset.add(v);
            }
        }
        //After above code, we obtain the set V^x Now fowllowing code is going to find C^x

        for (Clause c : phi.getSentence()) { // first iterating all clause within phi.
//            if(c.contains(special_v.get(0))){
//                System.out.println("I found you~"+sb);
//                sb++;
//            }
            property p = assignments.calculateClauseValueWithoutSet(c, h);
            if (p != property.TRUE) { // If given clause doesn't satisfy
                Clause k = c.clone();

                for (Variable v : c.getVariableList()) {
                    /* Below sentence is equivalent to v in M\{v}*/
                    if (!h.contains(v) && assignments_variable.contains(v)) k.remove(v);
                }

//                for (Variable v : h){
//                    if (k.contains(v)) k.remove(v);
//                }


//                for (Variable v : c.getVariableList()) {
//                    if (!h.contains(v)) {
//                        k.remove(v);
//                    }
//                }
                s_c.add(k);
            }
        }
        return new CNF_sentence(s_c, s_vset, phi.getK(), phi.getD(), phi.getRNG());
    }


    /**
     * Initialisation function, which act line 2 on Algorithm 3. The initial sample procedure.
     *
     * @param useMorserTardo whether use Moser-tardo to do the sample. k = 0 means we use random sample
     * @return An assignment that assigns each literal a value.
     */
    protected final Assignment initialassign(boolean useMorserTardo) {
        // Uniformly assign.
        //In the initial step, we only care about marked variables.
        Assignment assignment = new Assignment(this.markedVariableSet);
        for (Variable v : markedVariableSet) {
            if (RNG.nextDouble() < 0.5) {
                assignment.changeValue(v, property.TRUE);
            } else {
                assignment.changeValue(v, property.FALSE);
            }
        }
        //System.out.println(markedVariableSet.size());
        if (useMorserTardo) { // Assign with moser-tardo algorithm.
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
        this.assignments = assignment;
        return assignment;
    }

    public Assignment getAssignments() {
        return assignments;
    }
}
