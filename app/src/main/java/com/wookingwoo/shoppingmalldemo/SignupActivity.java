package com.wookingwoo.shoppingmalldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail, mEtPw;
    private Button mBtnSigup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("shoppingmallDemo");


        mEtEmail = findViewById(R.id.et_email);
        mEtPw = findViewById(R.id.et_pw);
        mBtnSigup = findViewById(R.id.btn_register);


        mBtnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입 시작

                String strEmail = mEtEmail.getText().toString();
                String strPw = mEtPw.getText().toString();


                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 회원가입 성공할때

                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();

                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPw);

                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(SignupActivity.this, "Complete Sign up", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SignupActivity.this, "Signup error", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


    }
}