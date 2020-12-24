package com.algo;

public class Main {

    public static void main(String[] args) {
        CNF_sentence s = new CNF_sentence(5, 5, 20, 1000);
        //System.out.println(s.toString());
        // Moser_Tardos_A m = new Moser_Tardos_A(s);
        // ArrayList<Variable> v = m.Marking();
        System.out.println("finish create");
        Sample ss = new Sample(s, 0.25f);
        ss.main_algorithm();
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
