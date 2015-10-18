package com.desmond.androiddatabinding.twitter;

import com.desmond.androiddatabinding.BuildConfig;

import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterMethod;
import twitter4j.User;
import twitter4j.auth.AccessToken;

/**
 * Created by desmond on 17/10/15.
 */
public class TwitterClient extends TwitterAdapter {
    private static final int PAGE_SIZE = 200;

    private final AsyncTwitter mAsyncTwitter;
    private final TwitterConsumer mTwitterConsumer;

    public static TwitterClient newInstance(TwitterConsumer streamConsumer) {
        final AsyncTwitter asyncTwitter = AsyncTwitterFactory.getSingleton();

        try {
            asyncTwitter.setOAuthConsumer(BuildConfig.TWITTER_CONSUMER_KEY, BuildConfig.TWITTER_CONSUMER_SECRET);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        final AccessToken accessToken = new AccessToken(BuildConfig.TWITTER_ACCESS_KEY,
                BuildConfig.TWITTER_ACCESS_SECRET);
        asyncTwitter.setOAuthAccessToken(accessToken);

        return new TwitterClient(asyncTwitter, streamConsumer);
    }

    TwitterClient(AsyncTwitter asyncTwitter, TwitterConsumer streamConsumer) {
        this.mAsyncTwitter = asyncTwitter;
        this.mTwitterConsumer = streamConsumer;
    }

    @Override
    public void onException(TwitterException te, TwitterMethod method) {
        mTwitterConsumer.onError(te);
    }

    @Override
    public void verifiedCredentials(User user) {
        mTwitterConsumer.onConnected();
    }

    public void fetchHomeStream(long sinceId) {
        Paging paging = new Paging(1, PAGE_SIZE, sinceId);
        mAsyncTwitter.getHomeTimeline(paging);
    }

    @Override
    public void gotHomeTimeline(ResponseList<Status> statuses) {
        mTwitterConsumer.addToHomeStream(statuses);
    }

    public void shutdown() {
        mAsyncTwitter.shutdown();
    }

    public void connect() {
        mAsyncTwitter.addListener(TwitterClient.this);
        mAsyncTwitter.verifyCredentials();
    }
}
