package info.sqlite.helper;

import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by Cynthia.Khalil on 7/7/2017.
 */

public class User {

    private int id;
    private String username;
    private String password;

    public User() {
    }

    public User(int id)
    {
        this.id = id;
    }

    public User(int id, String username, String password)
    {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null)
            return false;
        return password != null ? password.equals(user.password) : user.password == null;

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User deserializeData(String str)
    {
        Gson gson = new Gson();
        try {
            HashMap hashMapResponse = new Gson().fromJson(str, HashMap.class);
            User usr = new User();
            usr.setId(Integer.parseInt((String) hashMapResponse.get("User_Code")));
            usr.setUsername((String) hashMapResponse.get("User_Description"));
            usr.setPassword((String) hashMapResponse.get("User_Password"));
            return usr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
