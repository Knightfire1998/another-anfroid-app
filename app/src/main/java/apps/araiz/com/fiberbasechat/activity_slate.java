package apps.araiz.com.fiberbasechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class activity_slate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PaintView PainView = new PaintView(this);
        setContentView(PainView);
    }
}
