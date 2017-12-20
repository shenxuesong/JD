package example.dell.jd.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import example.dell.jd.Bean.UserBean;
import example.dell.jd.EventBus.MessageEvent1;
import example.dell.jd.IActivity.IUsers;
import example.dell.jd.R;
import example.dell.jd.net.CallBackListener;
import example.dell.jd.net.HttpUtils1;
import example.dell.jd.persenter.UpLoadPersenter;
import example.dell.jd.persenter.UserPersenter;
import example.dell.jd.view.LoginActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Dell on 2017/11/30.
 */

public class Fragment05 extends Fragment implements View.OnClickListener,IUsers ,CallBackListener {
    private View view;
    /**
     * 登录/注册>
     */
    private TextView mTvLogin;
    private String uid;
    private Bitmap head;// 头像Bitmap
    private UserPersenter userPersenter;
    private ImageView img;
    private static String path = "/sdcard/myHead/";
    private UserBean.DataBean data;
    private String nickname;
    private UpLoadPersenter upLoadPersenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //上传图片
      //  upLoadPersenter = new UpLoadPersenter(this);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment05, container, false);
        userPersenter = new UserPersenter(this);
        initView(view);

        return view;
    }

    private void initView(View view) {
        mTvLogin = (TextView) view.findViewById(R.id.tv_login);
        img = view.findViewById(R.id.iv_tx);
        img.setOnClickListener(this);
        mTvLogin.setOnClickListener(this);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
           img.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
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
            case R.id.iv_tx:
                showTypeDialog();
                break;
        }

    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMess(MessageEvent1 event) {

        uid = event.getUid();

        //Log.i("CARTUID", uid);
        if(uid !=null&& uid !=""){
            userPersenter.getMP(uid+"");

            //上传图片
       //     upLoadPersenter.UpLoad("storage/emulated/0/myHead/head.jpg",uid);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showBean(UserBean userBean) {
     //   Log.i("USER",userBean.getMsg());
        data = userBean.getData();
        data.setNickname("雪松");
        nickname = (String) data.getNickname();
        mTvLogin.setText(nickname);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTvLogin.setText(nickname);
    }

    private void showTypeDialog() {
        //上传图片
        HashMap<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("uid", "71");
       HttpUtils1.getHttpUtils().uploadFile(paramsMap, "/abc123.jpg", this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "mmexport1511619684769.jpg")));
                          startActivityForResult(intent2, 2);// 采用ForResult打开
                        dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/mmexport1511619684769.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        img.setImageBitmap(head);// 用ImageView显示出来
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "mmexport1511619684769.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

  /*  @Override
    public void showStr(String s) {
        Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();
    }
*/
    @Override
    public void onSuccess(String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String msg) {
       // Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
