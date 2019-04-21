package com.example.ratems;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommentPost extends AppCompatActivity {
private EditText nameC;
private EditText commentC;
private Button submit;
private Query query;
private DatabaseReference userTableReference, commentTableReference;
    private SharedPreferences preferences;
    SharedPreferences.Editor editor;
   static String s="";
private  static int i=0;
    private long commentCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_post);
        nameC=findViewById(R.id.name);
        commentC=findViewById(R.id.comment);
        submit=findViewById(R.id.submit);

        commentTableReference = FirebaseDatabase.getInstance().getReference().child("Comment");
        userTableReference = FirebaseDatabase.getInstance().getReference().child("USER");

        preferences = getApplicationContext().getSharedPreferences("myref", Context.MODE_PRIVATE); // 0 - for private mode
         editor = preferences.edit();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }


        });


       //for counting comment
userTableReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (dataSnapshot.getChildrenCount()!=0){
            final String userId=preferences.getString("id1","");
            commentTableReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
query =commentTableReference.orderByChild("userId").equalTo(userId);
query.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.getChildrenCount()!=0){
            userTableReference.child(preferences.getString("id1","")).child("commentCount").setValue(dataSnapshot.getChildrenCount());

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    private void startPosting() {

        String c=commentC.getText().toString().trim();
        if (!TextUtils.isEmpty(c)){

        String    id2= UUID.randomUUID().toString();
            String    id1= UUID.randomUUID().toString();
            String n=nameC.getText().toString().trim();

            String user =preferences.getString("id1","");
        if(user.isEmpty()) {
            editor.putString("id1",id1);
            editor.apply();

            commentTableReference.child(id2).child("CommentText").setValue(c);
            commentTableReference.child(id2).child("userId").setValue(id1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            commentTableReference.child(id2).child("time").setValue(time);

            userTableReference.child(id1).child("name").setValue(n);
            userTableReference.child(id1).child("commentCount").setValue("1");

            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            commentTableReference.child(id2).child("CommentText").setValue(c);
            String string=preferences.getString("id1","");
            commentTableReference.child(id2).child("userId").setValue(string);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            commentTableReference.child(id2).child("time").setValue(time);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        }
    }
}
