package example.dell.jd.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import example.dell.jd.R;

public class Web3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web3);
        WebView wb= findViewById(R.id.web3);
        wb.loadUrl("https://search.jd.com/Search?keyword=%E5%89%83%E9%A1%BB%E5%88%80&enc=utf-8&suggest=1.def.0.V08&wq=ti&pvid=03d5363cde8c458e9a68d15e7b40fb2a");
    }
}
