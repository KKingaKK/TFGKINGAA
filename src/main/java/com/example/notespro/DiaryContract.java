package com.example.notespro;
import android.provider.BaseColumns;

public final class DiaryContract {
    private DiaryContract() {}

    public static class DiaryEntry implements BaseColumns {
        public static final String TABLE_NAME = "diary_entries";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_CONTENT = "content";
    }
}
