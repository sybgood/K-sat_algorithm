package com.algo;
import java.awt.desktop.SystemSleepEvent;
import java.util.List;
import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {
	// write your code here
        CNF_sentence s = new CNF_sentence(5,3,31,20);
        System.out.println(s.toString());
        Moser_Tardos_A m = new Moser_Tardos_A(s);
        ArrayList<Variable> v = m.Marking();
        for (Variable ss:v) {
            System.out.println(ss.getName());
        }
    }
}
