package com.husseinelkheshen.elgayza;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class DataListener {
    public interface OnDataListener {
        void onStart();
        void onSuccess(DataSnapshot data);
        void onFailed(DatabaseError databaseError);
    }
}
