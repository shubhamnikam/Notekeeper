package com.theappnerds.shubham.notekeeper.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theappnerds.shubham.notekeeper.Activities.NoteListActivity;
import com.theappnerds.shubham.notekeeper.Fragments.EditNoteFragment;
import com.theappnerds.shubham.notekeeper.Models.NoteItem;
import com.theappnerds.shubham.notekeeper.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmResults;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private Context mContext;
    private Realm mRealm;
    private RealmResults<NoteItem> mRealmNoteList;
    private LayoutInflater mLayoutInflater;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;

    public NoteAdapter(Context context, Realm realm, RealmResults<NoteItem> realmUserList) {
        mContext = context;
        mRealm = realm;
        mRealmNoteList = realmUserList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.note_list_item, viewGroup, false);
        return new ViewHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        NoteItem noteItem = mRealmNoteList.get(position);
        viewHolder.noteTitle.setText(noteItem.getNoteTitle());
        viewHolder.noteDetail.setText(noteItem.getNoteDetail());
        viewHolder.noteDateAdded.setText(noteItem.getNoteDate());
    }

    @Override
    public int getItemCount() {
        return mRealmNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView noteTitle, noteDetail, noteDateAdded;
        public ImageView editButton, deleteButton;
        public int id;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.tv_noteTitle_id);
            noteDetail = itemView.findViewById(R.id.tv_noteDetail_id);
            noteDateAdded = itemView.findViewById(R.id.tv_dateAdded_id);
            editButton = itemView.findViewById(R.id.iv_edit);
            deleteButton = itemView.findViewById(R.id.iv_delete);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.iv_edit:
                    int position = getAdapterPosition();
                    NoteItem noteItem = mRealmNoteList.get(position);
                    editNoteItem(noteItem, position);
                    break;

                case R.id.iv_delete:
                    position = getAdapterPosition();
                    deleteNoteItem(position);
                    break;
            }
        }

        private void editNoteItem(final NoteItem noteItem, int position) {
            EditNoteFragment editNoteFragment = new EditNoteFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("EditNote", noteItem);
            bundle.putInt("ItemPosition", position);
            editNoteFragment.show(((NoteListActivity) mContext).getSupportFragmentManager(), editNoteFragment.getTag());
            editNoteFragment.setArguments(bundle);
            notifyItemChanged(getAdapterPosition(), noteItem);
            notifyDataSetChanged();
        }

        private void deleteNoteItem(final int position) {
            mBuilder = new AlertDialog.Builder(mContext);
            mLayoutInflater = LayoutInflater.from(mContext);
            View view = mLayoutInflater.inflate(R.layout.dialog_confirmationdelete_noteitem, null);

            Button noButton = view.findViewById(R.id.noButton);
            Button yesButton = view.findViewById(R.id.yesButton);

            mBuilder.setView(view);
            mAlertDialog = mBuilder.create();
            mAlertDialog.show();

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAlertDialog.dismiss();
                }
            });

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            mRealmNoteList.deleteFromRealm(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, mRealmNoteList.size());

                        }
                    });

                    mAlertDialog.dismiss();
                }
            });
        }

    }
}
