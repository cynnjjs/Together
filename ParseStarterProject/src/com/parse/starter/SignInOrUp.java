package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SignInOrUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        Button signin_button,signup_button;
        signin_button=(Button) findViewById(R.id.signin_button);
        signup_button=(Button) findViewById(R.id.signup_button);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInOrUp.this, CheckLoginStatus.class);
                startActivity(intent);
                finish();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInOrUp.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
