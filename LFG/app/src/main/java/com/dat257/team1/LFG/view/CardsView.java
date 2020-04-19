package com.dat257.team1.LFG.view;

public class CardsView {
    private int mImageResource;
    private String mTitle;
    private String mDescription;

    public CardsView(int imageResource, String title, String description){

        mImageResource = imageResource;
        mTitle = title;
        mDescription = description;

    }

    public int getImageResource(){
        return mImageResource;
    }




    public String getTitle(){

        return mTitle;


    }

    public String getDescription(){
        return mDescription;
    }


}
