package com.example.projetv1;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class extract_and_save {
//https://androidapps-development-blogs.medium.com/how-to-retrieve-data-from-firebase-cloud-firestore-in-android-studio-quiz-app-java-android-12108341f93d

    private static ArrayList<Film> mArrayList = new ArrayList<>();

    private void getListItems() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ProjetAndroid").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.d("EMPTY", "onSuccess: LIST EMPTY");
                            return;
                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    Log.d("GOOD", "onSuccess: DOCUMENT" + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                    DocumentReference documentReference1 = FirebaseFirestore.getInstance().document("some path");
                                    documentReference1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Film film= documentSnapshot.toObject(Film.class);
                                            Log.d(TAG, "onSuccess: " + type.toString());
                                            mArrayList.add(type);
                                            Log.d(TAG, "onSuccess: " + mArrayList);
                                        /* these logs here display correct data but when
                                         I log it in onCreate() method it's empty*/
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
                );
    }
}
