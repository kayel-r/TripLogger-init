package com.bignerdranch.android.triplogger.database;

/**
 * Created by reece on 28/10/2016.
 */

public class TripDbSchema {
    public static final class TripTable {
        public static final String NAME = "trips";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
        }
    }
}
