package com.example.breakingbadv2.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

public class Character implements Serializable, Parcelable {
    private String TAG = this.getClass().getSimpleName();
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

    protected Character(Parcel in) {
        TAG = in.readString();
        name = in.readString();
        nickname = in.readString();
        status = in.readString();
        birthDate = in.readString();
        jobTitle = in.readString();
        participatedSeasons = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public String getName() {
        Log.d(TAG, "called getName");
        return name;
    }

    public String getNickname() {
        Log.d(TAG, "called getNickname");
        int endIndex = nickname.length() -1;
        return nickname.substring(0, endIndex);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TAG);
        dest.writeString(name);
        dest.writeString(nickname);
        dest.writeString(status);
        dest.writeString(birthDate);
        dest.writeString(jobTitle);
        dest.writeString(participatedSeasons);
        dest.writeString(imageUrl);
    }
}
