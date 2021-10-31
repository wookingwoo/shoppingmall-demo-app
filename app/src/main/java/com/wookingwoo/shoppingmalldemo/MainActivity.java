package com.wookingwoo.shoppingmalldemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();


        Button btn_accountInfo = (Button) findViewById(R.id.btn_accountInfo);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

// 로기인되지 않은 상태
        if (currentUser == null) {
            btn_accountInfo.setText("회원가입하기");
            btn_accountInfo.setBackgroundColor(btn_accountInfo.getContext().getResources().getColor(R.color.common_google_signin_btn_text_dark_default));
            btn_accountInfo.setBackgroundTintList(btn_accountInfo.getResources().getColorStateList(R.color.common_google_signin_btn_text_dark_default));


            btn_accountInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("회원가입");
                    builder.setMessage("회원가입 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            //"예" 버튼 클릭시 실행
                            Toast.makeText(getBaseContext(), "회원가입페이지로 이동합니다.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            //"아니오" 버튼 클릭시 실행
                            Toast.makeText(getBaseContext(), "회원가입을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.create().show();


                }
            });
        }

//        로그인 된상태
        else {
            btn_accountInfo.setText("접속자 정보:\n" + currentUser.getEmail());


            btn_accountInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("로그아웃");
                    builder.setMessage("로그아웃 하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            //"예" 버튼 클릭시 실행

                            // 로그아웃
                            mFirebaseAuth.signOut();

                            Intent intent = new Intent(MainActivity.this, SigninActivity.class);
                            startActivity(intent);

                            Toast.makeText(getBaseContext(), "로그아웃하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(
                                DialogInterface dialog, int id) {
                            //"아니오" 버튼 클릭시 실행
                            Toast.makeText(getBaseContext(), "로그아웃을 취소하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.create().show();


                }
            });


        }


    }
}