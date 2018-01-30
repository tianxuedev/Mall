package com.example.userlh.fruitmall2.db;

import android.widget.ImageView;

import org.litepal.crud.DataSupport;

/**
 * Created by userLH on 2018/1/29.
 */

public class Shop extends DataSupport {
        private int id;
        private String shopIntroduction;          //商店介绍
        private String shopName;                 //商店名字
        private int shopCode;               //商店编号
        public int getId(){
            return id;
        }
        public void setId(int id){
            this.id=id;
        }
        public String getShopName(){
            return shopName;
        }
        public void setShopName(String shopName){
            this.shopName=shopName;
        }
    public String getShopIntroduction(){
        return shopIntroduction;
    }
    public void setShopIntroduction(String shopIntroduction){
        this.shopIntroduction=shopIntroduction;
    }
        public int getShopCode(){
            return shopCode;
        }
        public void setShopCode(int shopCode){
            this.shopCode=shopCode;
        }
    }
