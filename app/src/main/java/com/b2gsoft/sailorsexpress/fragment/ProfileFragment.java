package com.b2gsoft.sailorsexpress.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.b2gsoft.sailorsexpress.R;

import static com.b2gsoft.sailorsexpress.view.MainActivity.homeView;

public class ProfileFragment extends Fragment {

    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.context = view.getContext();

        return view;
    }


    @Override
    public void onDestroy() {

        onBackPressed();
        super.onDestroy();
    }


    private void onBackPressed() {

        homeView();

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, new  HomeFragment())
                .addToBackStack(null)
                .commit();
    }
}
