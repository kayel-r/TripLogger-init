package com.bignerdranch.android.triplogger.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.triplogger.Trip;
import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by reece on 28/10/2016.
 */

public class TripCursorWrapper extends CursorWrapper {
    public TripCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Trip getTrip() {
        String uuidString = getString(getColumnIndex(TripTable.Cols.UUID));
        String title = getString(getColumnIndex(TripTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TripTable.Cols.DATE));

        Trip trip = new Trip(UUID.fromString(uuidString));
        trip.setTitle(title);
        trip.setDate(new Date(date));

        return trip;
    }
}
