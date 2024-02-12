package com.gdsc.knu.util;

public class RandomNickGenerator {
    private static final String[] adjectives = {"happy", "sad", "angry", "funny", "serious", "crazy", "lazy", "smart", "dumb", "cool"};
    private static final String[] nouns = {"dog", "cat", "bird", "fish", "rabbit", "turtle", "hamster", "parrot", "snake", "lizard"};

    public static String generate(){
        int adjIndex = (int)(Math.random() * adjectives.length);
        int nounIndex = (int)(Math.random() * nouns.length);
        return adjectives[adjIndex] + "_" + nouns[nounIndex] + "_" + (int)(Math.random() * 100);
    }
}
