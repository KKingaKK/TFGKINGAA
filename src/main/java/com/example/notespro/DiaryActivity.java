package com.example.notespro;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DiaryActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etContent;
    private Button btnSave;
    private Button btnEdit;
    private Button btnDelete;
    private Button volver123;
    private EditText tvEntriesList;

    private List<String> diaryEntries;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        volver123 =findViewById(R.id.volver123);
        tvEntriesList = findViewById(R.id.tvEntriesList);

        diaryEntries = new ArrayList<>();

        SQLiteOpenHelper dbHelper = new DiaryDbHelper(this);
        database = dbHelper.getWritableDatabase();

        volver123.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiaryActivity.this, MainActivity.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String content = etContent.getText().toString();
                String entry = title + ": " + content;
                diaryEntries.add(entry);
                saveEntryToDatabase(title, content);
                updateEntriesList();
                clearInputFields();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedEntryIndex = getSelectedEntryIndex();
                if (selectedEntryIndex != -1) {
                    String selectedEntry = diaryEntries.get(selectedEntryIndex);
                    String[] parts = selectedEntry.split(": ", 2);
                    if (parts.length == 2) {
                        String title = parts[0];
                        String content = parts[1];
                        etTitle.setText(title);
                        etContent.setText(content);
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedEntryIndex = getSelectedEntryIndex();
                if (selectedEntryIndex != -1) {
                    String selectedEntry = diaryEntries.get(selectedEntryIndex);
                    String[] parts = selectedEntry.split(": ", 2);
                    if (parts.length == 2) {
                        String title = parts[0];
                        String content = parts[1];
                        deleteEntryFromDatabase(title, content);
                        diaryEntries.remove(selectedEntryIndex);
                        updateEntriesList();
                        clearInputFields();
                    }
                }
            }
        });

        loadEntriesFromDatabase();
        updateEntriesList();
    }

    private int getSelectedEntryIndex() {
        String selectedEntry = tvEntriesList.getText().toString();
        for (int i = 0; i < diaryEntries.size(); i++) {
            if (diaryEntries.get(i).equals(selectedEntry)) {
                return i;
            }
        }
        return -1;
    }

    private void saveEntryToDatabase(String title, String content) {
        ContentValues values = new ContentValues();
        values.put(DiaryContract.DiaryEntry.COLUMN_TITLE, title);
        values.put(DiaryContract.DiaryEntry.COLUMN_CONTENT, content);
        database.insert(DiaryContract.DiaryEntry.TABLE_NAME, null, values);
    }

    private void deleteEntryFromDatabase(String title, String content) {
        String selection = DiaryContract.DiaryEntry.COLUMN_TITLE + " = ? AND " +
                DiaryContract.DiaryEntry.COLUMN_CONTENT + " = ?";
        String[] selectionArgs = {title, content};
        database.delete(DiaryContract.DiaryEntry.TABLE_NAME, selection, selectionArgs);
    }

    private void loadEntriesFromDatabase() {
        diaryEntries.clear();
        Cursor cursor = database.query(
                DiaryContract.DiaryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry.COLUMN_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(DiaryContract.DiaryEntry.COLUMN_CONTENT));
            String entry = title + ": " + content;
            diaryEntries.add(entry);
        }

        cursor.close();
    }

    private void updateEntriesList() {
        StringBuilder entries = new StringBuilder();
        for (String entry : diaryEntries) {
            entries.append(entry).append("\n");
        }
        tvEntriesList.setText(entries.toString());
    }

    private void clearInputFields() {
        etTitle.getText().clear();
        etContent.getText().clear();
    }
}
