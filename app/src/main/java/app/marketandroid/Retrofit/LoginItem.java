package app.marketandroid.Retrofit;

public class LoginItem {
    private String phone; //Login 유저 아이디(핸드폰 번호) POST
    private String password; //Login 유저 비밀번호 POST
    private int id;

    private String token; //Login 유저 토큰 정보 GET

    private String user_id; //Login 유저 핸드폰 번호(ID) 정보 GET
    private String name; //Login 유저 이름 정보 GET
    private String is_active; //Login 유저 승인 정보 GET
    private String is_admin; //Login 유저 어드민 정보 GET

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
