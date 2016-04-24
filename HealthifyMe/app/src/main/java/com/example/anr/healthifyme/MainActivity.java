package com.example.anr.healthifyme;

import android.app.Activity;
import android.graphics.Point;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {
    List<String> dates;
    Model mDataOutputModel;

    ViewPager mViewPager;
    PageAdapter mPageAdapter;
    TabLayout mTabLayout;
    TextView month ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize ids
        initializeIds();

        // initialize data
        initializeDataModel();

        // set up view pager
        setUpViewPager();

        // api call
        apiCallToGetData();


    }

    // ids initialization
    private void initializeIds() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        month = (TextView) findViewById(R.id.month_text);
    }

    // initialize data
    private void initializeDataModel() {
        dates = new ArrayList<>();
        mDataOutputModel = new Model();
    }

    // set up view pager
    private void setUpViewPager() {
        mPageAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPageAdapter);


        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mTabLayout.setupWithViewPager(mViewPager);
    }

    // api call
    private void apiCallToGetData() {
        RestAdapter.getRestService().getData1(new Callback<Model>() {
            @Override
            public void success(Model dataModel, Response response) {
                if (dataModel != null) {
                    if (dataModel.slots != null) {
                        // set up output model
                        mDataOutputModel = dataModel;

                        // get keys
                        dates = new ArrayList<>(dataModel.getSlots().keySet());

                        // notify adapter that we have new data
                        if (mViewPager != null && mViewPager.getAdapter() != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mViewPager.getAdapter().notifyDataSetChanged();
                                    try {
                                        updateTabs();
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
            }
        });
    }

    // update the tabs
    private void updateTabs() throws ParseException {
        if (mTabLayout != null && dates != null
                && mTabLayout.getTabCount() == dates.size()) {
            for (int i = 0; i < dates.size(); i++) {
                if (mTabLayout.getTabAt(i) != null && dates.get(i) != null) {
                    String day = convertDateToDay(dates.get(i));
                    String dateNumber = dates.get(i).substring(dates.get(i).lastIndexOf("-") + 1);
                    String monthText = getMonth(dates.get(i));
                    month.setText(monthText);
                    if(day != null)
                    mTabLayout.getTabAt(i).setText(dateNumber+"\n"+day);
                }
            }
        }
    }

    class PageAdapter extends FragmentStatePagerAdapter {
        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String date = dates.get(position);
            List<String> dayTiming = new ArrayList<>(mDataOutputModel.getSlots().get(date).keySet());
            Map<String, List<Model.SlotDetails>> slotInfo = mDataOutputModel.getSlots().get(date);

            return new CustomFragment(date, dayTiming, slotInfo);
        }

        @Override
        public int getCount() {
            return dates.size();
        }
    }

    public String convertDateToDay(String date) throws ParseException {
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dateConverted = inFormat.parse(date);
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String day = outFormat.format(dateConverted);;
        return day.substring(0,3);
    }

    public String getMonth(String date ){
        String monthNumber = date.substring(date.indexOf("-")+1,date.lastIndexOf("-"));
        switch  (Integer.parseInt(monthNumber)){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "NotAMonth";

        }
    }

}
