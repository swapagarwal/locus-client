package com.justiceleague.locus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.justiceleague.locus.LoginActivity;
import com.justiceleague.locus.R;
import com.justiceleague.locus.SignUpActivity;

public class LoginSignup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
    }

    public void signIn(View view){
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void signUp(View view){
        Intent intent=new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
