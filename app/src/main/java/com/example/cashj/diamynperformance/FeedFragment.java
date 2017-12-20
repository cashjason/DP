package com.example.cashj.diamynperformance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;


public class FeedFragment extends ListFragment implements View.OnClickListener {

    TweetTimelineListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setListAdapter(adapter);
        System.out.println("LIST IS SET");
        return inflater.inflate(R.layout.fragment_feed, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TwitterConfig config = new TwitterConfig.Builder(super.getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("jceuIOfjwqwEEvzOmpFQ1wrAY", "E8I5O5WXQKDrscBj8bMfE9MrhSe1HP8ajLgyylMvVP8efGZKzx"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        UserTimeline userTimeline = new UserTimeline.Builder().screenName("DiamynHall").build();
        userTimeline.next(null, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) { System.out.println("Success"); }
            @Override
            public void failure(TwitterException exception) { System.out.println("Failure"); }
        });

        adapter = new TweetTimelineListAdapter.Builder(super.getContext())
                .setTimeline(userTimeline)
                .build();
        adapter = new TweetTimelineListAdapter(super.getContext(), userTimeline);
        setListAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
    }
}
