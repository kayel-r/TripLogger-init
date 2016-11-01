package com.bignerdranch.android.triplogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.triplogger.database.TripBaseHelper;
import com.bignerdranch.android.triplogger.database.TripCursorWrapper;
import com.bignerdranch.android.triplogger.database.TripDbSchema;
import com.bignerdranch.android.triplogger.database.TripDbSchema.TripTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by reece on 25/10/2016.
 */

public class TripLab {
    private static TripLab sTripLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TripLab get(Context context) {
        if (sTripLab == null) {
            sTripLab = new TripLab(context);
        }
        return sTripLab;
    }

    private TripLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TripBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addTrip(Trip t) {
        ContentValues values = getContentValues(t);

        mDatabase.insert(TripTable.NAME, null, values);
    }

    public List<Trip> getTrips() {
        List<Trip> trips = new ArrayList<>();

        TripCursorWrapper cursor = queryTrips(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                trips.add(cursor.getTrip());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return trips;
    }

    public Trip getTrip(UUID id) {
        TripCursorWrapper cursor = queryTrips(
                TripTable.Cols.UUID + " = ?",
                new String[] { id.toString() }
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTrip();
        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Trip trip) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, trip.getPhotoFilename());
    }

    public void updateTrip(Trip trip) {
        String uuidString = trip.getId().toString();
        ContentValues values = getContentValues(trip);

        mDatabase.update(TripTable.NAME, values,
                TripTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private static ContentValues getContentValues(Trip trip) {
        ContentValues values = new ContentValues();
        values.put(TripTable.Cols.UUID, trip.getId().toString());
        values.put(TripTable.Cols.TITLE, trip.getTitle());
        values.put(TripTable.Cols.DATE, trip.getDate().getTime());

        return values;
    }

    private TripCursorWrapper queryTrips(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TripTable.NAME,
                null, // Cols, null selects all
                whereClause,
                whereArgs,
                null, // Group
                null, // having
                null // order
        );

        return new TripCursorWrapper(cursor);
    }
}
