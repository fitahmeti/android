package com.app.swishd.utility;


public class Const {

    public enum SORT_BY {
        distance, open, date
    }

    public enum SEARCH_STATUS {
        saved, unsaved
    }

    public enum YES_NO {
        yes, no
    }

    public enum JOB_STATUS {
        active, open, inProgress
    }

    public enum OFFER_STATUS {
        reject, accept
    }

    public enum QR_CODE {
        pickup_office, drop_office, deliver, pickup
    }

    public enum WEEK_DAY_NAME {
        Monday(0),
        Tuesday(1),
        Wednesday(2),
        Thursday(3),
        Friday(4),
        Saturday(5),
        Sunday(6);

        private final int value;

        WEEK_DAY_NAME(final int newValue) {
            value = newValue;
        }


        /**
         * @param name Week Day Name
         * @return the index number associated with the Day
         */
        public static int getValueByName(String name) {
            for (WEEK_DAY_NAME name1 : values()) {
                if (name.toString().equals(name1.toString()))
                    return name1.getValue();
            }
            return 0;
        }


        /**
         * @param position Index Number
         * @return the name associated with the Index number
         */
        public static String getWeekName(int position) {
            for (WEEK_DAY_NAME name : values()) {
                if (name.getValue() == position)
                    return name.toString();
            }
            return "";
        }

        public int getValue() {
            return value;
        }
    }
}
