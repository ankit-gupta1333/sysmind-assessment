package com.example.marvelcharacters.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.marvelcharacters.MyApplication;
import com.example.marvelcharacters.R;
import com.example.marvelcharacters.entity.Person;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class AddDetailsActivity extends AppCompatActivity {

    TextInputEditText etName, etDescription;
    Button btAddToList;
    Box<Person> personBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.add_details_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyApplication myApplication = new MyApplication();
        BoxStore boxStore = myApplication.getBoxStore();
        personBox = boxStore.boxFor(Person.class);
        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        btAddToList = findViewById(R.id.bt_add_to_list);

        btAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBlankFields()) {
                    Person person = new Person();
                    person.setName(etName.getText().toString());
                    person.setDescription(etDescription.getText().toString());
                    personBox.put(person);
                    Toast.makeText(AddDetailsActivity.this, getString(R.string.added_to_list), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean checkBlankFields() {
        if((etName.getText()+"").equalsIgnoreCase("")) {
            Toast.makeText(AddDetailsActivity.this, getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            return false;
        }
        if((etDescription.getText()+"").equalsIgnoreCase("")) {
            Toast.makeText(AddDetailsActivity.this, getString(R.string.please_enter_description), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddDetailsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
