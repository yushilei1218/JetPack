package com.test.shileiyu.jetpack.common.permission;

import android.Manifest;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by chuangjie.pcj on 2018/10/30.
 * <p>
 * 权限组
 */
public class PermissionsGroup {
    public static final String[] CALENDAR;
    public static final String[] CAMERA;
    public static final String[] CONTACTS;
    public static final String[] LOCATION;
    public static final String[] MICROPHONE;
    public static final String[] PHONE;
    public static final String[] SENSORS;
    public static final String[] SMS;
    public static final String[] STORAGE;

    public static final Map<String, String> PERMISSION_NAME_MAP = new HashMap<>();

    static {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            CALENDAR = new String[]{};
            CAMERA = new String[]{};
            CONTACTS = new String[]{};
            LOCATION = new String[]{};
            MICROPHONE = new String[]{};
            PHONE = new String[]{};
            SENSORS = new String[]{};
            SMS = new String[]{};
            STORAGE = new String[]{};
        } else {
            CALENDAR = new String[]{
                    Manifest.permission.READ_CALENDAR,
                    Manifest.permission.WRITE_CALENDAR};

            CAMERA = new String[]{
                    Manifest.permission.CAMERA};

            CONTACTS = new String[]{
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.GET_ACCOUNTS};

            LOCATION = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION};

            MICROPHONE = new String[]{
                    Manifest.permission.RECORD_AUDIO};

            PHONE = new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CALL_LOG};

            SENSORS = new String[]{
                    Manifest.permission.BODY_SENSORS};

            SMS = new String[]{
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.RECEIVE_WAP_PUSH,
                    Manifest.permission.RECEIVE_MMS};

            STORAGE = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};

            addMatch(PERMISSION_NAME_MAP, CAMERA, "相机");
            addMatch(PERMISSION_NAME_MAP, STORAGE, "存储");
            addMatch(PERMISSION_NAME_MAP, CALENDAR, "日历");
            addMatch(PERMISSION_NAME_MAP, PHONE, "电话");
            addMatch(PERMISSION_NAME_MAP, LOCATION, "位置");
            addMatch(PERMISSION_NAME_MAP, CONTACTS, "通讯录");
            addMatch(PERMISSION_NAME_MAP, SENSORS, "传感器");
            addMatch(PERMISSION_NAME_MAP, MICROPHONE, "麦克风");
            addMatch(PERMISSION_NAME_MAP, SMS, "短信");
        }
    }

    private static void addMatch(Map<String, String> holder, String[] permissions, String name) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        for (String p : permissions) {
            holder.put(p, name);
        }
    }

    /**
     * 提供权限组 组合申请，当需要申请几个权限组时可以使用concat对所申请的权限组进行拼接
     * <p>
     * eg:String [] gs = PermissionsGroup.concat(PermissionsGroup.STORAGE,PermissionsGroup.LOCATION);
     * <p>
     * 这样就可以使用AskPermission一次性申请多个权限组
     *
     * @param groups 权限组
     * @return 权限数组
     */
    public static String[] concat(String[]... groups) {
        ArrayList<String> p = new ArrayList<>();
        if (groups != null && groups.length > 0) {
            for (String[] g : groups) {
                p.addAll(Arrays.asList(g));
            }
        }
        return p.toArray(new String[p.size()]);
    }

    private static List<String> sNecessary;

    /**
     * 获取App必要的权限
     *
     * @return 必要权限集合
     */
    public static List<String> getNecessaryPermissions() {
        if (sNecessary == null) {
            sNecessary = new ArrayList<>();
            sNecessary.addAll(Arrays.asList(concat(PHONE, STORAGE)));
        }
        return sNecessary;
    }

    /**
     * 给定权限集合，返回这个权限集合所包含的 权限中文名称
     *
     * @param permissions 权限集合
     * @return 非空数组，如果没有找到会返回一个带空字符串的数组
     */
    @NonNull
    public static String[] getPermissionNames(List<String> permissions) {
        if (permissions != null && permissions.size() > 0) {
            HashSet<String> set = new HashSet<>();
            for (String p : permissions) {
                String name = PERMISSION_NAME_MAP.get(p);
                if (!TextUtils.isEmpty(name)) {
                    set.add(name);
                }
            }
            if (set.size() > 0) {
                return set.toArray(new String[set.size()]);
            }
        }
        return new String[]{" "};
    }
}