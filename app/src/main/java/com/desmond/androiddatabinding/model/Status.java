package com.desmond.androiddatabinding.model;

import android.support.annotation.NonNull;

/**
 * Created by desmond on 17/10/15.
 */
public class Status {
    private String name;
    private String screenName;
    private String text;
    private String imageUrl;

    public Status(@NonNull String name, @NonNull String screenName, @NonNull String text, @NonNull String imageUrl) {
        this.name = name;
        this.screenName = screenName;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getText() {
        return text;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
