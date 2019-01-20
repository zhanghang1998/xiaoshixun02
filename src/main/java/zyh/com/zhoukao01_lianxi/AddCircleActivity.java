package zyh.com.zhoukao01_lianxi;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zyh.com.adapter.ImageAdapter;
import zyh.com.bean.Result;
import zyh.com.bean.UserInfo;
import zyh.com.core.DataCall;
import zyh.com.core.WDActivity;
import zyh.com.core.db.DaoMaster;
import zyh.com.core.db.UserInfoDao;
import zyh.com.core.exception.ApiException;
import zyh.com.frag.Fragment02_circle;
import zyh.com.presenter.PublishCirclePresenter;
import zyh.com.util.StringUtils;
import zyh.com.util.UIUtils;

public class AddCircleActivity extends AppCompatActivity implements DataCall<Result> {

    public final static int PHOTO = 0;// 相册选取
    public final static int CAMERA = 1;// 拍照

    /**
     * 记录处于前台的Activity
     */
    private static AddCircleActivity mForegroundActivity = null;

    @BindView(R.id.bo_text)
    EditText mText;

    @BindView(R.id.bo_image_list)
    RecyclerView mImageList;
    ImageAdapter mImageAdapter;
    PublishCirclePresenter presenter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_circle);
        ButterKnife.bind(this);
        //获取greendao数据库 ; 从而可以获取网络连接需要的参数
        UserInfoDao userInfoDao = DaoMaster.newDevSession(this, UserInfoDao.TABLENAME).getUserInfoDao();
        List<UserInfo> userInfos = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Status.eq(1)).list();
        if (userInfos != null && userInfos.size() > 0) {
            //读取第一项
            userInfo = userInfos.get(0);
        }

        mImageAdapter = new ImageAdapter();
        mImageAdapter.setSign(1);
        mImageAdapter.add(R.drawable.mask_01);
        mImageList.setLayoutManager(new GridLayoutManager(this, 3));
        mImageList.setAdapter(mImageAdapter);

        presenter = new PublishCirclePresenter(this);
    }


    @OnClick(R.id.back)
    public void back() {
        finish();
    }

    @OnClick(R.id.send)
    public void publish() {
        presenter.reqeust(userInfo.getUserId(),
                userInfo.getSessionId(),
                1, mText.getText().toString(), mImageAdapter.getList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//resultcode是setResult里面设置的code值
            String filePath = getFilePath(null,requestCode,data);
            if (!StringUtils.isEmpty(filePath)) {
                mImageAdapter.add(filePath);
                mImageAdapter.notifyDataSetChanged();
//                Bitmap bitmap = UIUtils.decodeFile(new File(filePath),200);
//                mImage.setImageBitmap(bitmap);
            }
        }

    }

    @Override
    public void success(Result data) {
        if (data.getStatus().equals("0000")){
            Fragment02_circle.addCircle = true;
            finish();
        }else{
            UIUtils.showToastSafe(data.getStatus()+"  "+data.getMessage());
        }
    }

    @Override
    public void fail(ApiException e) {
        UIUtils.showToastSafe(e.getCode()+"  "+e.getDisplayMessage());
    }


    /**
     * 获取当前处于前台的activity
     */
    public static AddCircleActivity getForegroundActivity() {
        return mForegroundActivity;
    }

    /**
     * 得到图片的路径
     *
     * @param fileName
     * @param requestCode
     * @param data
     * @return
     */
    public String getFilePath(String fileName, int requestCode, Intent data) {
        if (requestCode == CAMERA) {
            return fileName;
        } else if (requestCode == PHOTO) {
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            String img_path = actualimagecursor
                    .getString(actual_image_column_index);
            // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                actualimagecursor.close();
            return img_path;
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unBind();
    }
}
