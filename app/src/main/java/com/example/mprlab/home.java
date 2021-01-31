package com.example.mprlab;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTitle("Programming Books");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        final ListView listView = findViewById(R.id.list_view);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();


        //Adapter
        AssetManager assetManager = getResources().getAssets();

        Scanner scanner_title = null;
        try {
            scanner_title = new Scanner(assetManager.open("book_title.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner_author = null;
        try {
            scanner_author = new Scanner(assetManager.open("book_author.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner_description = null;
        try {
            scanner_description = new Scanner(assetManager.open("book_description.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        final ArrayList<Model> dataChunk = new ArrayList<>();
        int index = 0;
        while (scanner_title.hasNextLine() && scanner_author.hasNextLine() && scanner_description.hasNextLine()){
            index += 1;
//            System.out.println("Repeating scanner #"+index);
//            Log.i("Data Processing","Churning data "+ index);
            Model buffer = new Model();

            buffer.title = scanner_title.nextLine();
            buffer.author = scanner_author.nextLine();
            buffer.description = scanner_description.nextLine();

            dataChunk.add(buffer);

            ContentValues record = new ContentValues();
            record.put(dbHelper.ATTR_TITLE, buffer.title);
            record.put(dbHelper.ATTR_AUTHOR, buffer.author);
            record.put(dbHelper.ATTR_DESCRIPTION, buffer.description);
            if (!dbHelper.CheckIfExist(dbHelper.TABLE_NAME, dbHelper.ATTR_TITLE, buffer.title)){
//                System.out.println("Is Database Open?"+ sqLiteDatabase.isOpen());
//                System.out.println("Saving data to SQL Database "+index);
//                Log.i("Data Saving","Database data "+ index);
                sqLiteDatabase.insertOrThrow(dbHelper.TABLE_NAME, null, record);
            }
            sqLiteDatabase.close();
        }

        Adapter myAdapter = new Adapter(this, R.layout.listview_items, dataChunk);

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), dataChunk.get(position).title, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, Item.class);
                intent.putExtra("id", position+1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);

        MenuItem search = menu.findItem(R.id.ab_search);
        // MenuItemCompat.GetActionView is deprecated
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ab_help:
                Intent myIntent = new Intent(home.this, help.class);
                home.this.startActivity(myIntent);
                return true;
            case R.id.ab_setting:
                Intent myIntents = new Intent(home.this, settings.class);
                home.this.startActivity(myIntents);
                return true;
            case R.id.ab_about:
                AlertDialog.Builder about = new AlertDialog.Builder(this);
                about.setTitle("Programming Books");
                about.setMessage(
                                "Dibuat Oleh:\n" +
                                "1. Irwanto (171401084)\n" +
                                "2. Gita Tarigan (171401077)\n" +
                                "3. Yanuar Tumanggor (171401092)\n"
                );
                about.create().show();
                return true;
            case R.id.ab_logout:
                AlertDialog.Builder logout_alert = new AlertDialog.Builder(this);
                logout_alert.setMessage("Confirm Signing Out").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoogleSignInOptions gso = new GoogleSignInOptions.
                                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                build();
                        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getApplicationContext(),gso);
                        googleSignInClient.signOut();

                        Intent i = new Intent(home.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}

