package com.algo;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        CNF_sentence s = new CNF_sentence(5, 5, 12, 1000);
        //System.out.println(s.toString());
        // Moser_Tardos_A m = new Moser_Tardos_A(s);
        // ArrayList<Variable> v = m.Marking();
        System.out.println("finish create");
        long startMili1 = System.currentTimeMillis();// 当前时间对应的毫秒数
        ArrayList<property> o = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            Random RNG = new Random(i);
            Sample ss = new Sample(s, 0.25f, RNG);
            ss.main_algorithm();
            o.add(ss.getAssignments().calculateSentenceValue(s));
        }
        for (property p : o) {
            System.out.println(p);
        }
//        Sample ss = new Sample(s, 0.25f,s.getRNG());
//        long endMili1=System.currentTimeMillis();
//        long startMili=System.currentTimeMillis();// 当前时间对应的毫秒数
//        ss.main_algorithm();
//        long endMili=System.currentTimeMillis();
//        System.out.println("Moser tardo time used "+(endMili1-startMili1)+"ms");
//        System.out.println("sample time used "+(endMili-startMili)+"ms");
        //helpgenerator();
    }

    public static void helpgenerator() {
        int i = 4;
        while (true) {
            double k = Combination(i, 1) + Combination(i, 2) + Combination(i, 3);
            k /= Math.pow(2, i);
            k *= 2.71828;
            k *= i;

            if (k <= 1) {
                System.out.println(i);
                break;
            }
            i++;
        }
    }

    private static int Combination(int n, int k) {
        if (k == 0 || k == n)
            return 1;
        else
            return Combination(n - 1, k) + Combination(n - 1, k - 1);
    }
}
