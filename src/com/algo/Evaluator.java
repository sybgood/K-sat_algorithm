package com.algo;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Evaluator {
    float epsilon = 0.25f;
    ArrayList<property> o = new ArrayList<>();
    ArrayList<Assignment> assignments = new ArrayList<>();
    CNF_sentence sen;

    public Evaluator(CNF_sentence s, int max_iteration) {
        sen = s;
        for (int i = 0; i < max_iteration; i++) {
            CNF_sentence sss = s.clone();
            Random RNG = new Random(i);
            Sample ss = new Sample(sss, epsilon, RNG);
            ss.main_algorithm();
            o.add(ss.getAssignments().calculateSentenceValue(s));
            if (ss.getAssignments().calculateSentenceValue(s) == property.TRUE) {
                assignments.add(ss.getAssignments());
            }
        }
    }

    public void evaluate() {
        if (assignments.size() > 0) {
            String s = this.calculate(sen);
            writeInComputer(s, "graph");
        } else {
            System.out.println("error!");
        }
    }

    private String calculate(CNF_sentence sentence) {
        StringBuilder s = new StringBuilder();
        Assignment a;
        HashMap<Variable, Double> h = new HashMap<Variable, Double>();
        HashMap<Variable, Double> mse = new HashMap<Variable, Double>();

        for (Variable v : sentence.getVariableList()) {
            h.put(v, 0.0);
            mse.put(v, 0.0);
            for (int i = 0; i < assignments.size(); i++) {
                switch (assignments.get(i).getValue(v)) {
                    case TRUE:
                        h.put(v, h.get(v) + 1);
                        break;
                    case FALSE:
                        break;
                    case NOTASSIGN:
                        h.put(v, h.get(v) + 0.5);
                        break;
                    default:
                }
            }

            h.put(v, h.get(v) / assignments.size());

            for (int i = 0; i < assignments.size(); i++) {
                switch (assignments.get(i).getValue(v)) {
                    case TRUE:
                        mse.put(v, mse.get(v) + Math.pow(1 - h.get(v), 2));
                        break;
                    case FALSE:
                        mse.put(v, mse.get(v) + Math.pow(0 - h.get(v), 2));
                        break;
                    case NOTASSIGN:
                        break;
                    default:
                }
            }
            s.append(v.toString() + " True probability: " + h.get(v) + " Standard deviation " +
                    +Math.sqrt(mse.get(v)) + "\n");

        }
        Double l = 0.0;
        for (Double v : h.values()) {
            l += v;
        }
        l /= h.size();
        s.append("Average probability to be assigned true is " + l);
        return s.toString();
    }


    public void setEpsilon(float epsilon) {
        this.epsilon = epsilon;
    }


    public void writeInComputer(String contents, String title) {
        FileOutputStream bos;
        try {
            bos = new FileOutputStream(title);
            BufferedOutputStream buff = new BufferedOutputStream(bos);
            buff.write(contents.getBytes());
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
