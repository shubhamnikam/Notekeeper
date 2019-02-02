package com.theappnerds.shubham.notekeeper.Activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theappnerds.shubham.notekeeper.Adapters.NoteAdapter;
import com.theappnerds.shubham.notekeeper.Fragments.AddNoteFragment;
import com.theappnerds.shubham.notekeeper.Models.NoteItem;
import com.theappnerds.shubham.notekeeper.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class NoteListActivity extends AppCompatActivity {

    Realm mRealm;
    RecyclerView mRecyclerView;
    RealmResults<NoteItem> mRealmNoteList;
    NoteAdapter mNoteListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        SetupUI();
    }

    private void SetupUI() {
        mRealm = Realm.getDefaultInstance();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration
                (mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRealmNoteList = mRealm.where(NoteItem.class).findAll();

        mNoteListAdapter = new NoteAdapter(this, mRealm, mRealmNoteList);
        mRecyclerView.setAdapter(mNoteListAdapter);
        mNoteListAdapter.notifyDataSetChanged();

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.replaceMenu(R.menu.main_menu_bottom);

        FloatingActionButton fabNewsOptions = findViewById(R.id.floatingActionButton);
        fabNewsOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                addNoteFragment.show(getSupportFragmentManager(), addNoteFragment.getTag());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mNoteListAdapter != null) {
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRealm.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu_bottom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "My Notes", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_reminderNotes) {
            Toast.makeText(this, "Reminder \n yet to be implemented", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.action_scheduleNotes) {
            Toast.makeText(this, "Schedule \n yet to be implemented", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
