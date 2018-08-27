package apps.araiz.com.fiberbasechat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class activity_settings extends AppCompatActivity {


    private DatabaseReference muserDatabade;
    private FirebaseUser mCurrentUser;


    private TextView mdispname;
    private TextView mdispStatus;
    private CircleImageView mdispImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

            mdispImage = (CircleImageView) findViewById(R.id.setting_image);
            mdispname = (TextView) findViewById(R.id.settingsdisp_name);
            mdispStatus = (TextView) findViewById(R.id.settings_status);



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

              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {

              }
          });



    }
}