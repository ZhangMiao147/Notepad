package com.zhangmiao.notepad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.Address;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.MoodContentBean;
import com.zhangmiao.notepad.bean.NoteContentBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;
import com.zhangmiao.notepad.location.MyLocationListener;

import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 写心情界面
 * Author: zhangmiao
 * Date: 2017/9/29
 */
public class WriteMoodActivity extends Activity {

    private static final String TAG = WriteMoodActivity.class.getSimpleName();

    private static final int DATA_WEATHER = 1;

    @BindView(R.id.activity_write_mood_content_text)
    EditText mContentEditText;

    @BindView(R.id.activity_write_mood_weather_iv)
    ImageView iv_weather; //天气

    @BindView(R.id.activity_write_mood_lock_iv)
    ImageView iv_lock;

    @BindView(R.id.activity_write_mood_mood_iv)
    ImageView iv_mood;

    @BindView(R.id.activity_write_mood_location_iv)
    ImageView iv_location;

    @BindView(R.id.activity_write_mood_location_layout_ll)
    LinearLayout ll_locationLayout;

    @BindView(R.id.activity_write_mood_location_province_tv)
    TextView tv_locationProvince;

    @BindView(R.id.activity_write_mood_location_city_tv)
    TextView tv_locationCity;


    private RecordDataBean bean;

    private boolean isSave;
    private boolean isUpdate;

    private int mMoodWeather;
    private int mMoodType;
    private boolean mMoodLock;
    private int mFontSize;
    private StringBuffer mLocation;

    private LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = new MyLocationListener();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mood);
        ButterKnife.bind(this);
        isSave = false;
        Intent intent = getIntent();
        RecordDataBean recordDataBean = (RecordDataBean) intent.getSerializableExtra("edit");
        if (recordDataBean == null) {
            isUpdate = false;

            bean = new RecordDataBean();
            mMoodWeather = DataBean.WEATHER_FINE;
            mMoodType = DataBean.MOOD_HAPPY;
            mFontSize = DataBean.FONT_MIDDLE;
            mMoodLock = false;
            mLocation = new StringBuffer();
        } else {
            isUpdate = true;
            bean = recordDataBean;
            MoodContentBean moodContentBean = MoodContentBean.jsonToBean(bean.getContent());
            mMoodWeather = moodContentBean.getWeather();
            mMoodType = moodContentBean.getMood();
            mMoodLock = moodContentBean.getIslock();
            mFontSize = moodContentBean.getFont();
            mContentEditText.setText(moodContentBean.getArticle());
            switch (moodContentBean.getWeather()) {
                case DataBean.WEATHER_FINE:
                    iv_weather.setImageResource(R.drawable.fine);
                    break;
                case DataBean.WEATHER_RAIN:
                    iv_weather.setImageResource(R.drawable.rain);
                    break;
                case DataBean.WEATHER_SHADE:
                    iv_weather.setImageResource(R.drawable.shade);
                    break;
                case DataBean.WEATHER_SNOW:
                    iv_weather.setImageResource(R.drawable.snow);
                    break;
                default:
                    break;
            }

            switch (moodContentBean.getMood()) {
                case DataBean.MOOD_HAPPY:
                    iv_mood.setImageResource(R.drawable.happy);
                    break;
                case DataBean.MOOD_UNHAPPY:
                    iv_mood.setImageResource(R.drawable.unhappy);
                    break;
                default:
                    break;
            }
            String location = moodContentBean.getLocation();
            if (!TextUtils.isEmpty(location)) {
                String[] lo = location.split("/*");
                if (lo != null) {
                    if (lo.length == 1) {
                        tv_locationCity.setText(lo[0]);
                    } else if (lo.length == 2) {
                        tv_locationCity.setText(lo[0]);
                        tv_locationProvince.setText(lo[1]);
                    }
                }
            }
            if (moodContentBean.getIslock()) {
                iv_lock.setImageResource(R.drawable.lock);
            } else {
                iv_lock.setImageResource(R.drawable.unlock);
            }
        }
        mContentEditText.setTextSize(mFontSize);
        mLocationClient = new LocationClient(getApplicationContext());
        myLocationListener.setGetAddressInfo(new MyLocationListener.GetAddressInfo() {
            @Override
            public void getAddressInfo(Address address) {
                if (address != null && !TextUtils.isEmpty(address.province)) {
                    if (iv_location != null && ll_locationLayout != null && tv_locationProvince != null && tv_locationCity != null) {
                        iv_location.setVisibility(View.GONE);
                        ll_locationLayout.setVisibility(View.VISIBLE);
                        tv_locationProvince.setText(address.province);
                        mLocation.append(address.province);
                        String city = address.city;
                        if (!TextUtils.isEmpty(city)) {
                            mLocation.append("*" + city);
                            tv_locationCity.setText(city);
                        } else {
                            tv_locationCity.setVisibility(View.GONE);
                        }
                    }
                }

            }
        });
        mLocationClient.registerLocationListener(myLocationListener);
        setLocationOption();
    }

    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();

        //可选，设置定位模式，默认是高精度
        //LocationMode.Hight_Accuracy: 高精度
        //LocationMode.Battery_Saving: 低功耗
        //LocationMode.Device_Sensors: 仅使用设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD0911：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，同意返回WGS84类型坐标
        option.setCoorType("bd0911");

        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);

        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，蚕食必须设置为true。
        option.setOpenGps(true);

        //可选，设置是否当GPS有效时按照1s/s次频率输出GPS结果，默认false
        option.setLocationNotify(true);

        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)
        option.setIgnoreKillProcess(false);

        //可选，设置是否收集Crash信息，默认收集，即参数为false
        option.SetIgnoreCacheException(false);

        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位
        option.setWifiCacheTimeOut(5 * 60 * 1000);

        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数是false
        option.setEnableSimulateGps(false);

        option.setIsNeedAddress(true);

        mLocationClient.setLocOption(option);

    }

    @OnClick(R.id.activity_write_mood_toolbar_back_iv)
    public void back() {
        if (isSave == false) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("是否不保存返回？")
                    .setNegativeButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("否", null);
            builder.show();
        } else {
            finish();
        }
    }

    @OnClick(R.id.activity_write_mood_toolbar_save_iv)
    public void save() {
        isSave = true;
        String content = mContentEditText.getText().toString();
        if (content.isEmpty()) {
            bean.setContent("无内容");
        } else {
            bean.setContent(content);
        }

        MoodContentBean moodContentBean = new MoodContentBean();
        moodContentBean.setWeather(mMoodWeather);
        moodContentBean.setMood(mMoodType);
        moodContentBean.setIslock(mMoodLock);
        moodContentBean.setArticle(content);
        moodContentBean.setFont(mFontSize);
        moodContentBean.setLocation(mLocation.toString());

        bean.setContent(moodContentBean.toJson());
        long time = (new Date()).getTime();
        bean.setUpdateDate(time);
        bean.setCrateDate(time);
        bean.setType(DataBean.DATA_TYPE_MOOD);
        bean.setIsWastebasket(false);
        Log.d(TAG, "bean:" + bean);
        if (isUpdate) {
            RecordDao.updateNote(bean);
        } else {
            bean.setId(UUID.randomUUID().toString());
            RecordDao.insertNote(bean);
        }
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.activity_write_mood_toolbar_undo_iv)
    public void undo() {

    }

    @OnClick(R.id.activity_write_mood_toolbar_redo_iv)
    public void redo() {

    }

    @OnClick(R.id.activity_write_mood_toolbar_font_iv)
    public void font() {
        switch (mFontSize) {
            case DataBean.FONT_SMILE:
                mFontSize = DataBean.FONT_MIDDLE;
                break;
            case DataBean.FONT_MIDDLE:
                mFontSize = DataBean.FONT_BIG;
                break;
            case DataBean.FONT_BIG:
                mFontSize = DataBean.FONT_SMILE;
                break;
            default:
                break;
        }
        mContentEditText.setTextSize(mFontSize);
    }

    @OnClick(R.id.activity_write_mood_toolbar_camera_iv)
    public void camera() {

    }

    @OnClick(R.id.activity_write_mood_location_iv)
    public void setLocation() {
        mLocationClient.start();
    }

    @OnClick(R.id.activity_write_mood_weather_iv)
    public void setWeather() {
        switch (mMoodWeather) {
            case DataBean.WEATHER_FINE:
                mMoodWeather = DataBean.WEATHER_SHADE;
                iv_weather.setImageResource(R.drawable.shade);
                break;
            case DataBean.WEATHER_SHADE:
                mMoodWeather = DataBean.WEATHER_RAIN;
                iv_weather.setImageResource(R.drawable.rain);
                break;
            case DataBean.WEATHER_RAIN:
                mMoodWeather = DataBean.WEATHER_SNOW;
                iv_weather.setImageResource(R.drawable.snow);
                break;
            case DataBean.WEATHER_SNOW:
                mMoodWeather = DataBean.WEATHER_FINE;
                iv_weather.setImageResource(R.drawable.fine);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.activity_write_mood_mood_iv)
    public void setMood() {
        switch (mMoodType) {
            case DataBean.MOOD_HAPPY:
                mMoodType = DataBean.MOOD_UNHAPPY;
                iv_mood.setImageResource(R.drawable.unhappy);
                break;
            case DataBean.MOOD_UNHAPPY:
                mMoodType = DataBean.MOOD_HAPPY;
                iv_mood.setImageResource(R.drawable.happy);
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.activity_write_mood_lock_iv)
    public void clock() {
        mMoodLock = !mMoodLock;
        if (mMoodLock) {
            iv_lock.setImageResource(R.drawable.lock);
        } else {
            iv_lock.setImageResource(R.drawable.unlock);
        }
    }

}
