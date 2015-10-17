package com.desmond.androiddatabinding.model;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;

/**
 * Created by desmond on 17/10/15.
 */
public class Status {
    private String name;
    private String screenName;
    private String text;
    private String imageUrl;
    private Status quotedStatus;
    private ObservableField<Status> observableQuotedStatus;

    public Status(@NonNull String name, @NonNull String screenName, @NonNull String text,
                  @NonNull String imageUrl, @NonNull Status quotedStatus) {
        this.name = name;
        this.screenName = screenName;
        this.text = text;
        this.imageUrl = imageUrl;
        this.quotedStatus = quotedStatus;
        observableQuotedStatus = new ObservableField<>();
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

    public boolean hasQuotedStatus() {
        return quotedStatus != null;
    }

    public void updateQuotedStatus() {
        observableQuotedStatus.set(quotedStatus);
    }

    public void clearQuotedStatus() {
        observableQuotedStatus.set(null);
    }

    public ObservableField<Status> getObservableQuotedStatus() {
        return observableQuotedStatus;
    }
}
