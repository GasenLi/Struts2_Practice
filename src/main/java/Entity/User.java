package Entity;

public class User {
    private String userID;
    private String username;
    private String password;

    private static final String[][] data = new String[][]{
            {"0001","aa","111111"},
            {"0002","bb","222222"},
            {"0003","cc","333333"},
            {"0004","dd","444444"},
            {"0005","ee","555555"}
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static String[][] getData() {
        return data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
