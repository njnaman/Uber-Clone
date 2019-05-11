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
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String current_category;
    private TextView username,pswrd,new_register,login_label;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //FirebaseApp.initializeApp(this);

        firebaseAuth= FirebaseAuth.getInstance();
        current_category = getIntent().getStringExtra("clicked");
        username=findViewById(R.id.user_name);
        pswrd=findViewById(R.id.user_pswrd);
        new_register=findViewById(R.id.new_register_label);
        login=findViewById(R.id.login);

        login_label = findViewById(R.id.login_label);
        if(current_category.equals("driver"))
            login_label.setText("Driver Login");
        else if(current_category.equals("customer"))
            login_label.setText("Customer Login");




        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){

                    if(current_category.equals("driver")){
                    Intent intent = new Intent(login_page.this, driver_map.class);
                    startActivity(intent);
                    finish();}
                    else {
                        Intent intent = new Intent(login_page.this, customer_map.class);
                        startActivity(intent);
                        finish();
                    }


                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=username.getText().toString();
                final String password=pswrd.getText().toString();
                if(current_category.equals("driver")){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(login_page.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Intent intent = new Intent(login_page.this, driver_map.class);
                                startActivity(intent);
                            }
                        }
                    });

                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(login_page.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Intent intent = new Intent(login_page.this, customer_map.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });



        new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_page.this,register.class);
                intent.putExtra("clicked",current_category);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }
}
