package com.example.ratems;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    DatabaseReference fireData;

    private FirebaseRecyclerAdapter<Comment, BlogViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        fireData = FirebaseDatabase.getInstance().getReference().child("Comment");
        FirebaseRecyclerOptions<Comment> options =
                new FirebaseRecyclerOptions.Builder<Comment>()
                        .setQuery(fireData, Comment.class)
                        .build();
firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Comment, BlogViewHolder>(options) {
    @Override
    protected void onBindViewHolder(@NonNull final BlogViewHolder holder, int position, @NonNull Comment model) {
       // holder.setName(model.getName());
        String usr=model.getUserId();
      DatabaseReference userTableRef=FirebaseDatabase.getInstance().getReference().child("USER");
      userTableRef.child(usr).child("name").addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              String userName=dataSnapshot.getValue(String.class);
              holder.setName(userName);
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
        holder.setCommentText(model.getCommentText());
       holder.setTime(model.getTime());
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

        return new BlogViewHolder(view, getApplicationContext());

    }
};
recyclerView.setHasFixedSize(true);
firebaseRecyclerAdapter.startListening();
recyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        Context context;


        BlogViewHolder(View itemView, Context context) {
            super(itemView);
            mView = itemView;
            this.context = context;

        }

        public void setCommentText(String Comment) {
            TextView comment = mView.findViewById(R.id.text3);
            comment.setText(Comment);


        }

        public void setName(final String name) {
            TextView Name = mView.findViewById(R.id.text2);
            Name.setText(name);

        }

        public void setTime(String time) {
            TextView textView = mView.findViewById(R.id.text1);
            textView.setText(time);
        }
    }
}