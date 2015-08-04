package ru.sergey.abadzhev.twittertestapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;

import java.util.List;

/**
 * Created by sergey on 04.08.15.
 */
public class TwitsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.twits_layout, container, false);


        ListView listView = (ListView) view.findViewById(R.id.tweets_listview);


        final TweetViewAdapter adapter = new TweetViewAdapter(getActivity());
        listView.setAdapter(adapter);

        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();

        service.homeTimeline(null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        adapter.setTweets(result.data);
                    }

                    @Override
                    public void failure(TwitterException error) {
                        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failure),
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tweets_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                service.homeTimeline(null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                            @Override
                            public void success(Result<List<Tweet>> result) {
                                adapter.setTweets(result.data);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void failure(TwitterException error) {
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.failure),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

        ImageButton composeBtn = (ImageButton) view.findViewById(R.id.tweet_btn);
        composeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, new ComposeFragment());
                transaction.commit();
            }
        });

        ImageButton logoutBtn = (ImageButton) view.findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Twitter.getSessionManager().clearActiveSession();
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, new LoginFragment());
                transaction.commit();
            }
        });

        return view;
    }


}
