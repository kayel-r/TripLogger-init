package com.bignerdranch.android.triplogger;

import java.util.Date;
import java.util.UUID;

/**
 * Created by reece on 25/10/2016.
 */

public class Trip {
    private UUID mId;
    private String mTitle;
    private Date mDate;

    public Trip() {
        this(UUID.randomUUID());
    }

    public Trip(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId().toString() + ".jpg";
    }
}
