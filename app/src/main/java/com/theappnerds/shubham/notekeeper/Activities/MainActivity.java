package com.theappnerds.shubham.notekeeper.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theappnerds.shubham.notekeeper.Fragments.AddNoteFragment;
import com.theappnerds.shubham.notekeeper.Models.NoteItem;
import com.theappnerds.shubham.notekeeper.R;

import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        byPassActivity();

        FloatingActionButton fab = findViewById(R.id.fab_createNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                addNoteFragment.show(getSupportFragmentManager(), addNoteFragment.getTag());
            }
        });

    }

    private void byPassActivity() {
        if (mRealm.where(NoteItem.class).count() > 0) {
            Intent intent = new Intent(this, NoteListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);        }
    }
}
