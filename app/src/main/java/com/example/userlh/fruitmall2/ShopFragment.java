package com.example.userlh.fruitmall2;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userlh.fruitmall2.db.DryCargo;
import com.example.userlh.fruitmall2.db.Fruit;
import com.example.userlh.fruitmall2.db.Meat;
import com.example.userlh.fruitmall2.db.Shop;
import com.example.userlh.fruitmall2.db.Vegetable;
import com.example.userlh.fruitmall2.util.HttpUtil;
import com.example.userlh.fruitmall2.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by userLH on 2018/1/29.
 */

public class ShopFragment extends Fragment {
    public static final int LEVEL_SHOP = 0;
    public static final int LEVEL_VEGETABLE = 1;
    public static final int LEVEL_MEAT = 2;
    public static final int LEVEL_FRUIT = 3;
    public static final int LEVEL_DRYCARGO = 4;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private TextView introductionText;
    private Button backButton;
    private ListView listView;
    private List<Shop> shopList;
    private List<Vegetable> VegetableList;
    private List<Meat>MeatList;
    private List<Fruit>FruitList;
    private List<DryCargo>DryCargoList;
    //private ShopAdapter adapter;
    private ArrayAdapter<String> adapter;
    private Shop selectedShop;
    private List<String>dataList=new ArrayList<>();
    private int currentLevel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose, container, false);
        titleText=(TextView)view.findViewById(R.id.title_text);
        backButton=(Button)view.findViewById(R.id.back_button);
        introductionText=(TextView)view.findViewById(R.id.introduction);
        listView = (ListView) view.findViewById(R.id.list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(currentLevel==LEVEL_SHOP){
                    selectedShop=shopList.get(position);
                    int a=selectedShop.getShopCode();             //获取选中的商店
                    if(a==LEVEL_VEGETABLE){
                        queryVegetables();
                    }if(a==LEVEL_FRUIT) {
                        queryFruits();
                    }if(a==LEVEL_MEAT){
                        queryMeats();
                    }if(a==LEVEL_DRYCARGO){
                        queryDryCargos();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLevel==LEVEL_VEGETABLE||currentLevel==LEVEL_MEAT||currentLevel==LEVEL_FRUIT
                        ||currentLevel==LEVEL_DRYCARGO){
                    queryShops();
                }
            }
        });
        queryShops();
    }

//    class ShopAdapter extends Adapter {
//        private List<Shop> mShopList;
//        class ViewHolder extends RecyclerView.ViewHolder {
//            View shopView;
//            ImageView shopImage;
//            TextView shopName;
//            TextView shopIntroduction;
//
//            public ViewHolder(View view) {
//                super(view);
//                shopView = view;
//                shopImage = (ImageView) view.findViewById(R.id.image);
//                shopName = (TextView) view.findViewById(R.id.name);
//                shopIntroduction=(TextView) view.findViewById(R.id.introduction);
//            }
//        }
//
//        public ShopAdapter(List<Shop> shopList) {
//            mShopList = shopList;
//        }
//
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
//            final ViewHolder holder = new ViewHolder(view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position=holder.getAdapterPosition();
//                    if(currentLevel==LEVEL_SHOP){
//                        selectedShop=shopList.get(position);
//                        int a=selectedShop.getShopCode();             //获取选中的商店
//                        if(a==LEVEL_VEGETABLE){
//                            queryVegetables();
//                        }if(a==LEVEL_FRUIT) {
//                        queryFruits();
//                        }if(a==LEVEL_MEAT){
//                            queryMeats();
//                        }if(a==LEVEL_DRYCARGO){
//                            queryDryCargos();
//                        }
//                        }
//                }
//            });
//            backButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(currentLevel==LEVEL_VEGETABLE||currentLevel==LEVEL_MEAT||currentLevel==LEVEL_FRUIT
//                            ||currentLevel==LEVEL_DRYCARGO){
//                        queryShops();
//                    }
//                }
//            });
//            queryShops();
//            return holder;
//        }

//        public int getItemCount() {
//            return dataList.size();
//        }
//    }
    //查询
    private void queryShops(){
        titleText.setText("生鲜商城");
        backButton.setVisibility(View.GONE);
        shopList= DataSupport.findAll(Shop.class);
        if(shopList.size()>0){
            dataList.clear();
            for(Shop shop:shopList){
                dataList.add(shop.getShopName());
                dataList.add(shop.getShopIntroduction());
            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_SHOP;
        }else{
            String address="http://120.78.87.169:8080/sxsc/api/getShopList";
            queryFromServer(address,"shop");
        }
    }
    private void queryVegetables(){
        titleText.setText(selectedShop.getShopName());
        backButton.setVisibility(View.VISIBLE);
        VegetableList=DataSupport.where("shopid=?",String.valueOf(selectedShop.getId()))
                .find(Vegetable.class);
        if(VegetableList.size()>0){
            dataList.clear();
            for(Vegetable vegetable:VegetableList){
                dataList.add(vegetable.getCommodityName());
            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_VEGETABLE;
        }else{
            int shopCode=selectedShop.getShopCode();
            String address="http://120.78.87.169:8080/sxsc/api/getAllGoodsOfShop?SID="+shopCode;
            queryFromServer(address,"vegetable");
        }
    }
    private void queryMeats(){
        titleText.setText(selectedShop.getShopName());
        backButton.setVisibility(View.VISIBLE);
        MeatList=DataSupport.where("shopid=?",String.valueOf(selectedShop.getId()))
                .find(Meat.class);
        if(MeatList.size()>0){
            dataList.clear();
            for(Meat meat:MeatList){
                dataList.add(meat.getCommodityName());
            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_MEAT;
        }else{
            int shopCode=selectedShop.getShopCode();
            String address="http://120.78.87.169:8080/sxsc/api/getAllGoodsOfShop?SID="+shopCode;
            queryFromServer(address,"meat");
        }
    }
    private void queryFruits(){
        titleText.setText(selectedShop.getShopName());
        backButton.setVisibility(View.VISIBLE);
        FruitList=DataSupport.where("shopid=?",String.valueOf(selectedShop.getId()))
                .find(Fruit.class);
        if(FruitList.size()>0){
            dataList.clear();
            for(Fruit fruit:FruitList){
                dataList.add(fruit.getCommodityName());
            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_FRUIT;
        }else{
            int shopCode=selectedShop.getShopCode();
            String address="http://120.78.87.169:8080/sxsc/api/getAllGoodsOfShop?SID="+shopCode;
            queryFromServer(address,"fruit");
        }
    }
    private void queryDryCargos(){
        titleText.setText(selectedShop.getShopName());
        backButton.setVisibility(View.VISIBLE);
        DryCargoList=DataSupport.where("shopid=?",String.valueOf(selectedShop.getId()))
                .find(DryCargo.class);
        if(DryCargoList.size()>0){
            dataList.clear();
            for(DryCargo dryCargo:DryCargoList){
                dataList.add(dryCargo.getCommodityName());
            }
            adapter.notifyDataSetChanged();
            currentLevel=LEVEL_DRYCARGO;
        }else{
            int shopCode=selectedShop.getShopCode();
            String address="http://120.78.87.169:8080/sxsc/api/getAllGoodsOfShop?SID="+shopCode;
            queryFromServer(address,"drycargo");
        }
    }
    private void queryFromServer(String address,final String type){
        showProgressDialog();
        Log.d("MainActivity", "queryFromServer: ");
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("MainActivity", "onResponse1: ");
                String respponseText=response.body().string();
                boolean result=false;
                Log.d("MainActivity", "onResponse2: ");
                if("shop".equals(type)){
                    Log.d("MainActivity", "onResponse3: ");
                    result= Utility.handleShopResponse(respponseText);
                }else if("vegetable".equals(type)){
                    result=Utility.handleVegetableResponse(respponseText,selectedShop.getId());
                }else if("meat".equals(type)){
                    result=Utility.handleMeatResponse(respponseText,selectedShop.getId());
                }else if("fruit".equals(type)){
                    result=Utility.handleFruitResponse(respponseText,selectedShop.getId());
                }else if("drycargo".equals(type)){
                    result=Utility.handleDryCargoResponse(respponseText,selectedShop.getId());
                }
                if(result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if("shop".equals(type)){
                                queryShops();
                            }else if("vegetable".equals(type)){
                                queryVegetables();
                            }else if("meat".equals(type)){
                                queryMeats();
                            }else if("fruit".equals(type)){
                                queryFruits();
                            }else if("drycargo".equals(type)){
                                queryDryCargos();
                            }
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(),"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog!=null){
            progressDialog.dismiss();
        }
    }
}