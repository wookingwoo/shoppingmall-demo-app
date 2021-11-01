package com.wookingwoo.shoppingmalldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private String fb_uid;

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
            btn_accountInfo.setText("[로그아웃하기]\n" + currentUser.getEmail());


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


        final String[] menuArray = {"cake", "chicken", "hamburger", "meat", "pasta", "pizza", "sandwich"};


        Map<String, Integer> menuImgMap = new HashMap<>();
        menuImgMap.put("cake", R.drawable.cake_img);
        menuImgMap.put("chicken", R.drawable.chicken_img);
        menuImgMap.put("hamburger", R.drawable.hamburger_img);
        menuImgMap.put("meat", R.drawable.meat_img);
        menuImgMap.put("pasta", R.drawable.pasta_img);
        menuImgMap.put("pizza", R.drawable.pizza_img);
        menuImgMap.put("sandwich", R.drawable.sandwich_img);
        menuImgMap.put("기본 상품1", R.drawable.shopping_cart);
        menuImgMap.put("기본 상품2", R.drawable.shopping_cart);
        menuImgMap.put("기본 상품3", R.drawable.shopping_cart);
        menuImgMap.put("기본 상품4", R.drawable.shopping_cart);
        menuImgMap.put("기본 상품5", R.drawable.shopping_cart);


        ListView lv = findViewById(R.id.listView);
        ItemListAdopter ilAdapter = new ItemListAdopter();

        lv.setAdapter(ilAdapter);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


        if (firebaseUser != null) {

            fb_uid = "3CcUTGI5dKQaSuuSUoquigrTRQG3"; // guest UID
        } else {


            try {
                fb_uid = firebaseUser.getUid();

            } catch (Exception e) {
                fb_uid = "3CcUTGI5dKQaSuuSUoquigrTRQG3"; // guest UID

            }


        }

        DocumentReference docRef = db.collection("ShoppingCart").document(fb_uid);

// Source can be CACHE, SERVER, or DEFAULT.
        Source source = Source.CACHE;

// Get the document, forcing the SDK to use the offline cache

        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                try {

                    if (task.isSuccessful()) {
                        // Document found in the offline cache
                        DocumentSnapshot document = task.getResult();
                        Log.d("get-cart-item-firestore", "Cached document data: " + document.getData());


                        List<String> getCartItemFS = (List<String>) document.get("items");
                        Log.d("get-cart-item-firestore", "33333->" + getCartItemFS);

                        int nullCount = 0;

                        for (String s : getCartItemFS) {
                            if (s != null && !s.equals("null")) {
                                Log.d("get-cart-item-firestore", "44444->" + s);
                                ilAdapter.addItem(s, ContextCompat.getDrawable(MainActivity.this, menuImgMap.get(s)));
                                ilAdapter.notifyDataSetChanged(); // listview 갱신


                            } else {
                                nullCount++;
                            }

                        }


                        if (nullCount >= 500) {
                            ilAdapter.addItem("기본 상품1", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                            ilAdapter.addItem("기본 상품2", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                            ilAdapter.addItem("기본 상품3", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                            ilAdapter.addItem("기본 상품4", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                            ilAdapter.addItem("기본 상품5", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                            ilAdapter.notifyDataSetChanged(); // listview 갱신

                        }


                    } else {
                        Log.d("get-cart-item-firestore", "Cached get failed: ", task.getException());

                        ilAdapter.addItem("기본 상품1", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                        ilAdapter.addItem("기본 상품2", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                        ilAdapter.addItem("기본 상품3", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                        ilAdapter.addItem("기본 상품4", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                        ilAdapter.addItem("기본 상품5", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                        ilAdapter.notifyDataSetChanged(); // listview 갱신


                    }

                } catch (Exception e) {
                    //에러시 수행
                    ilAdapter.addItem("기본 상품1", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                    ilAdapter.addItem("기본 상품2", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                    ilAdapter.addItem("기본 상품3", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                    ilAdapter.addItem("기본 상품4", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                    ilAdapter.addItem("기본 상품5", ContextCompat.getDrawable(MainActivity.this, R.drawable.shopping_cart));
                    ilAdapter.notifyDataSetChanged(); // listview 갱신
                }
            }
        });


        ilAdapter.notifyDataSetChanged(); // listview 갱신
//        ilAdapter.uploadDB(); // Firestore에 갱신


        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_add);


        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);

                dlg.setTitle("상품 추가");
                dlg.setIcon(R.drawable.shopping_cart);
                dlg.setItems(menuArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  array[which]: 클릭한 값
                        Toast.makeText(getApplicationContext(), menuArray[which] + "를 추가했습니다.", Toast.LENGTH_LONG).show();
                        ilAdapter.addItem(menuArray[which], ContextCompat.getDrawable(getBaseContext(), menuImgMap.get(menuArray[which])));
                        ilAdapter.notifyDataSetChanged(); // listview 갱신
                        ilAdapter.uploadDB(); // Firestore에 갱신


                    }
                });
                dlg.show();
            }
        });


    }
}
