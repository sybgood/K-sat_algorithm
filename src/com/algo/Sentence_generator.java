package com.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Sentence_generator {
    private int number_of_clauses;
    private int number_of_variable = 0;
    private Random RNG;
    private HashMap<Variable,Integer> appeartimes = new HashMap<>();
    private int k; //k-uniform CNF formula
    private int d; // each variable can appera at most d times.
    private ArrayList<Variable> VariableList = new ArrayList<>();
    private ArrayList<Clause> ClauseList = new ArrayList<>();


    public Sentence_generator(int k, int d, int seed, int number_of_clauses){
        this.k = k;
        this.d = d;
        RNG = new Random(seed);
        this.number_of_clauses = number_of_clauses;
        this.calculateNumberOfVariable();
    }

    private void generateVariable(){
        for (int i = 0; i<number_of_variable;i++){
            Variable v = new Variable(i);
            VariableList.add(v);
            appeartimes.put(v,0);
        }
    }
    private  void generateClause(){
        for (int i = 0; i<number_of_clauses;i++){
            Clause c = new Clause(k);
            ClauseList.add(c);
        }
    }
    /**
     * Need to make sure v is in range
     * @param v index of the variable in the variable list.
     * @return  boolean value
     */
    private boolean isVariableInLimit(Variable v){
        return appeartimes.get(v) < d;
    }


    private void generateSentence(){
        // To make sure the generate is uniform, we will random select a variable, and put it into a random clauses.
        // And repeat this process until all clause is filled.
        int count = 0;
        int m,order,cluseorder,apperatime;
        int total_variable = number_of_clauses*k;
        Variable literal;
        Clause c;

        while(count<total_variable){
            //System.out.println(count);
            order = RNG.nextInt(number_of_variable); // random select a literal
            literal = VariableList.get(order);
            if(isVariableInLimit(literal)){ //this checks whether literal appear more than d times.

                boolean ok = false;
                m = 0;
                while (!ok){
                    m++;
                    cluseorder = RNG.nextInt(number_of_clauses); //random select a clause
                    c = ClauseList.get(cluseorder); //random select a clause
                    //System.out.println("m："+m+c.toString());
                    ok = c.checkInLimit() && !c.contains(literal); /* only cluase has no more than k literal, and clause
                                                                     doesn't contains such literal would be considered*/
                    if (ok){
                        if(RNG.nextDouble()<0.5){  // either negation or not.
                            c.addVariable(literal,false);
                        }
                        else c.addVariable(literal,true);

                        apperatime = appeartimes.get(literal);
                        appeartimes.put(literal,apperatime+1);
                        count ++;
                    }
                    if(d>number_of_clauses||m>=number_of_clauses) break; // avoid dead cycle
                }
            }
        }
    }

    public ArrayList<Clause> generate() {
        this.generateVariable();
        this.generateClause();
        this.generateSentence();
        return ClauseList;
    }

    private void calculateNumberOfVariable(){
        int TotalVariable = number_of_clauses*k;
        this.number_of_variable = (TotalVariable/d)+1;
        this.number_of_variable *= (1 + RNG.nextDouble()*0.5); //make more variable
    }

    public ArrayList<Variable> getVariableList() {
        return VariableList;
    }
}
