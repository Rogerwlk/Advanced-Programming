package com.example.lkwan.chineseflashcards;

import android.support.annotation.NonNull;

/**
 * Created by Roger Wang on 4/20/2017.
 */

public class Word implements Comparable<Word> {
    private String mMeaning;
    private String mCharacter;
    private String mPinyin;
    private byte mPreference = 0;
    private int mAudioID = NOT_PROVIDED;
    private static final int NOT_PROVIDED = -1;

    public Word(String meaning, String character, String pinyin) {
        mMeaning = meaning;
        mCharacter = character;
        mPinyin = pinyin;
    }

    public Word(String meaning, String character, String pinyin, int audioID) {
        mMeaning = meaning;
        mCharacter = character;
        mPinyin = pinyin;
        mAudioID = audioID;
    }

    public String getMeaning() {
        return mMeaning;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public String getPinyin() {
        return mPinyin;
    }

    public int getAudioResourceId() {
        return mAudioID;
    }

    public boolean hasAudio() {
        return mAudioID != NOT_PROVIDED;
    }

    public void like() {
        mPreference++;
    }

    public void dislike() {
        mPreference--;
    }

    public void answerCorrect() {
        mPreference -= 2;
    }

    public void answerIncorrect() {
        mPreference += 2;
    }

    public void setPreference(byte preference) {
        mPreference = preference;
    }

    public byte getPreference() {
        return mPreference;
    }

    public int compareTo(@NonNull Word oth) {
        return mPreference - oth.mPreference;
    }
}
