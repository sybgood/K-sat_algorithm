package com.algo;
import java.util.List;
import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {
	// write your code here
        CNF_sentence s = new CNF_sentence(3,2,1,20);
        System.out.println(s.toString());
        System.out.println(s.calculateValue());

    }
}
