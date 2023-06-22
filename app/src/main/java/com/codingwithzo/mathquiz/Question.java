package com.codingwithzo.mathquiz;

import java.util.List;

public class Question {
    private int firstNumber;
    private int secondNumber;
    private String operation;
    private int rightAnswer;
    private List<Integer> options;
    private int position;

    public Question(int firstNumber, int secondNumber, String operation, int rightAnswer, List<Integer> options, int position) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.operation = operation;
        this.rightAnswer = rightAnswer;
        this.options = options;
        this.position = position;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public String getOperation() {
        return operation;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public List<Integer> getOptions() {
        return options;
    }

    public int getPosition() {
        return position;
    }
}

