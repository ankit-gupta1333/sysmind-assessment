package com.example.marvelcharacters.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.Box;
import io.objectbox.BoxStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.marvelcharacters.MyApplication;
import com.example.marvelcharacters.R;
import com.example.marvelcharacters.adapter.MarvelCharacterRecyclerAdapter;
import com.example.marvelcharacters.entity.Person;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static List<Person> personList = new ArrayList<>();
    FloatingActionButton fabAdd;
    Box<Person> personBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.marvel_characters));
        MyApplication myApplication = new MyApplication();
        BoxStore boxStore = myApplication.getBoxStore();
        personBox = boxStore.boxFor(Person.class);
        personList = personBox.getAll();
        fabAdd = findViewById(R.id.fbt_add);
        mRecyclerView = (RecyclerView) findViewById(R.id.person_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MarvelCharacterRecyclerAdapter(MainActivity.this, personList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDetailsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
