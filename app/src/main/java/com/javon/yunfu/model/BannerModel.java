package com.javon.yunfu.model;

import java.util.List;


public class BannerModel {
    public List<String> imgs;
    public List<String> tips;



    @Override
    public String toString() {
        return "BannerModel{" +
                "imgs=" + imgs +
                ", tips=" + tips +
                '}';
    }
}