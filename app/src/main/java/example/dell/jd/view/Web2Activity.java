package example.dell.jd.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import example.dell.jd.R;

/**
 * 全球售
 */
public class Web2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web1);
      WebView wb= findViewById(R.id.web1);
      wb.loadUrl("https://channel.jd.com/fashion.html");
    }
}
