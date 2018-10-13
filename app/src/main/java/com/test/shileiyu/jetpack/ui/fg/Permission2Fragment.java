package com.test.shileiyu.jetpack.ui.fg;


import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.permission.AskPermission;
import com.test.shileiyu.jetpack.common.permission.IRationale;
import com.test.shileiyu.jetpack.common.permission.PermissionAction;
import com.test.shileiyu.jetpack.common.permission.RequestExecutor;
import com.test.shileiyu.jetpack.common.permission.action.ExplainPermissionRationale;
import com.test.shileiyu.jetpack.common.permission.action.GuideUser2SettingAction;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Permission2Fragment extends Fragment {


    private TextView mTv;

    public Permission2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_permission2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTv = (TextView) view.findViewById(R.id.btn_fg_tv);
        view.findViewById(R.id.btn_fg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskPermission.with(Permission2Fragment.this).createRequest()
                        .permission(Manifest.permission.READ_CALENDAR,
                                Manifest.permission.WRITE_CALENDAR,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .showRationale(new ExplainPermissionRationale())
                        .onDenied(new GuideUser2SettingAction(getActivity()))
                        .onGranted(new PermissionAction<List<String>>() {
                            @Override
                            public void onCall(List<String> data) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("申请成功啦：");
                                for (String p : data) {
                                    sb.append(p).append("\n");
                                }
                                mTv.setText(sb.toString());
                            }
                        }).start();
            }
        });
    }
}
