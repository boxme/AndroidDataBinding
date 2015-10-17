package com.desmond.androiddatabinding.activity;

import com.desmond.androiddatabinding.R;
import com.desmond.androiddatabinding.adapter.StatusAdapter;
import com.desmond.androiddatabinding.twitter.TwitterClient;
import com.desmond.androiddatabinding.twitter.TwitterConsumer;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import twitter4j.Status;

public class MainActivity extends AppCompatActivity implements TwitterConsumer {

    private TwitterClient mTwitterClient;
    private StatusAdapter mStatusAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mStatusAdapter = new StatusAdapter();
        mRecyclerView.setAdapter(mStatusAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTwitterClient = TwitterClient.newInstance(MainActivity.this);
        mTwitterClient.connect();
    }

    @Override
    protected void onPause() {
        mTwitterClient.shutdown();
        super.onPause();
    }

    @Override
    public void addToHomeStream(final List<Status> newStatuses) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStatusAdapter.setStatusList(newStatuses);
            }
        });
    }

    @Override
    public void onConnected() {
        mTwitterClient.fetchHomeStream(1);
    }

    @Override
    public void onError(Exception e) {
        Log.d("MainActivity", e.toString());
        Snackbar.make(mRecyclerView, "Twitter Error: " + e.toString(), Snackbar.LENGTH_LONG).show();
    }
}
