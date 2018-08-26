package com.zhangmiao.notepad.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangmiao.notepad.R;
import com.zhangmiao.notepad.adapter.RecordYearAdapter;
import com.zhangmiao.notepad.bean.DataBean;
import com.zhangmiao.notepad.bean.RecordDataBean;
import com.zhangmiao.notepad.db.RecordDao;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class SetFragment extends Fragment {

    private static final String TAG = SetFragment.class.getSimpleName();

    @BindView(R.id.fragment_set_person_head_portrait_iv)
    ImageView iv_personHeadPortrait;

    @BindView(R.id.fragment_set_sign_in_tv)
    TextView tv_singIn;

    @BindView(R.id.fragment_set_note_tv)
    TextView tv_note;

    @BindView(R.id.fragment_set_mood_tv)
    TextView tv_mood;

    @BindView(R.id.fragment_set_wastebasket_tv)
    TextView tv_wastebasket;

    @BindView(R.id.fragment_set_account_information_tv)
    TextView tv_accountInformation;

    @BindView(R.id.fragment_set_private_information_tv)
    TextView tv_privateInformation;

    @BindView(R.id.fragment_set_course_tv)
    TextView tv_course;

    @BindView(R.id.fragment_set_synchronous_tv)
    TextView tv_synchronous;

    @BindView(R.id.fragment_set_clean_cache_tv)
    TextView tv_cleanCache;

    @BindView(R.id.fragment_set_version_information_tv)
    TextView tv_versionInformation;

    @BindView(R.id.fragment_set_legal_provisions_tv)
    TextView tv_legalProvision;

    Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        List<RecordDataBean> recordDataBeanList = RecordDao.queryAll();
        int wastebasketCount = 0;
        int noteCount = 0;
        int moodCount = 0;
        if (recordDataBeanList != null) {
            for (RecordDataBean bean : recordDataBeanList) {
                if (bean.getIsWastebasket()) {
                    wastebasketCount++;
                } else {
                    int type = bean.getType();
                    switch (type) {
                        case DataBean.DATA_TYPE_MOOD:
                            moodCount++;
                            break;
                        case DataBean.DATA_TYPE_NOTE:
                            noteCount++;
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        tv_mood.setText(moodCount+"");
        tv_note.setText(noteCount+"");
        tv_wastebasket.setText(wastebasketCount+"");

    }

    @OnClick(R.id.fragment_set_person_head_portrait_iv)
    void clickHeadPortrait() {
        Toast.makeText(mContext, "账户头像", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_sign_in_tv)
    void singIn() {
        Toast.makeText(mContext, "登录注册", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_account_information_tv)
    void showAccountInformation() {
        Toast.makeText(mContext, "账户信息", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_private_information_tv)
    void showPrivateInformation() {
        Toast.makeText(mContext, "私密信息", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_course_tv)
    void showCourse() {
        Toast.makeText(mContext, "教程", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_synchronous_tv)
    void synchronous() {
        Toast.makeText(mContext, "同步", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_clean_cache_tv)
    void cleanCache() {
        Toast.makeText(mContext, "清理缓存", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_version_information_tv)
    void versionInformation() {
        Toast.makeText(mContext, "版本信息", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fragment_set_legal_provisions_tv)
    void legalInformation() {
        Toast.makeText(mContext, "法律条款", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
