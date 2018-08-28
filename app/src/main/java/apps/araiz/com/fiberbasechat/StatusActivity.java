package apps.araiz.com.fiberbasechat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class StatusActivity extends AppCompatActivity {
private Toolbar mtoolbar;
private TextInputLayout mstatus;
private Button msavebtn;


private ProgressDialog mprogressbar;

//firebase
private DatabaseReference mStatusdatabase;
private FirebaseUser mcurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mtoolbar = (Toolbar) findViewById(R.id.status_App_Bar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String Status_value = getIntent().getStringExtra("status_value");
        mstatus = (TextInputLayout) findViewById(R.id.statusinput);
        mstatus.getEditText().setText(Status_value);
        msavebtn = (Button) findViewById(R.id.status_save_Btn);

        mcurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mcurrentUser.getUid();
        mStatusdatabase = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);





        msavebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mprogressbar = new ProgressDialog(StatusActivity.this);
                mprogressbar.setTitle("Saving Changes");
                mprogressbar.setMessage("Please wait while we save the changes ");
                mprogressbar.show();

                String status = mstatus.getEditText().getText().toString();
                mStatusdatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            mprogressbar.dismiss();
                        }

                        else{


                            Toast.makeText(getApplicationContext(),"Error while saving"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}
