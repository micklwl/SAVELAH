package com.example.junhu.savelah.dataObjects;

public class Upload {
    private String imageUrl;

    public Upload(){}

    public Upload(String ImageUrl){
        imageUrl = ImageUrl;
    }


    public String getmImageUrl() {
        return imageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.imageUrl = mImageUrl;
    }


}
