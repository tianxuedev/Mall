package com.example.userlh.fruitmall2.util;

import android.text.TextUtils;

import com.example.userlh.fruitmall2.db.DryCargo;
import com.example.userlh.fruitmall2.db.Fruit;
import com.example.userlh.fruitmall2.db.Meat;
import com.example.userlh.fruitmall2.db.Shop;
import com.example.userlh.fruitmall2.db.Vegetable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by userLH on 2018/1/29.
 */

public class Utility {
    //解析和处理服务器返回的商店列表信息
    public static boolean handleShopResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allShops=new JSONArray(response);
                for (int i=0;i<allShops.length();i++){
                    JSONObject shopObject=allShops.getJSONObject(i);
                    Shop shop=new Shop();
                    shop.setShopName(shopObject.getString("name"));
                    shop.setShopIntroduction(shopObject.getString("introduce"));
                    shop.setShopCode(shopObject.getInt("sClassify"));
                    shop.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析和处理服务器返回的蔬菜商店信息
    public static boolean handleVegetableResponse(String response,int shopId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allVegetables=new JSONArray(response);
                for(int i=0;i<allVegetables.length();i++){
                    JSONObject vegetableObject=allVegetables.getJSONObject(i);
                    Vegetable vegetable=new Vegetable();
                    vegetable.setCommodityName(vegetableObject.getString("name"));
                    vegetable.setShopId(shopId);
                    vegetable.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析和处理服务器返回的肉类商店信息
    public static boolean handleMeatResponse(String response,int shopId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allMeats=new JSONArray(response);
                for(int i=0;i<allMeats.length();i++){
                    JSONObject meatObject=allMeats.getJSONObject(i);
                    Meat meat=new Meat();
                    meat.setCommodityName(meatObject.getString("name"));
                    meat.setShopId(shopId);
                    meat.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析和处理服务器返回的水果商店信息
    public static boolean handleFruitResponse(String response,int shopId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allFruits=new JSONArray(response);
                for(int i=0;i<allFruits.length();i++){
                    JSONObject fruitObject=allFruits.getJSONObject(i);
                    Fruit fruit=new Fruit();
                    fruit.setCommodityName(fruitObject.getString("name"));
                    fruit.setShopId(shopId);
                    fruit.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析和处理服务器返回的干货商店信息
    public static boolean handleDryCargoResponse(String response,int shopId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allDryCargos=new JSONArray(response);
                for(int i=0;i<allDryCargos.length();i++){
                    JSONObject drycargoObject=allDryCargos.getJSONObject(i);
                    DryCargo dryCargo=new DryCargo();
                    dryCargo.setCommodityName(drycargoObject.getString("name"));
                    dryCargo.setShopId(shopId);
                    dryCargo.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
