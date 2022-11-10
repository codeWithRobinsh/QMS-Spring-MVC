package com.epam.quizproject.util;

import java.util.Scanner;

public class Utility {
    private static Utility utility;
    private static Scanner sc;
    private Utility(){

    }
    public static Utility getInstance(){
        if(utility == null)
            utility = new Utility();
        return utility;
    }
    public static Scanner getScanner(){
        if(sc == null)
            sc = new Scanner(System.in);
        return sc;
    }
}
