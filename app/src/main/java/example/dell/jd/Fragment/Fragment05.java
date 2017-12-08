package example.dell.jd.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import example.dell.jd.R;
import example.dell.jd.view.LoginActivity;

/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment05 extends Fragment implements View.OnClickListener {
    private View view;
    /**
     * 登录/注册>
     */
    private TextView mTvLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment05, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        mTvLogin = (TextView) view.findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }


}
