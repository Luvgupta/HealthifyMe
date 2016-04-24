package com.example.anr.healthifyme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ANR on 23/04/16.
 */
public class CustomAdapter extends AbstractExpandableItemAdapter<CustomAdapter.ParentHolder, CustomAdapter.ChildHolder> {
    private final String TAG = this.getClass().toString();
    Context mContext;
    private List<String> dayTiming;
    private Map<String, List<Model.SlotDetails>> slotInfo;

    public CustomAdapter(Context context, List<String> dayTiming, Map<String, List<Model.SlotDetails>> slotInfo) {
        this.mContext = context;
        this.dayTiming = dayTiming;
        this.slotInfo = slotInfo;

        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return dayTiming.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return slotInfo.get(dayTiming.get(groupPosition)).size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return dayTiming.get(groupPosition).hashCode();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return slotInfo.get(dayTiming.get(groupPosition)).get(childPosition).hashCode();
    }

    @Override
    public ParentHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_layout, parent, false);
        return new ParentHolder(view);
    }

    @Override
    public ChildHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_layout, parent, false);
        return new ChildHolder(view);
    }

    @Override
    public void onBindGroupViewHolder(ParentHolder holder, int groupPosition, int viewType) {
        // set up title text
        holder.titleText.setText(dayTiming.get(groupPosition));


        // set up available slot text
        holder.availableSlotText.setText(slotInfo.get(dayTiming.get(groupPosition)).size() + " slots available");

        // set up arrow

    }

    @Override
    public void onBindChildViewHolder(ChildHolder holder, int groupPosition, int childPosition, int viewType) {
        Model.SlotDetails slotDetails = slotInfo.get(dayTiming.get(groupPosition)).get(childPosition);

        String startTime = "", endTime = "";
        try {
            startTime = (getTime(slotInfo.get(dayTiming.get(groupPosition)).get(childPosition).start_time));
            endTime = (getTime(slotInfo.get(dayTiming.get(groupPosition)).get(childPosition).end_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // set start time
        holder.startTime.setText(startTime);

        // set end time
        holder.endTime.setText(endTime);

        // set background
        if (slotDetails.is_booked() || slotDetails.is_expired()) {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.child_disable_background_color));
        } else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.child_enable_background_color));
        }
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(ParentHolder holder, int groupPosition, int x, int y, boolean expand) {
        if (expand) {
            holder.upDownArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down_arrow));
            holder.upDownArrow.setRotation(0);
        } else {
            holder.upDownArrow.setImageDrawable(mContext.getResources().getDrawable(R.drawable.down_arrow));
            holder.upDownArrow.setRotation(180);
        }
        return true;
    }

    static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public MyBaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ParentHolder extends MyBaseViewHolder {
        TextView titleText;
        TextView availableSlotText;
        ImageView upDownArrow;

        public ParentHolder(View v) {
            super(v);

            titleText = (TextView) v.findViewById(R.id.parent_text);
            availableSlotText = (TextView) v.findViewById(R.id.parent_slot_available);
            upDownArrow = (ImageView) v.findViewById(R.id.parent_up_down_arrow);

        }
    }

    public static class ChildHolder extends MyBaseViewHolder {
        TextView startTime;
        TextView endTime;

        public ChildHolder(View v) {
            super(v);
            startTime = (TextView) v.findViewById(R.id.child_start_time);
            endTime = (TextView) v.findViewById(R.id.child_end_time);
        }
    }

    public String getTime(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssz");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        Date d = format.parse(date);
        String formattedTime = output.format(d);
        return formattedTime.toUpperCase();
    }
}
