package com.test.shileiyu.jetpack.common.bean.filter;

import com.test.shileiyu.jetpack.common.bean.filter.sub.Distance;
import com.test.shileiyu.jetpack.common.bean.filter.sub.District;
import com.test.shileiyu.jetpack.common.bean.filter.sub.SubWay;
import com.test.shileiyu.jetpack.common.bean.filter.sub.SubWayStation;
import com.test.shileiyu.jetpack.common.bean.filter.sub.TradeArea;
import com.test.shileiyu.jetpack.common.bean.filter.sub.Type;
import com.test.shileiyu.jetpack.common.util.Util;

import java.util.ArrayList;
import java.util.List;

public class OriginData extends Composite {

    private List<Composite> selectNote = new ArrayList<>();


    public static void main(String[] a) {
        OriginData data = new OriginData();
        Type type = new Type();
        type.name = "距离位置";
        Distance child = new Distance();
        type.addChild(child);
        data.addChild(type);
    }

    public static OriginData get() {
        OriginData originData = new OriginData();
        Type thradA = new Type("商圈");
        thradA.addChild(new TradeArea("不限"));
        thradA.addChild(new TradeArea("后海"));
        thradA.addChild(new TradeArea("天安门"));
        thradA.addChild(new TradeArea("西单"));
        originData.addChild(thradA);
        Type district = new Type("行政区");
        district.addChild(new District("不限"));
        district.addChild(new District("朝阳"));
        district.addChild(new District("东城"));
        district.addChild(new District("西城"));
        originData.addChild(district);

        Type subWay = new Type("地铁线路");
        SubWay way1 = new SubWay("8号线");
        way1.addChild(new SubWayStation("不限"));
        way1.addChild(new SubWayStation("南锣古巷"));
        way1.addChild(new SubWayStation("什刹海"));
        way1.addChild(new SubWayStation("鼓楼大街"));
        way1.addChild(new SubWayStation("安德里北街"));
        way1.addChild(new SubWayStation("安华桥"));
        way1.addChild(new SubWayStation("北土城"));
        way1.addChild(new SubWayStation("奥体中心"));
        way1.addChild(new SubWayStation("奥林匹克"));
        way1.addChild(new SubWayStation("森林公园南门"));
        way1.addChild(new SubWayStation("林萃桥"));
        way1.addChild(new SubWayStation("西小口"));
        way1.addChild(new SubWayStation("育新"));
        way1.addChild(new SubWayStation("霍营"));
        way1.addChild(new SubWayStation("回龙观"));
        way1.addChild(new SubWayStation("平西王府"));
        subWay.addChild(way1);
        SubWay way2 = new SubWay("2号线");
        way2.addChild(new SubWayStation("不限"));
        way2.addChild(new SubWayStation("积水潭"));
        way2.addChild(new SubWayStation("鼓楼大街"));
        way2.addChild(new SubWayStation("安定门"));
        subWay.addChild(way2);
        originData.addChild(subWay);
        return originData;
    }
}
