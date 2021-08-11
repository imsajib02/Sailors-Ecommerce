package com.b2gsoft.sailorsexpress.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.view.LocationActivity;

public class AddressFragment extends Fragment {

    RelativeLayout locationLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);

        locationLayout = (RelativeLayout) view.findViewById(R.id.rel_location);

        locationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
