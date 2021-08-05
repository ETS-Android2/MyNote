package com.example.mynote;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginClass extends Fragment {
   EditText userEmail,userPassword;
   Button loginButton;
   FirebaseAuth fauth;
   Context context;
   ForCommunication forCommunication;
   MainFragment.ForFab forFab;
   ProgressBar progressBar;
   LoginClass(Context context){
       this.context = context;
       forFab = (MainFragment.ForFab)context;
       forCommunication = (ForCommunication)context;
   }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userEmail = view.findViewById(R.id.userEmailAddressLogin);
        userPassword = view.findViewById(R.id.userPasswordLogin);
        progressBar = view.findViewById(R.id.loginProgressBar);
        loginButton = view.findViewById(R.id.loginbutton);
        fauth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(View->{
            progressBar.setVisibility(android.view.View.VISIBLE);
            String email = userEmail.getText().toString();
            String userpass = userPassword.getText().toString();
            if(email.isEmpty()|| userpass.isEmpty()){
                Toast.makeText(context, "All Fields Are Requied To Fill", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(android.view.View.INVISIBLE);
                if(email.isEmpty()){
                    userEmail.setError("This Field Is Required");
                }
                else{
                    userPassword.setError("This Field Is Required");
                }
            }
            else {
                fauth.signInWithEmailAndPassword(email, userpass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fauth.getCurrentUser();
                        Toast.makeText(context, "You Are Successfully Logged In ", Toast.LENGTH_SHORT).show();
                        forCommunication.replaceFragment();
                        forFab.setVisibility();
                        forCommunication.forNameSet();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(android.view.View.INVISIBLE);
                        Toast.makeText(context, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
