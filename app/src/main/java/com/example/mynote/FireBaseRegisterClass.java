package com.example.mynote;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class FireBaseRegisterClass extends Fragment {
    EditText userName,userEmail,userPass,userConfirmPass;
    TextView alreadylogintv;
    Button registerButton;
    Context context;
    FirebaseAuth fauth;
    ForCommunication forCommunication;
    FireBaseRegisterClass(Context context ){
        this.context = context;
        forCommunication = (ForCommunication) context;
    }
    @Override
    public void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.firebase_register_class,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         userName = view.findViewById(R.id.userName);
         userEmail = view.findViewById(R.id.userEmailAddress);
         userPass = view.findViewById(R.id.userPassword);
         userConfirmPass = view.findViewById(R.id.useConfirmPassword);
         alreadylogintv = view.findViewById(R.id.alreadylogin);
         registerButton = view.findViewById(R.id.registerButton);
         alreadylogintv.setOnClickListener(View->
         {

             forCommunication.alreadyLoginHandle();
         });
         registerButton.setOnClickListener(View->
         {
             String email = userEmail.getText().toString();
             String name = userName.getText().toString();
             String pass = userPass.getText().toString();
             String confirmPass = userConfirmPass.getText().toString();
             if (email.isEmpty() || email==null || name.isEmpty() ||name==null || pass.isEmpty() || pass == null || confirmPass.isEmpty() || confirmPass ==null) {
                 Toast.makeText(context, "All Fields Are Required", Toast.LENGTH_SHORT).show();

             } else if (!confirmPass.equals(pass)) {
                 userConfirmPass.setError("Password Does Not Match");
             } else {
                 fauth = FirebaseAuth.getInstance();
                 fauth.createUserWithEmailAndPassword(email, confirmPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                     @Override
                     public void onSuccess(AuthResult authResult) {
                         FirebaseUser user = fauth.getCurrentUser();
                         UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                 .setDisplayName(name)
                                 .build();
                         user.updateProfile(request);
                         forCommunication.onCancelPressed();
                         Toast.makeText(context, "You Are Logged In SuccessFully", Toast.LENGTH_SHORT).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(context, "Error :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 });
             }
         });
    }
}

