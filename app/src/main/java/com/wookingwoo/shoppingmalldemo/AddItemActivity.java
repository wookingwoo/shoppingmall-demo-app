package com.wookingwoo.shoppingmalldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddItemActivity extends AppCompatActivity {


    private static final String TAG = "AddItemActivity";
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        findViewById(R.id.btn_registerItem).setOnClickListener(onClickListener);


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_registerItem:
                    profileUpdate();

                    break;
            }
        }
    };


    private void profileUpdate() {
        final String title = ((EditText) findViewById(R.id.et_itemTitle)).getText().toString();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        if (title.length() == 0) {

            startToast("상품명을 입력해주세요.");

        }


// 로그인되지 않은 상태
        else if (currentUser == null) {

            startToast("먼저 회원가입을 해주세요..");


        } else {


            RegisterItems writeInfo = new RegisterItems(title, currentUser.getUid());
            uploader(writeInfo);


        }

    }

    private void uploader(RegisterItems registerItems) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("items").add(registerItems)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        startToast("성공적으로 등록되었습니다.");

                        Intent intent = new Intent(AddItemActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // 현재 activity finish
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}