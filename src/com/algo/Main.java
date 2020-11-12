package com.algo;

public class Main {

    public static void main(String[] args) {
        CNF_sentence s = new CNF_sentence(5, 3, 31, 30);
        //System.out.println(s.toString());
        // Moser_Tardos_A m = new Moser_Tardos_A(s);
        // ArrayList<Variable> v = m.Marking();
        Sample ss = new Sample(s, 0.25f);
        ss.initialassign(0);
        CNF_sentence k = ss.simplify(0);
        ss.findConn(k);
    }
}
