package com.example.breakingbadv2.domain;

import android.util.Log;

import java.io.Serializable;

public class Character implements Serializable {
    private final String TAG = this.getClass().getSimpleName();
    private String name;
    private String nickname;
    private String status;
    private String birthDate;
    private String jobTitle;
    private String participatedSeasons;
    private String imageUrl;

    public Character(String name, String nickname, String status, String birthDate,
                     String jobTitle, String participatedSeasons, String imageUrl) {
        this.name = name;
        this.nickname = nickname;
        this.status = status;
        this.birthDate = birthDate;
        this.jobTitle = jobTitle;
        this.participatedSeasons = participatedSeasons;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        Log.d(TAG, "called getName");
        return name;
    }

    public String getNickname() {
        Log.d(TAG, "called getNickname");
        return nickname;
    }

    public String getStatus() {
        Log.d(TAG, "called getStatus");
        return status;
    }

    public String getBirthDate() {
        Log.d(TAG, "called getBirthDate");
        return birthDate;
    }

    public String getJobTitle() {
        Log.d(TAG, "called getJobTitle");
        return jobTitle;
    }

    public String getParticipatedSeasons() {
        Log.d(TAG, "called getParticipatedSeasons");
        return participatedSeasons;
    }

    public String getImageUrl() {
        Log.d(TAG, "called getImageUrl");
        return imageUrl;
    }
}
