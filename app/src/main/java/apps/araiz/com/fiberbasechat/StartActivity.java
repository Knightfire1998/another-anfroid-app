package apps.araiz.com.fiberbasechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class StartActivity extends AppCompatActivity {
    private Button mregbtn,mstartbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


       mstartbtn= (Button) findViewById(R.id.startlogin);
       mstartbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent loginIntent = new Intent(StartActivity.this,loginactivity.class);
               startActivity(loginIntent);
               finish();
           }
       });


        mregbtn= (Button) findViewById(R.id.startregbutton);
      mregbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent regIntent = new Intent(StartActivity.this,RegisterActivity.class);
              startActivity(regIntent);
              finish();

          }
      });







    }
}
