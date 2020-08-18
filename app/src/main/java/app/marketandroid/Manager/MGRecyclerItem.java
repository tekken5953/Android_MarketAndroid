package app.marketandroid.Manager;

public class MGRecyclerItem {
    private String user_name;
    private String productsStr;
    private String weightStr;
    private String countStr;
    private String total_priceStr;
    private String personal_priceStr;

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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setPersonal_priceStr(String personal_priceStr) {
        this.personal_priceStr = personal_priceStr;
    }

    public MGRecyclerItem( String user_name, String productsStr, String weightStr,
                          String countStr, String total_priceStr, String personal_priceStr) {
        this.user_name = user_name;
        this.productsStr = productsStr;
        this.weightStr = weightStr;
        this.countStr = countStr;
        this.total_priceStr = total_priceStr;
        this.personal_priceStr = personal_priceStr;
    }
}
