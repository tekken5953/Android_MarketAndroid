package app.marketandroid.Retrofit;

public class SignUpItem {
    private String phone; //회원가입 핸드폰 번호(ID) POST
    private String name; //회원가입 이름 POST
    private String password1; //회원가입 비밀번호 POST
    private String password2; //회원가입 비밀번호 확인 POST

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

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
