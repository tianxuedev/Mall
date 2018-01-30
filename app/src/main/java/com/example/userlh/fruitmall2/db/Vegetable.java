package com.example.userlh.fruitmall2.db;

/**
 * Created by userLH on 2018/1/29.
 */

import android.widget.ImageView;

import org.litepal.crud.DataSupport;

/**
 * Created by userLH on 2018/1/29.
 */

public class Vegetable extends DataSupport {
    private int id;
    private String commodityName;                        //商品名字
    private int shopId;                          //当前商品所属商店的id值
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getCommodityName(){
        return commodityName;
    }
    public void setCommodityName(String commodityName){
        this.commodityName=commodityName;
    }
    public int getShopId(){
        return shopId;
    }
    public void setShopId(int shopId){
        this.shopId=shopId;
    }
}