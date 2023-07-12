package com.sapai_4.ai.model;

public class ToneOfVoiceModel {
    private int id;
    private String toneOfVoice;

    public ToneOfVoiceModel(int id, String toneOfVoice) {
        this.id = id;
        this.toneOfVoice = toneOfVoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToneOfVoice() {
        return toneOfVoice;
    }

    public void setToneOfVoice(String toneOfVoice) {
        this.toneOfVoice = toneOfVoice;
    }
}
