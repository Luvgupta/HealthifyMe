package com.example.anr.healthifyme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.List;
import java.util.Map;

/**
 * Created by ANR on 23/04/16.
 */
public class CustomFragment extends Fragment {
    String date;
    List<String> dayTiming;
    Map<String, List<Model.SlotDetails>> slotInfo;

    RecyclerView recyclerView;
    CustomAdapter adapter;

    public CustomFragment(String date, List<String> dayTiming, Map<String, List<Model.SlotDetails>> slotInfo) {
        this.date = date;
        this.dayTiming = dayTiming;
        this.slotInfo = slotInfo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new CustomAdapter(getActivity(), dayTiming, slotInfo);

        recyclerView.setAdapter(expMgr.createWrappedAdapter(adapter));

        expMgr.attachRecyclerView(recyclerView);
    }

    public void notifyData(List<String> dayTiming, Map<String, List<Model.SlotDetails>> slotInfo) {
        this.dayTiming = dayTiming;
        this.slotInfo = slotInfo;

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
