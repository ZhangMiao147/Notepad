package com.zhangmiao.notepad.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangmiao.notepad.R;

/**
 * Author: zhangmiao
 * Date: 2017/10/8
 */
public class MyNoteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_note, container, false);
    }
}
