package com.ibc.android.demo.appslist.app;

/**
 * Created by Spicycurryman on 8/5/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "packagenameManager";

    // Packagename table name
    private static final String TABLE_PACKAGE_NAME = "packagenames";

    // Contacts Table Columns names
    private static final String KEY_PACKAGE_NAME = "packagenames";
    private static final String KEY_STATE = "state";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PACKAGE_NAME + "("
                + KEY_PACKAGE_NAME + " STRING PRIMARY KEY," + KEY_STATE + " INT,"
                 + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACKAGE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new packagename
    void addPackageName(PackageName packageName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATE, packageName.getPACKAGENAME()); // Packagename

        // Inserting Row
        db.insert(TABLE_PACKAGE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single package
    PackageName getPackageName(String packagename) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PACKAGE_NAME, new String[] {KEY_PACKAGE_NAME,
                        KEY_STATE }, KEY_PACKAGE_NAME + "=?",
                new String[] { packagename }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        PackageName packageName = new PackageName((cursor.getString(0)),
                cursor.getInt(1));
        // return packagename
        return packageName;
    }

    // Getting All packagenames
    public List<PackageName> getAllPackageNames() {
        List<PackageName> packageNameList = new ArrayList<PackageName>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PACKAGE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                PackageName packageName = new PackageName();
                packageName.setPACKAGENAME(cursor.getString(0));
                packageName.setState(cursor.getInt(1));
                // Adding contact to list
                packageNameList.add(packageName);
            } while (cursor.moveToNext());
        }

        // return packagename list
        return packageNameList;
    }

    // Updating single packagename
    public int updatePackageName(PackageName packageName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STATE, packageName.getState());

        // updating row
        return db.update(TABLE_PACKAGE_NAME, values, KEY_PACKAGE_NAME + " = ?",
                new String[] { String.valueOf(packageName.getPACKAGENAME()) });
    }

    // Deleting single packagename
    public void deletePackageName(PackageName packageName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PACKAGE_NAME, KEY_PACKAGE_NAME + " = ?",
                new String[] { String.valueOf(packageName.getPACKAGENAME()) });
        db.close();
    }


    // Getting packagename Count
    public int getPackageNameCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PACKAGE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}