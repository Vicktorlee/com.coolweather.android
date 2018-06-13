package com.coolweather.android;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coolweather.android.R;
import com.coolweather.android.db.User;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

public class LoginActivity extends AppCompatActivity {
    private Button login_sub;
    private Button sign_in;
    private EditText usernameText;
    private EditText passwordText;
    private String userName;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Acquire all the widgets.
        login_sub=(Button) findViewById(R.id.log_in_button);
        sign_in=(Button) findViewById(R.id.sign_in_button);
        usernameText=(EditText) findViewById(R.id.username_text);
        passwordText=(EditText) findViewById(R.id.password_text);
//        acquire input.
        userName=usernameText.getText().toString();
        password=passwordText.getText().toString();
//        Logic of Sign-in Activity (Temporary)
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataSupport.where("userName=?",userName).find(User.class)!=null){
                    Toast.makeText(LoginActivity.this,"Username already exists" +
                            ".Please choose a different name!",Toast.LENGTH_SHORT).show();
                }else{
                    User user=new User();
                    user.setUserName(userName);
                    user.setUserPassword(password);
                    user.save();
                    Toast.makeText(LoginActivity.this,"User created success",Toast
                    .LENGTH_SHORT).show();
                }
            }
        });
        login_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user=new User();
                user.setUserName(userName);
                user.setUserPassword(password);
                if (DataSupport.where("userName=?",userName)
                .where("userPassword=?",password)
                    .find(User.class)==null){
                    Toast.makeText(LoginActivity.this,"Either no username existed" +
                            "yet, or your password is not correct",Toast.LENGTH_SHORT)
                    .show();
                }else{
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_SHORT)
                            .show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("userName",userName);
                    intent.putExtra("userPassword",password);
                    startActivity(intent);
                }
            }
        });
    }
}
