package com.example.notespro;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "diary.db";

    public DiaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DIARY_ENTRIES_TABLE = "CREATE TABLE " + DiaryContract.DiaryEntry.TABLE_NAME + " (" +
                DiaryContract.DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DiaryContract.DiaryEntry.COLUMN_TITLE + " TEXT," +
                DiaryContract.DiaryEntry.COLUMN_CONTENT + " TEXT)";

        db.execSQL(SQL_CREATE_DIARY_ENTRIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DiaryContract.DiaryEntry.TABLE_NAME);
        onCreate(db);
    }
}

