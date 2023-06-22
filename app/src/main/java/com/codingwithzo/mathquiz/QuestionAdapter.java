package com.codingwithzo.mathquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questions;

    public QuestionAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question question = questions.get(position);
        holder.bind(question);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView textQuestion;
        Button buttonOption1;
        Button buttonOption2;
        Button buttonOption3;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            textQuestion = itemView.findViewById(R.id.textQuestion);
            buttonOption1 = itemView.findViewById(R.id.buttonOption1);
            buttonOption2 = itemView.findViewById(R.id.buttonOption2);
            buttonOption3 = itemView.findViewById(R.id.buttonOption3);
        }

        public void bind(Question question) {
            textQuestion.setText(question.getFirstNumber() + " " + question.getOperation() + " " + question.getSecondNumber() + " = ?");
            buttonOption1.setText(String.valueOf(question.getOptions().get(0)));
            buttonOption2.setText(String.valueOf(question.getOptions().get(1)));
            buttonOption3.setText(String.valueOf(question.getOptions().get(2)));
        }
    }
}

