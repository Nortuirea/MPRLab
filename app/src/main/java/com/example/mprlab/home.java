package com.example.mprlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class home extends AppCompatActivity {

    String[] text1 = {"#1 The Pragmatic Programmer",
            "#2 Clean Code",
            "#3 Code Complete",
            "#4 Refactoring",
            "#5 Head First Design Patterns",
            "#6 The Mythical Man-Month",
            "#7 The Clean Coder",
            "#8 Working Effectively with Legacy Code",
            "#9 Design Patterns",
            "#10 Cracking the Code Interview"};
    String[] text2 = {"David Thomas & Andrew Hunt",
            "Robert C. Martin",
            "Steve McConnell",
            "Martin Fowler",
            "Eric Freeman & Bert Bates & Kathy Sierra & Elisabeth Robson",
            "Frederick P. Brooks Jr",
            "Robert C. Martin",
            "Michael Feathers",
            "Erich Gamma & Richard Helm & Ralph Johnson & John Vlissides",
            "Gayle Laakmann McDowell"};
    String[] text3 = {"Dave Thomas and Andy Hunt wrote the first edition of this influential book in 1999 to help their clients create better software and rediscover the joy of coding. These lessons have helped a generation of programmers examine the very essence of software development, independent of any particular language, framework, or methodology, and the Pragmatic philosophy has spawned hundreds of books, screencasts, and audio books, as well as thousands of careers and success stories.",
    "Clean Code is divided into three parts. The first describes the principles, patterns, and practices of writing clean code. The second part consists of several case studies of increasing complexity. Each case study is an exercise in cleaning up code—of transforming a code base that has some problems into one that is sound and efficient. The third part is the payoff: a single chapter containing a list of heuristics and “smells” gathered while creating the case studies. The result is a knowledge base that describes the way we think when we write, read, and clean code.",
    "Widely considered one of the best practical guides to programming, Steve McConnell’s original CODE COMPLETE has been helping developers write better software for more than a decade. Now this classic book has been fully updated and revised with leading-edge practices—and hundreds of new code samples—illustrating the art and science of software construction. Capturing the body of knowledge available from research, academia, and everyday commercial practice, McConnell synthesizes the most effective techniques and must-know principles into clear, pragmatic guidance. No matter what your experience level, development environment, or project size, this book will inform and stimulate your thinking—and help you build the highest quality code.",
    "For several years, expert-level object programmers have employed a growing collection of techniques to improve the structural integrity and performance of such existing software programs. Referred to as refactoring, these practices have remained in the domain of experts because no attempt has been made to transcribe the lore into a form that all developers could use…until now. In Refactoring: Improving the Design of Existing Software, renowned object technology mentor Martin Fowler breaks new ground, demystifying these master practices and demonstrating how software practitioners can realize the significant benefits of this new process. With proper training a skilled system designer.",
    "What’s so special about this book? We think your time is too valuable to spend struggling with new concepts. Using the latest research in cognitive science and learning theory to craft a multi-sensory learning experience, Head First Design Patterns uses a visually rich format designed for the way your brain works, not a text-heavy approach that puts you to sleep.",
    "Few books on software project management have been as influential and timeless as The Mythical Man-Month. With a blend of software engineering facts and thought-provoking opinions, Fred Brooks offers insight for anyone managing complex projects. These essays draw from his experience as project manager for the IBM System/360 computer family and then for OS/360, its massive software system.",
    "In The Clean Coder: A Code of Conduct for Professional Programmers, legendary software expert Robert C. Martin introduces the disciplines, techniques, tools, and practices of true software craftsmanship. This book is packed with practical advice–about everything from estimating and coding to refactoring and testing. It covers much more than technique: It is about attitude. Martin shows how to approach software development with honor, self-respect, and pride; work well and work clean; communicate and estimate faithfully; face difficult decisions with clarity and honesty; and understand that deep knowledge comes with a responsibility to act.",
    "This book draws on material Michael created for his own renowned Object Mentor seminars: techniques Michael has used in mentoring to help hundreds of developers, technical managers, and testers bring their legacy systems under control.",
    "The authors begin by describing what patterns are and how they can help you design object-oriented software. They then go on to systematically name, explain, evaluate, and catalog recurring designs in object-oriented systems. With Design Patterns as your guide, you will learn how these important patterns fit into the software development process, and how you can leverage them to solve your own design problems most efficiently.",
    "Learn how to uncover the hints and hidden details in a question, discover how to break down a problem into manageable chunks, develop techniques to unstick yourself when stuck, learn (or re-learn) core computer science concepts, and practice on 189 interview questions and solutions."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Programming Books");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);

        ListView listView = findViewById(R.id.list_view);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

        for (int i = 0; i < text1.length; i++) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("judul", text1[i]);
            hashMap.put("author", "Oleh : " + text2[i] + "");
            hashMap.put("deskripsi", text3[i] + "");
            arrayList.add(hashMap);
        }

        String[] from = {"judul", "author", "deskripsi"};
        int[] to = {R.id.judul_buku, R.id.penulis_buku, R.id.deskripsi_buku};
        SimpleAdapter adapter = new SimpleAdapter(
                this, arrayList,
                R.layout.desain,
                from, to
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getApplicationContext(), text1[i], Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, "You tapped help",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ab_setting:
                Toast.makeText(this, "You tapped setting",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ab_about:
                AlertDialog.Builder about = new AlertDialog.Builder(this);
                about.setTitle("Aplikasi UTS MPR4");
                about.setMessage(
                                "Dibuat Oleh:\n" +
                                "1. Irwanto (171401084)\n" +
                                "3. Gita Tarigan (171401077)\n" +
                                "3. Yanuar Tumanggor (171401092)\n"
                );
                about.create().show();
                return true;
            case R.id.ab_logout:
                AlertDialog.Builder logout_alert = new AlertDialog.Builder(this);
                logout_alert.setMessage("Confirm Signing Out").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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

