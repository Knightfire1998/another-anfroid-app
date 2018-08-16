package apps.araiz.com.fiberbasechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class loginactivity extends AppCompatActivity {
private TextInputLayout mLoginemail;
private TextInputLayout mLoginpass;
    private Toolbar mToolbar;
    private Button mloginbtn;
    private ProgressDialog mlogprogress;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        mAuth = FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.login_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.loginbar);

        mLoginemail= (TextInputLayout) findViewById(R.id.login_email);
        mLoginpass = (TextInputLayout) findViewById(R.id.login_pass);
        mloginbtn =  (Button) findViewById(R.id.loginbtn);
        mlogprogress = new ProgressDialog(this);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginemail.getEditText().getText().toString();
               String pass = mLoginpass.getEditText().getText().toString();

               if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(pass)){
                        mlogprogress.setTitle("Logging in");
                        mlogprogress.setMessage("Wait while process is running");
                        mlogprogress.setCanceledOnTouchOutside(false);
                        mlogprogress.show();
                        loginUser(email,pass);

               }


            }
        });


    }

    private void loginUser(String email, String pass) {




        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mlogprogress.dismiss();
                    Intent mainIntent = new Intent(loginactivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();

                }
                else{


                    mlogprogress.hide();
                    Toast.makeText(loginactivity.this, "Login failed."+
                                    task.getException(),
                            Toast.LENGTH_LONG).show();

                }
            }
        });


    }


}
