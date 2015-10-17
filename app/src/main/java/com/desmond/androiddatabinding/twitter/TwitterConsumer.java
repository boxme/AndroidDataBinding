package com.desmond.androiddatabinding.twitter;

import java.util.List;

import twitter4j.Status;

/**
 * Created by desmond on 17/10/15.
 */
public interface TwitterConsumer {
    void addToHomeStream(List<Status> newStatuses);

    void onConnected();

    void onError(Exception e);
}
