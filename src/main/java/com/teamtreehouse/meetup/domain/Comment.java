package com.teamtreehouse.meetup.domain;

import java.util.Date;

import com.teamtreehouse.meetup.integration.Gravatar;

public class Comment {
    private String mMessage;
    private String mPoster;
    private String mPosterEmail;
    private Date mPostedDate;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getPoster() {
        return mPoster;
    }

    public void setPoster(String mPoster) {
        this.mPoster = mPoster;
    }

    public String getPosterAvatar() {
        return Gravatar.urlFor(getPosterEmail());
    }

    public Date getPostedDate() {
        return mPostedDate;
    }

    public void setPostedDate(Date mPostedDate) {
        this.mPostedDate = mPostedDate;
    }

    public String getPosterEmail() {
        return mPosterEmail;
    }

    public void setPosterEmail(String mPosterEmail) {
        this.mPosterEmail = mPosterEmail;
    }
}
