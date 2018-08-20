package com.zhangmiao.notepad.location;

import android.util.Log;

import com.baidu.location.Address;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

/**
 * Author: zhangmiao
 * Date: 2018/8/20
 */
public class MyLocationListener extends BDAbstractLocationListener {

    public static final String TAG = MyLocationListener.class.getSimpleName();

    private GetAddressInfo mGetAddressInfo;

    @Override
    public void onReceiveLocation(BDLocation location) {
        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
        if (mGetAddressInfo != null) {
            mGetAddressInfo.getAddressInfo(location.getAddress());
        }
    }

    public void setGetAddressInfo(GetAddressInfo getAddressInfo) {
        mGetAddressInfo = getAddressInfo;
    }

    public interface GetAddressInfo {
        public void getAddressInfo(Address address);
    }
}
