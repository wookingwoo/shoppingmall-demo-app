package com.wookingwoo.shoppingmalldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;
    private EditText mEtEmail, mEtPw, mETConfirmPW, mETUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseRef = database.getReference("shoppingmallDemo");


        mEtEmail = findViewById(R.id.et_email);
        mEtPw = findViewById(R.id.et_pw);
        mETConfirmPW = findViewById(R.id.et_confirmPW);
        mETUserName = findViewById(R.id.et_userName);
        Button mBtnSigup = findViewById(R.id.btn_register);


        mBtnSigup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strEmail = mEtEmail.getText().toString();
                String strPw = mEtPw.getText().toString();
                String strConfirmPW = mETConfirmPW.getText().toString();
                String strUserName = mETUserName.getText().toString();


                RadioButton rb_accept = (RadioButton) findViewById(R.id.rb_accept);


                if (strEmail.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Input email", Toast.LENGTH_SHORT).show();

                } else if (!TextUtils.isEmpty(strEmail) && !(Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
                    Toast.makeText(SignupActivity.this, "????????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();

                } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{8,15}.$", strPw)) {
                    Toast.makeText(SignupActivity.this, "?????? ????????? ???????????????: (??????, ??????, ??????????????? ?????? ????????? 8~15???)", Toast.LENGTH_SHORT).show();

                } else if (strConfirmPW.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Input Confirm Password", Toast.LENGTH_SHORT).show();

                } else if (!strPw.equals(strConfirmPW)) {
                    Toast.makeText(SignupActivity.this, "???????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();

                } else if (strUserName.length() == 0) {
                    Toast.makeText(SignupActivity.this, "Input your name", Toast.LENGTH_SHORT).show();

                } else if (!rb_accept.isChecked()) {

                    Toast.makeText(SignupActivity.this, "???????????? ??????????????? ???????????? ????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();

                } else {
                    // ???????????? ??????
                    mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // ???????????? ????????????
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                                UserAccount account = new UserAccount();

                                account.setIdToken(firebaseUser.getUid());
                                account.setEmailId(firebaseUser.getEmail());
                                account.setPassword(strPw);
                                account.setUserName(strUserName);


                                // fire sotre ??? ??????
                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                db.collection("UserAccount").document(firebaseUser.getUid()).set(account)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("firestore-user-account", "DocumentSnapshot successfully written!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("firestore-user-account", "Error writing document", e);
                                            }
                                        });


                                // realtime database ??? ??????
//                                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);


                                Toast.makeText(SignupActivity.this, "Complete Sign up", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                                startActivity(intent);

                            } else {

                                if (task.getException() != null) {

                                    String errorString = task.getException().toString();

                                    if (errorString.equals("com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The email address is badly formatted.")) {
                                        Toast.makeText(SignupActivity.this, "????????? ????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();

                                    } else if (errorString.equals("com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account.")) {
                                        Toast.makeText(SignupActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }


                                    Log.d("sign-up-error", errorString);


                                } else {
                                    Toast.makeText(SignupActivity.this, "Signup error", Toast.LENGTH_SHORT).show();
                                }


                            }
                        }
                    });

                }
            }
        });


        Button btnGoSignin = findViewById(R.id.btn_goSignin);

        btnGoSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(intent);

            }
        });


        Button bt_viewPrivacy = findViewById(R.id.bt_viewPrivacy);

        bt_viewPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle(R.string.privacy);
                builder.setMessage("This app was created as an personal assignment in MobileProgramming class. The back-end was configured using firebase. If there is a problem with the server connection, it may not work normally. The last modified product may not be saved due to BaseAdapter's life cycle problem. For account information, Firebase's Authentication was used, and for product information by account, Firestore Database was used. Don't enter your personal information or password. The source of the image used in the app is Pixabay and follows the Pixabay License.\n\nMade by Jeongheon Woo.");
                builder.setIcon(android.R.drawable.ic_dialog_alert);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), ":)", Toast.LENGTH_SHORT).show();
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
    }
}