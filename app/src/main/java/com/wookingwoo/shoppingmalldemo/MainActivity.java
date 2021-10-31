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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;

    private ListView listView;

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


        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        fab_add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });


//        listView = (ListView) findViewById(R.id.listView);
//
//        List<String> itemData = new ArrayList<>();
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemData);
//
//        listView.setAdapter(adapter);
//
//        itemData.add("아이템1");
//        itemData.add("아이템2");
//        itemData.add("아이템3");
//        itemData.add("아이템4");
//        itemData.add("아이템5");
//        itemData.add("아이템6");
//        itemData.add("아이템7");
//        itemData.add("아이템8");
//        itemData.add("아이템9");
//        itemData.add("아이템10");
//        itemData.add("아이템11");
//        itemData.add("아이템12");
//        adapter.notifyDataSetChanged();


        ListView lv = findViewById(R.id.listView);
        ItemList ilAdapter = new ItemList();

        lv.setAdapter(ilAdapter);

        ilAdapter.addItem("상품1", ContextCompat.getDrawable(this, R.drawable.shopping_cart));
        ilAdapter.addItem("상품2", ContextCompat.getDrawable(this, R.drawable.shopping_cart));
        ilAdapter.addItem("상품3", ContextCompat.getDrawable(this, R.drawable.shopping_cart));
        ilAdapter.addItem("상품4", ContextCompat.getDrawable(this, R.drawable.shopping_cart));
        ilAdapter.addItem("상품5", ContextCompat.getDrawable(this, R.drawable.shopping_cart));

        
    }
}