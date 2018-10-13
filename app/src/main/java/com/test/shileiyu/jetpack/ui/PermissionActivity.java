package com.test.shileiyu.jetpack.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.test.shileiyu.jetpack.R;
import com.test.shileiyu.jetpack.common.permission.PermissionAction;
import com.test.shileiyu.jetpack.common.permission.AskPermission;
import com.test.shileiyu.jetpack.common.permission.IRationale;
import com.test.shileiyu.jetpack.common.permission.RequestExecutor;
import com.test.shileiyu.jetpack.common.permission.RequestSource;
import com.test.shileiyu.jetpack.common.permission.action.ExplainPermissionRationale;
import com.test.shileiyu.jetpack.common.permission.action.GuideUser2SettingAction;
import com.test.shileiyu.jetpack.common.permission.dialog.PermissionDialog;
import com.test.shileiyu.jetpack.common.util.Util;
import com.test.shileiyu.jetpack.ui.fg.Permission2Fragment;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Rationale;


import java.util.List;

public class PermissionActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1000) {
            Util.showToast("居然有onActivityResult");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mTv = (TextView) findViewById(R.id.tv_aaa);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndPermission.with(this).runtime().permission(Manifest.permission.CALL_PHONE,
                    Manifest.permission.SEND_SMS).rationale(new Rationale<List<String>>() {
                @Override
                public void showRationale(Context context, List<String> data, final com.yanzhenjie.permission.RequestExecutor executor) {
                    PermissionDialog.getExplainPermissionDialog(PermissionActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.execute();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executor.cancel();
                        }
                    }).show();
                }
            }).onDenied(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {

                    Util.showToast("AndPermission 被拒绝");
                    PermissionDialog.getOpenSettingDialog(PermissionActivity.this, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", PermissionActivity.this.getPackageName(), null));
                            PermissionActivity.this.startActivityForResult(intent, 1000);
                        }
                    }).show();
                }
            }).onGranted(new Action<List<String>>() {
                @Override
                public void onAction(List<String> data) {
                    Util.showToast("AndPermission 申请成功");
                }
            }).start();
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            AskPermission.with(this).createRequest()
//                    .permission(Manifest.permission.CALL_PHONE,
//                            Manifest.permission_group.CONTACTS)
//                    .showRationale(new ExplainPermissionRationale())
//                    .onDenied(new GuideUser2SettingAction(this))
//                    .onGranted(new PermissionAction<List<String>>() {
//                        @Override
//                        public void onCall(List<String> data) {
//                            StringBuilder sb = new StringBuilder();
//                            sb.append("申请成功啦：");
//                            for (String p : data) {
//                                sb.append(p).append("\n");
//                            }
//                            Util.showToast(sb.toString());
//                        }
//                    }).start();
//        }

        findViewById(R.id.btn_aaa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionByMe();
            }
        });
        findViewById(R.id.btn_bbb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionByAndPermission();
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_fg, new Permission2Fragment()).commitAllowingStateLoss();
    }

    private void checkPermissionByAndPermission() {
        AndPermission.with(this).runtime().permission(Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.CAMERA
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        mTv.setText(format(data, "申请成功的权限"));
                        Util.showToast("全部申请成功啦");
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                mTv.setText(format(data, "申请失败的权限："));
                showDialogOpenPermissionManual();
            }
        }).rationale(new Rationale<List<String>>() {
            @Override
            public void showRationale(Context context, List<String> data, final com.yanzhenjie.permission.RequestExecutor executor) {
                new AlertDialog.Builder(PermissionActivity.this)
                        .setTitle("解释为什么要权限")
                        .setMessage("AndPermission " + Util.toJson(data))
                        .setPositiveButton("继续申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                executor.execute();
                            }
                        })
                        .setNegativeButton("放弃申请", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                executor.cancel();
                            }
                        })
                        .create().show();
            }
        }).start();
    }

    private void checkPermissionByMe() {
        AskPermission.with(PermissionActivity.this)
                .createRequest()
                .permission(Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.CAMERA
                        , Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION)
                .showRationale(new IRationale<List<String>>() {
                    @Override
                    public void showRationale(List<String> data, final RequestExecutor executor, RequestSource source) {
                        String str = "需要告知用户请求权限的原因：";
                        StringBuilder format = format(data, str);
                        final String text = format.toString();
                        mTv.setText(text);
                        showDialog4ReasonWhyAppNeedPermission(executor, text);
                    }
                })
                .onGranted(new PermissionAction<List<String>>() {
                    @Override
                    public void onCall(List<String> data) {
                        mTv.setText(format(data, "申请成功的权限"));
                        Util.showToast("全部申请成功啦");
                    }
                }).onDenied(new PermissionAction<List<String>>() {
            @Override
            public void onCall(final List<String> data) {
                mTv.setText(format(data, "申请失败的权限："));
                showDialogOpenPermissionManual();
            }
        }).start();
    }

    private void showDialog4ReasonWhyAppNeedPermission(final RequestExecutor executor, String text) {
        new AlertDialog.Builder(PermissionActivity.this)
                .setTitle("解释为什么要权限")
                .setMessage(text)
                .setPositiveButton("继续申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executor.execute();
                    }
                })
                .setNegativeButton("放弃申请", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        executor.cancel();
                    }
                })
                .create().show();
    }

    private void showDialogOpenPermissionManual() {
        new AlertDialog.Builder(PermissionActivity.this).setMessage("权限申请失败，您将无法使用xxx功能，" +
                "您可以跳转到设置手动开启权限").setTitle("提醒手动开启")
                .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getApplicationContext().getPackageName(), null));
                        startActivity(intent);
                    }
                }).setNegativeButton("算了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    private StringBuilder format(List<String> data, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str).append("\n");
        for (String p : data) {
            sb.append(p).append("\n");
        }
        return sb;
    }
}
