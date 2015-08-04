package ru.sergey.abadzhev.twittertestapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

/**
 * Created by sergeyabadzhev on 04.08.15.
 */
public class ComposeFragment extends Fragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.compose_layout, container, false);



        final EditText text = (EditText) view.findViewById(R.id.send_text);

        ImageButton sendBtn = (ImageButton) view.findViewById(R.id.send_button);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StatusesService statusesService = Twitter.getApiClient(Twitter.getSessionManager().getActiveSession()).getStatusesService();
                statusesService.update(text.getText().toString(), null, null, null, null, null, null, null, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        FragmentTransaction transaction = getFragmentManager()
                                .beginTransaction();
                        transaction.replace(R.id.container, new TwitsFragment());
                        transaction.commit();
                    }

                    @Override
                    public void failure(TwitterException e) {

                    }
                });
            }
        });

        final ImageButton backBtn=(ImageButton) view.findViewById(R.id.back_button);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();
                transaction.replace(R.id.container, new TwitsFragment());
                transaction.commit();
            }
        });




        return view;
    }








}
