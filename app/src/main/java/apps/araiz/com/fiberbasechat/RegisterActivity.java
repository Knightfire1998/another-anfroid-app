package apps.araiz.com.fiberbasechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private TextInputLayout mname;
    private TextInputLayout memail;
    private TextInputLayout mpass;
    private Button mcreatebtn;
    private Toolbar mtoolbar;

    //progress bar
    private ProgressDialog mregprogress;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //toolbarsetting
        mtoolbar = (Toolbar) findViewById(R.id.register_bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Accout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mregprogress = new ProgressDialog(this);
       //firebase
        mAuth = FirebaseAuth.getInstance();
       //reg fields
        mname =(TextInputLayout) findViewById(R.id.textInputLayout6);
        memail=(TextInputLayout) findViewById(R.id.login_email);
        mpass =(TextInputLayout) findViewById(R.id.login_pass);
        mcreatebtn=(Button) findViewById(R.id.regbtn);

        mcreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Disp_name = mname.getEditText().getText().toString();
                String Email = memail.getEditText().getText().toString();
                String Pass = mpass.getEditText().getText().toString();

                if(!TextUtils.isEmpty(Disp_name)||!TextUtils.isEmpty(Email)||!TextUtils.isEmpty(Pass)) {

                    mregprogress.setTitle(getString(R.string.Registering));
                    mregprogress.show();
                    mregprogress.setMessage("PLease wait!");
                    mregprogress.setCanceledOnTouchOutside(false);
                    reg_user(Disp_name, Email, Pass);


                }





            }
        });


    }

    private void reg_user(String Disp_name, String Email, String Pass) {
       //add user
        mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mregprogress.dismiss();
                            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                            finish();
                        } else {
                                  mregprogress.hide();
                            Toast.makeText(RegisterActivity.this, "Authentication failed."+
                                    task.getException(),
                                    Toast.LENGTH_LONG).show();

                        }


                    }
                });

    }
}
