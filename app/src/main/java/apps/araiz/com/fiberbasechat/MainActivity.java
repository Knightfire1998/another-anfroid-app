package apps.araiz.com.fiberbasechat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;



import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static apps.araiz.com.fiberbasechat.R.menu.main_menu;

public class MainActivity extends AppCompatActivity {


    private Toolbar mtoolbar;
    private FirebaseAuth mAuth;
    private Button Slateview;
    private ViewPager mViewPager;
    private SectionPagerAdapter mSectionPagerAdapter;
    private TabLayout mTabLayout;
    private Button mviewportbtn;
    private Toolbar mactionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mtoolbar= (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Firebasechat");


        //Tabs
        mViewPager = (ViewPager) findViewById(R.id.main_tabPager);
        mSectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionPagerAdapter);


        mTabLayout = (TabLayout)  findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);




        //Slate
        Slateview = (Button) findViewById(R.id.Slate);
        Slateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Slate_view = new Intent(MainActivity.this,activity_slate.class);
                startActivity(Slate_view);

            }
        });


        //viewport
        mviewportbtn= (Button) findViewById(R.id.mviewportbtn);
        mviewportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent viewIntent = new Intent(MainActivity.this,ViewPort.class);
                startActivity(viewIntent);
                viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                finish();

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();


        if(currentUser == null){
                sendtostart();



        }
    }

    private void sendtostart() {
        Intent startIntent = new Intent(MainActivity.this,StartActivity.class);

        startActivity(startIntent);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() ==  R.id.main_logout_btn){

            FirebaseAuth.getInstance().signOut();
            sendtostart();
        }
        if(item.getItemId() == R.id.main_setting_btn){

            Intent settingsintent = new Intent(MainActivity.this, activity_settings.class);
            startActivity(settingsintent);
            //finish();
        }

        return  true;
    }


}
