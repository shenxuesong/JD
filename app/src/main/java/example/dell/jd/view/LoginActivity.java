package example.dell.jd.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import example.dell.jd.Bean.LoginBean;
import example.dell.jd.EventBus.MessageEvent1;
import example.dell.jd.IActivity.RegActivity;
import example.dell.jd.R;
import example.dell.jd.persenter.RegPersenter;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener ,RegActivity{

    /**
     * 请输入手机号码
     */
    private EditText mEtSj;
    /**
     * 请输入密码
     */
    private EditText mEtMm;
    /**
     * 登陆
     */
    private Button mLogin;
    /**
     * 注册
     */
    private Button mZhuce;
    private ImageView mWeixin;
    private ImageView mQq;
    private RegPersenter regPersenter;
    private int uid;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        regPersenter = new RegPersenter(this);

    }

    private void initView() {
        mEtSj = (EditText) findViewById(R.id.et_sj);
        mEtMm = (EditText) findViewById(R.id.et_mm);
        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mZhuce = (Button) findViewById(R.id.zhuce);
        mZhuce.setOnClickListener(this);
        mWeixin = (ImageView) findViewById(R.id.weixin);
        mWeixin.setOnClickListener(this);
        mQq = (ImageView) findViewById(R.id.qq);
        mQq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.login:

                    //点击登陆的事件
                    regPersenter.getLogin(mEtSj.getText().toString().trim(), mEtMm.getText().toString().trim());

                //登陆成功跳转
                startActivity(new Intent(LoginActivity.this, ShowGoodsActivity.class));


                break;
            case R.id.zhuce:
                //注册的点击事件
                if(mEtSj.getText().toString().trim()!=null&&mEtMm.getText().toString().trim()!=null){
                    regPersenter.getMP(mEtSj.getText().toString().trim(),mEtMm.getText().toString().trim());
                }

                break;
            case R.id.weixin:
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.WEIXIN, authListener);
                //登陆成功跳转
            //    startActivity(new Intent(LoginActivity.this,ShowGoodsActivity.class));

                break;
            case R.id.qq:
                //授权
                UMShareAPI.get(LoginActivity.this).doOauthVerify(LoginActivity.this, SHARE_MEDIA.QQ, authListener);

                Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * 成功后调用的方法
         * @param platform
         * @param action
         * @param data
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            String temp = "";
            for (String key : data.keySet()) {
                temp = temp + key + " : " + data.get(key) + "\n";
            }
          //     Toast.makeText(LoginActivity.this, temp, Toast.LENGTH_LONG).show();
           // Log.e("dfsdaf",temp);

            //登陆成功跳转
            startActivity(new Intent(LoginActivity.this, ShowGoodsActivity.class));
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消", Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this, "开始了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            //登陆成功跳转
            startActivity(new Intent(LoginActivity.this,ShowGoodsActivity.class));
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LoginActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();

        }
    };

    @Override
    public void ShowBean(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ShowStr(String s, LoginBean.DataBean data) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        uid = data.getUid();
        Log.i("UID",uid+"");
        //传值给详情页和购物车
        EventBus.getDefault().postSticky(new MessageEvent1(uid+""));
    }
}
