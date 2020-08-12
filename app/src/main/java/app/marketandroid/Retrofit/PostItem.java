package app.marketandroid.Retrofit;

public class PostItem {


    private String weight; //NB_AddFragment 버튼 클릭 시 신청 중량(보내기)
    private String products_id; //NB_AddFragment 버튼 클릭 시 품종 이름(보내기)
     //NB_AddFragment 버튼 클릭 시 유저 아이디(핸드폰 번호)(보내기)

    public String getWeight() { return weight;}

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }



}
