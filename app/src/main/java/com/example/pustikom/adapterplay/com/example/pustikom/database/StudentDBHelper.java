package com.example.pustikom.adapterplay.com.example.pustikom.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pustikom.adapterplay.com.example.pustikom.user.Student;

/**
 * Created by ACER on 11/11/2016.
 */

public class StudentDBHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "college.db";
    private static final int DATABASE_VERSION=1;

    public StudentDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String sql = "CREATE TABLE " + StudentContract.TABLE_NAME + " " +
                                StudentContract._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                                StudentContract.COLUMN_STUDENT_NIM + "TEXT NOT NULL," +
                                StudentContract.COLUMN_STUDENT_NAME + "TEXT NOT NULL," +
                                StudentContract.COLUMN_STUDENT_GENDER + "INTEGER," +
                                StudentContract.COLUMN_STUDENT_MAIL + "TEXT," +
                                StudentContract.COLUMN_STUDENT_PHONE + "TEXT";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //empty
    }

    public void insertStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_STUDENT_NIM,student.getNoreg());
        values.put(StudentContract.COLUMN_STUDENT_NAME,student.getName());
        values.put(StudentContract.COLUMN_STUDENT_GENDER,student.getGender());
        values.put(StudentContract.COLUMN_STUDENT_MAIL,student.getMail());
        values.put(StudentContract.COLUMN_STUDENT_PHONE,student.getPhone());
        db.insert(StudentContract.TABLE_NAME, null, values);
    }

    public void updateStudent(SQLiteDatabase wdb, Student student, String condition, String[] conditionArg){
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_STUDENT_NIM,student.getNoreg());
        values.put(StudentContract.COLUMN_STUDENT_NAME,student.getName());
        values.put(StudentContract.COLUMN_STUDENT_GENDER,student.getGender());
        values.put(StudentContract.COLUMN_STUDENT_MAIL,student.getMail());
        values.put(StudentContract.COLUMN_STUDENT_PHONE,student.getPhone());
        wdb.update(StudentContract.TABLE_NAME, values, condition, conditionArg);
    }

    public void truncate(SQLiteDatabase db){
        String sql = "DELETE FROM " + StudentContract.TABLE_NAME + ";VACUUM;";
        db.execSQL(sql);
    }

    public void delete(SQLiteDatabase rdb, Student student) {
        ContentValues values = new ContentValues();
        values.put(StudentContract.COLUMN_STUDENT_NIM,student.getNoreg());
        values.put(StudentContract.COLUMN_STUDENT_NAME,student.getName());
        values.put(StudentContract.COLUMN_STUDENT_GENDER,student.getGender());
        values.put(StudentContract.COLUMN_STUDENT_MAIL,student.getMail());
        values.put(StudentContract.COLUMN_STUDENT_PHONE,student.getPhone());
        rdb.delete(StudentContract.TABLE_NAME, null, null);
        //
    }

    private void fetch(SQLiteDatabase db) {
        // Create and/or open a database to read from it
       // SQLiteDatabase db = StudentDBHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                StudentContract._ID,
                StudentContract.COLUMN_STUDENT_NAME,
                StudentContract.COLUMN_STUDENT_GENDER,
                StudentContract.COLUMN_STUDENT_MAIL,
                StudentContract.COLUMN_STUDENT_PHONE };

        // Perform a query on the students table
        Cursor cursor = db.query(
                StudentContract.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

       // TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
           /* displayView.setText("The students table contains " + cursor.getCount() + " students.\n\n");
            displayView.append(StudentContract._ID + " - " +
                    StudentContract.COLUMN_STUDENT_NAME + " - " +
                    StudentContract.COLUMN_STUDENT_GENDER + " - " +
                    StudentContract.COLUMN_STUDENT_MAIL + " - " +
                    StudentContract.COLUMN_STUDENT_PHONE + "\n");*/

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(StudentContract._ID);
            int nameColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_NAME);
            int genderColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_GENDER);
            int mailColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_MAIL);
            int phoneColumnIndex = cursor.getColumnIndex(StudentContract.COLUMN_STUDENT_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                String currentMail = cursor.getString(mailColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView

                /*displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentGender + " - " +
                        currentMail + " - " +
                        currentPhone));*/
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

}
