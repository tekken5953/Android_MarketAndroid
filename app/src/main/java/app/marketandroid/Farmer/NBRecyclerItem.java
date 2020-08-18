package app.marketandroid.Farmer;

import android.graphics.drawable.Drawable;

public class NBRecyclerItem {
    private Drawable iconDrawable;
    private String timeStr;
    private String productsStr;
    private String weightStr;
    private String countStr;
    private String total_priceStr;
    private String personal_priceStr;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public String getProductsStr() {
        return productsStr;
    }

    public void setProductsStr(String productsStr) {
        this.productsStr = productsStr;
    }

    public String getWeightStr() {
        return weightStr;
    }

    public void setWeightStr(String weightStr) {
        this.weightStr = weightStr;
    }

    public String getCountStr() {
        return countStr;
    }

    public void setCountStr(String countStr) {
        this.countStr = countStr;
    }

    public String getTotal_priceStr() {
        return total_priceStr;
    }

    public void setTotal_priceStr(String total_priceStr) {
        this.total_priceStr = total_priceStr;
    }

    public String getPersonal_priceStr() {
        return personal_priceStr;
    }

    public void setPersonal_priceStr(String personal_priceStr) {
        this.personal_priceStr = personal_priceStr;
    }

    public NBRecyclerItem(Drawable iconDrawable, String timeStr,  String productsStr, String weightStr, String countStr, String total_priceStr, String personal_priceStr) {

        this.iconDrawable = iconDrawable;
        this.timeStr = timeStr;
        this.productsStr = productsStr;
        this.weightStr = weightStr;
        this.countStr = countStr;
        this.total_priceStr = total_priceStr;
        this.personal_priceStr = personal_priceStr;
    }
}
