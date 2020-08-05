package app.marketandroid;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable iconDrawable;
    private String nameStr;
    private String detailStr;

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }
    public void setNameStr(String nameStr) {
        this.nameStr = nameStr;
    }
    public void setDetailStr(String detailStr) {
        this.detailStr = detailStr;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }
    public String getNameStr() {
        return nameStr;
    }
    public String getDetailStr() {
        return detailStr;
    }
}
