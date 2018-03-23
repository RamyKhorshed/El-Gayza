package com.husseinelkheshen.quiz;

public class Questions {
    private String mQuestions[] = {
            "What is the capital of Spain?",
            "What is the capital of France?",
            "What is the capital of Germany?",
            "What is the capital of Wales?",
            "What is the capital of Egypt?"
    };

    private String mChoices[][] = {
            {"Madrid","Paris","London","Beijing"},
            {"Madrid","Paris","London","Beijing"},
            {"Madrid","Paris","Berlin","Beijing"},
            {"Madrid","Cardiff","London","Berlin"},
            {"Madrid","Paris","Cairo","Beijing"}
    };

    private String mCorrect[] = {
            "Madrid",
            "Paris",
            "Berlin",
            "Cardiff",
            "Cairo"
    };

    public String getQuestion(int i){
        String question = mQuestions[i];

        return question;
    }

    public String getChoice1(int i){
        String choice1 = mChoices[i][0];

        return choice1;
    }

    public String getChoice2(int i){
        String choice2 = mChoices[i][1];

        return choice2;
    }

    public String getChoice3(int i){
        String choice3 = mChoices[i][2];

        return choice3;
    }

    public String getChoice4(int i){
        String choice4 = mChoices[i][3];

        return choice4;
    }

    public String getCorrect(int i){
        String correct = mCorrect[i];

        return correct;
    }

    public int getLength(){
        return mQuestions.length;
    }
}
