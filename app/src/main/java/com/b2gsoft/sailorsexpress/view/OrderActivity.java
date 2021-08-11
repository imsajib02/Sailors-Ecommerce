package com.b2gsoft.sailorsexpress.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.b2gsoft.sailorsexpress.R;
import com.b2gsoft.sailorsexpress.adapter.ViewPagerAdapter;
import com.b2gsoft.sailorsexpress.utils.CustomViewPager;

public class OrderActivity extends AppCompatActivity {

    private TextView txtBack, txtNext;
    private RelativeLayout paymentIconBG, confirmedIconBG;
    private ImageView icPayment, icConfirmed;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        paymentIconBG = (RelativeLayout) findViewById(R.id.rel_payment);
        confirmedIconBG = (RelativeLayout) findViewById(R.id.rel_order_placed);

        txtBack = (TextView) findViewById(R.id.txt_back);
        txtNext = (TextView) findViewById(R.id.txt_next);

        icPayment = (ImageView) findViewById(R.id.ic_payment);
        icConfirmed = (ImageView) findViewById(R.id.ic_confirmed);

        viewPager = (CustomViewPager) findViewById(R.id.viewpager);

        setupViewPager();

        viewPager.setOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(position == 1) {

                    paymentIconBG.setBackground(getResources().getDrawable(R.drawable.round));
                    icPayment.setBackground(getResources().getDrawable(R.drawable.card_white));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void setupViewPager() {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
    }
}
