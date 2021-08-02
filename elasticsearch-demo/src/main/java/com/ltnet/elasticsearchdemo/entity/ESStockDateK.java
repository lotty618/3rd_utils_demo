package com.ltnet.elasticsearchdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "db_stockdatek", shards = 2)
public class ESStockDateK implements Serializable {
    private static final long serialVersionUID = -1L;

    @Id
    private Integer id;
    private String code;
    private String date;
    private String open;
    private String high;
    private String close;
    private String low;
    private String volume;
    /**
     * 价格变动
     */
    private String priceChange;
    /**
     * 涨跌幅
     */
    private String pChange;
    private String ma5;
    private String ma10;
    private String ma20;
    private String ma30;
    private String ma60;
    private String ma120;
    private String vMa5;
    private String vMa10;
    private String vMa20;
    private String vMa30;
    private String vMa60;
    private String vMa120;
    private String turnover;
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getpChange() {
        return pChange;
    }

    public void setpChange(String pChange) {
        this.pChange = pChange;
    }

    public String getMa5() {
        return ma5;
    }

    public void setMa5(String ma5) {
        this.ma5 = ma5;
    }

    public String getMa10() {
        return ma10;
    }

    public void setMa10(String ma10) {
        this.ma10 = ma10;
    }

    public String getMa20() {
        return ma20;
    }

    public void setMa20(String ma20) {
        this.ma20 = ma20;
    }

    public String getMa30() {
        return ma30;
    }

    public void setMa30(String ma30) {
        this.ma30 = ma30;
    }

    public String getMa60() {
        return ma60;
    }

    public void setMa60(String ma60) {
        this.ma60 = ma60;
    }

    public String getMa120() {
        return ma120;
    }

    public void setMa120(String ma120) {
        this.ma120 = ma120;
    }

    public String getvMa5() {
        return vMa5;
    }

    public void setvMa5(String vMa5) {
        this.vMa5 = vMa5;
    }

    public String getvMa10() {
        return vMa10;
    }

    public void setvMa10(String vMa10) {
        this.vMa10 = vMa10;
    }

    public String getvMa20() {
        return vMa20;
    }

    public void setvMa20(String vMa20) {
        this.vMa20 = vMa20;
    }

    public String getvMa30() {
        return vMa30;
    }

    public void setvMa30(String vMa30) {
        this.vMa30 = vMa30;
    }

    public String getvMa60() {
        return vMa60;
    }

    public void setvMa60(String vMa60) {
        this.vMa60 = vMa60;
    }

    public String getvMa120() {
        return vMa120;
    }

    public void setvMa120(String vMa120) {
        this.vMa120 = vMa120;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "StockDateK{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", date='" + date + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", close='" + close + '\'' +
                ", low='" + low + '\'' +
                ", volume='" + volume + '\'' +
                ", priceChange='" + priceChange + '\'' +
                ", pChange='" + pChange + '\'' +
                ", ma5='" + ma5 + '\'' +
                ", ma10='" + ma10 + '\'' +
                ", ma20='" + ma20 + '\'' +
                ", ma30='" + ma30 + '\'' +
                ", ma60='" + ma60 + '\'' +
                ", ma120='" + ma120 + '\'' +
                ", vMa5='" + vMa5 + '\'' +
                ", vMa10='" + vMa10 + '\'' +
                ", vMa20='" + vMa20 + '\'' +
                ", vMa30='" + vMa30 + '\'' +
                ", vMa60='" + vMa60 + '\'' +
                ", vMa120='" + vMa120 + '\'' +
                ", turnover='" + turnover + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
