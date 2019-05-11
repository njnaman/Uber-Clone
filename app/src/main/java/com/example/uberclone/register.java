package com.example.uberclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
private String current_category;
private TextView user_name_rg,user_pswrd_rg,register_label;
private Button register;
FirebaseAuth firebaseAuth;
FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        current_category = getIntent().getStringExtra("clicked");
        user_name_rg = findViewById(R.id.user_name_rg);
        user_pswrd_rg = findViewById(R.id.user_pswrd_rg);
        register = findViewById(R.id.register);
        firebaseAuth = FirebaseAuth.getInstance();

        register_label = findViewById(R.id.register_label);
        if(current_category.equals("driver"))
            register_label.setText("Driver Register");
        else if(current_category.equals("customer"))
            register_label.setText("Customer Register");



        /*authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(register.this,map.class);
                    intent.putExtra("clicked","driver");
                    startActivity(intent);
                    finish();


                }
            }
        };*/




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = user_name_rg.getText().toString();
               final String pswrd = user_pswrd_rg.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(username,pswrd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful())
                        {
                            FirebaseAuthException e = (FirebaseAuthException)task.getException();
                            Toast.makeText(register.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        else {
                            String uid = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference;
                            if(current_category.equals("driver"))
                            databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(uid);
                            else databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(uid);
                            databaseReference.setValue(true);
                            Toast.makeText(register.this, "registration successful Logging in...", Toast.LENGTH_SHORT).show();

                            firebaseAuth.signInWithEmailAndPassword(username,pswrd).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                        Toast.makeText(register.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Intent intent = new Intent(register.this, customer_map.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }
                    }
                });

            }
        });







    }

    /*@Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }*/
}
