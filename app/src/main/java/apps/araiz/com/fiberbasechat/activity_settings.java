package apps.araiz.com.fiberbasechat;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_settings extends AppCompatActivity {


    private DatabaseReference muserDatabade;
    private FirebaseUser mCurrentUser;


    private TextView mdispname;
    private TextView mdispStatus;
    private CircleImageView mdispImage;
    private Button mstatusbtn;
    private Button mimgbtn;


    private static final int GALLERY_PICK=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

            mdispImage = (CircleImageView) findViewById(R.id.setting_image);
            mdispname = (TextView) findViewById(R.id.settingsdisp_name);
            mdispStatus = (TextView) findViewById(R.id.settings_status);


            mstatusbtn = (Button) findViewById(R.id.setting_status_btn);
            mimgbtn = (Button) findViewById(R.id.setting_image_btn);



            mstatusbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String status_value = mdispStatus.getText().toString();
                    Intent statusintent = new Intent(activity_settings.this,StatusActivity.class);
                    statusintent.putExtra("status_value",status_value);

                    startActivity(statusintent);
                    finish();
                }
            });




            mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
            String current_uid = mCurrentUser.getUid();

            //database refrence using uid
            muserDatabade = FirebaseDatabase.getInstance().getReference().child("users").child(current_uid);


            //to retrieve data from the given path

          muserDatabade.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                  String name = dataSnapshot.child("name").getValue().toString();
                  String status = dataSnapshot.child("status").getValue().toString();
                  String image = dataSnapshot.child("image").getValue().toString();
                  String thumbimage = dataSnapshot.child("thumb_image").getValue().toString();


                  mdispname.setText(name);
                  mdispStatus.setText(status);


              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });


    }

}

