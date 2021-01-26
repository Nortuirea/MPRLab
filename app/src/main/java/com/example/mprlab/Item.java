package com.example.mprlab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView title = findViewById(R.id.itemTitle);
        TextView author = findViewById(R.id.itemAuthor);
        TextView description = findViewById(R.id.itemDescription);

        Bundle extras = getIntent().getExtras();
        int postId = -1;
        if(extras != null){
            postId = extras.getInt("id");
        }

//        System.out.println("postId value is "+postId);

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s=%s;", dbHelper.TABLE_NAME, dbHelper.ATTR_ID, postId);
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

//        System.out.println("This is how the query look like"+query);
//        System.out.println("This is the cursor "+cursor);
//        System.out.println(cursor != null);
        if(cursor != null && cursor.moveToFirst()){
//            Log.println(Log.WARN,"Cursor data", "Result\n"+(cursor != null));
            title.setText(cursor.getString(cursor.getColumnIndex(dbHelper.ATTR_TITLE)));
            author.setText(cursor.getString(cursor.getColumnIndex(dbHelper.ATTR_AUTHOR)));
            description.setText(cursor.getString(cursor.getColumnIndex(dbHelper.ATTR_DESCRIPTION)));
        }
        cursor.close();
    }
}