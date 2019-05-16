package com.example.uberclone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button login_customer,login_driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_customer =findViewById(R.id.login_customer);
        login_driver = findViewById(R.id.login_driver);

        login_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login_page.class);
                intent.putExtra("clicked","customer");
                startActivity(intent);
               // finish();
            }
        });


        login_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login_page.class);
                intent.putExtra("clicked","driver");
                startActivity(intent);
               // finish();
            }
        });






    }
}
