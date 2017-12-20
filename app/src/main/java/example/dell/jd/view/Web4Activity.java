package example.dell.jd.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import example.dell.jd.R;

public class Web4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web4);
         WebView wb= findViewById(R.id.web4);
         wb.loadUrl("https://search.jd.com/Search?keyword=%E5%90%B9%E9%A3%8E%E6%9C%BA&enc=utf-8&suggest=2.def.0.V08&wq=chui&pvid=c03087834c1041b1b7795d70b35eead6");
    }
}
