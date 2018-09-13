package com.martel.database;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.martel.database.models.Post;
import com.martel.database.models.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private EditText mNomeField;
    private EditText mProcField;
    private EditText mQtdField;
    private EditText mIdadeField;
    private EditText mDataField;
    private EditText mHoraField;
    private EditText mObsField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        mNomeField = findViewById(R.id.field_nome);
        mProcField = findViewById(R.id.field_proc);
        mIdadeField = findViewById(R.id.field_idade);
        mQtdField = findViewById(R.id.field_qtd);
        mDataField = findViewById(R.id.field_data);
        mHoraField = findViewById(R.id.field_hora);
        mSubmitButton = findViewById(R.id.fab_submit_post);
        mObsField = findViewById(R.id.field_obs);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String nome = mNomeField.getText().toString();
        final String proc = mProcField.getText().toString();
        final String idade = mIdadeField.getText().toString();
        final String qtd = mQtdField.getText().toString();
        final String data = mDataField.getText().toString();
        final String hora = mHoraField.getText().toString();
        final String obs = mObsField.getText().toString();

        // Title is required
        if (TextUtils.isEmpty(nome)) {
            mNomeField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(proc)) {
            mProcField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(idade)) {
            mIdadeField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(qtd)) {
            mQtdField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(data)) {
            mDataField.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(hora)) {
            mHoraField.setError(REQUIRED);
            return;
        }


        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Salvando...", Toast.LENGTH_SHORT).show();

        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, user.username, nome, proc,qtd,idade,hora,data,obs);
                        }

                        // Finish this Activity, back to the stream
                        setEditingEnabled(true);
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        // [START_EXCLUDE]
                        setEditingEnabled(true);
                        // [END_EXCLUDE]
                    }
                });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mNomeField.setEnabled(enabled);
        mProcField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    // [START write_fan_out]
    private void writeNewPost(String userId, String username, String paciente, String proc, String qtd, String idade, String hora, String data, String obs) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, paciente, proc, qtd, idade, hora, data, obs);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
    // [END write_fan_out]
}
