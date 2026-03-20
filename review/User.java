public class User {
    private String UserName;
    private String UserAddress;

    public String getName() {
        return UserName;
    }

    public String getAddress() {
        return UserAddress;
    }

    public void setUser(String name, String address) {
        UserName = name;
        UserAddress = address;
    }
}