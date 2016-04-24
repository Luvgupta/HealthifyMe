package com.example.anr.healthifyme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ANR on 23/04/16.
 */
public class Model {
    Map<String, Map<String, List<SlotDetails>>> slots;


    public Model() {
        slots = new HashMap<>();
    }

    public Map<String, Map<String, List<SlotDetails>>> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, Map<String, List<SlotDetails>>> slots) {
        this.slots = slots;
    }

    public static class SlotDetails {
        String end_time;
        boolean is_booked;
        boolean is_expired;
        long slot_id;
        String start_time;

        public SlotDetails() {

        }

        public SlotDetails(String end_time, boolean is_booked, boolean is_expired, long slot_id, String start_time) {
            this.end_time = end_time;
            this.is_booked = is_booked;
            this.is_expired = is_expired;
            this.slot_id = slot_id;
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public boolean is_booked() {
            return is_booked;
        }

        public void setIs_booked(boolean is_booked) {
            this.is_booked = is_booked;
        }

        public boolean is_expired() {
            return is_expired;
        }

        public void setIs_expired(boolean is_expired) {
            this.is_expired = is_expired;
        }

        public long getSlot_id() {
            return slot_id;
        }

        public void setSlot_id(long slot_id) {
            this.slot_id = slot_id;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }
    }
}
