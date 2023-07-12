package com.sapai_4.ai.api;

import java.util.List;

public class CompletionResponse {
    private List<CompletionChoice> choices;

    public List<CompletionChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<CompletionChoice> choices) {
        this.choices = choices;
    }
}
