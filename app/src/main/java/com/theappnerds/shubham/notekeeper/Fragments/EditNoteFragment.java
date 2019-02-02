package com.theappnerds.shubham.notekeeper.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.theappnerds.shubham.notekeeper.Activities.NoteListActivity;
import com.theappnerds.shubham.notekeeper.Adapters.NoteAdapter;
import com.theappnerds.shubham.notekeeper.Models.NoteItem;
import com.theappnerds.shubham.notekeeper.R;

import java.text.DateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.Realm;

public class EditNoteFragment extends BottomSheetDialogFragment {

    Realm mRealm;
    private NoteItem mNoteItem;
    NoteAdapter mNoteListAdapter;
    int mPosition;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_addnote, parent, false);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);

        Bundle bundle = getArguments();
        mNoteItem = (NoteItem) bundle.getSerializable("EditNote");
        mPosition = bundle.getInt("ItemPosition");

        setUpFragmentUi(view, mNoteItem, mPosition);
        return view;
    }

    private void setUpFragmentUi(View view, NoteItem noteItem, int mPosition) {
        mRealm = Realm.getDefaultInstance();

        final EditText noteTitle = view.findViewById(R.id.et_noteTitle_id);
        final EditText noteDetail = view.findViewById(R.id.et_noteDetail_id);
        final TextView noteHeaderText = view.findViewById(R.id.noteHeaderText);

        noteHeaderText.setText("Edit Note");
        Button noteSaveButton = view.findViewById(R.id.button_saveNote);


        noteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!noteTitle.getText().toString().isEmpty() && !noteDetail.getText().toString().isEmpty()) {

                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            noteItem.setNoteTitle(noteTitle.getText().toString());
                            noteItem.setNoteDetail(noteDetail.getText().toString());

                            Date date = new Date();
                            DateFormat df = DateFormat.getDateTimeInstance();
                            String newNoteDate = df.format(date);
                            noteItem.setNoteDate(newNoteDate);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dismiss();
                                    Intent intent = new Intent(getActivity(), NoteListActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    getActivity().finish();
                                    startActivity(intent);
                                }
                            }, 400);
                            //notifyItemChanged(mPosition, noteItem);
                        }
                    });

                } else {
                    Snackbar.make(view, "Add Note Title and Detail", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoteListAdapter != null) {
            mNoteListAdapter.notifyDataSetChanged();
        }
    }

}
