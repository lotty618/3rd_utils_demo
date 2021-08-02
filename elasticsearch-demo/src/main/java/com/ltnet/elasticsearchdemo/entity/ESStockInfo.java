package com.ltnet.elasticsearchdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "db_stock", shards = 2)
public class ESStockInfo {
    private static final long serialVersionUID = -1L;

    @Id
    private Integer id;
    private String code;
    private String name;
    private String fullname;
    private String enname;
    private String industry;
    private String area;
    private String pe;
    private String outstanding;
    private String totals;
    private String totalAssets;
    private String liquidAssets;
    private String fixedAssets;
    private String reserved;
    private String reservedPerShare;
    private String esp;
    private String bvps;
    private String pb;
    private String timeToMarket;
    private String undp;
    private String perundp;
    private String rev;
    private String profit;
    private String gpr;
    private String npr;
    private String holders;
    private String tags;
    private String addtime;
    private String industry_id;
    private String concept_ids;
    private String area_id;
    private String price;
    private String ts_code;
    private String status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPe() {
        return pe;
    }

    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }

    public String getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(String totalAssets) {
        this.totalAssets = totalAssets;
    }

    public String getLiquidAssets() {
        return liquidAssets;
    }

    public void setLiquidAssets(String liquidAssets) {
        this.liquidAssets = liquidAssets;
    }

    public String getFixedAssets() {
        return fixedAssets;
    }

    public void setFixedAssets(String fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getReservedPerShare() {
        return reservedPerShare;
    }

    public void setReservedPerShare(String reservedPerShare) {
        this.reservedPerShare = reservedPerShare;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }

    public String getBvps() {
        return bvps;
    }

    public void setBvps(String bvps) {
        this.bvps = bvps;
    }

    public String getPb() {
        return pb;
    }

    public void setPb(String pb) {
        this.pb = pb;
    }

    public String getTimeToMarket() {
        return timeToMarket;
    }

    public void setTimeToMarket(String timeToMarket) {
        this.timeToMarket = timeToMarket;
    }

    public String getUndp() {
        return undp;
    }

    public void setUndp(String undp) {
        this.undp = undp;
    }

    public String getPerundp() {
        return perundp;
    }

    public void setPerundp(String perundp) {
        this.perundp = perundp;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getGpr() {
        return gpr;
    }

    public void setGpr(String gpr) {
        this.gpr = gpr;
    }

    public String getNpr() {
        return npr;
    }

    public void setNpr(String npr) {
        this.npr = npr;
    }

    public String getHolders() {
        return holders;
    }

    public void setHolders(String holders) {
        this.holders = holders;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(String industry_id) {
        this.industry_id = industry_id;
    }

    public String getConcept_ids() {
        return concept_ids;
    }

    public void setConcept_ids(String concept_ids) {
        this.concept_ids = concept_ids;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTs_code() {
        return ts_code;
    }

    public void setTs_code(String ts_code) {
        this.ts_code = ts_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
