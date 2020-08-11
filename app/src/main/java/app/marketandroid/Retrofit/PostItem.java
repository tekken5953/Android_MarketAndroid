package app.marketandroid.Retrofit;

public class PostItem {
    private String hp_num; //Login 유저 아이디(핸드폰 번호)(보내기)
    private String user_name; //Login 유저 이름(보내기)
    private String user_pwd; //Login 유저 비밀번호(보내기)
    private String weight; //NB_AddFragment 버튼 클릭 시 신청 중량(보내기)
    private String products_id; //NB_AddFragment 버튼 클릭 시 품종 이름(보내기)
    private String user_id; //NB_AddFragment 버튼 클릭 시 유저 아이디(핸드폰 번호)(보내기)



    public String getUser_name() {
        return user_name;
    }

    public String getHp_num(){
        return hp_num;
    }

    public void setHp_num(String s){
        hp_num = s;
    }

    public void setUser_name(String s){
        user_name = s;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getProducts_id() {
        return products_id;
    }

    public void setProducts_id(String products_id) {
        this.products_id = products_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
