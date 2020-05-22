package com.dat257.team1.LFG.view.profileFeed;

public class Upload {
    private String mName;
    private String mImageUrl;

    public Upload() {

    }

    public Upload(String mName, String mImageUrl) {
        if (mName.trim().equals("")) {
            mName = "no name";
        }
        this.mName = mName;
        this.mImageUrl = mImageUrl;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
