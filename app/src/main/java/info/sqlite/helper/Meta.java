package info.sqlite.helper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Cynthia.Khalil on 7/11/2017.
 */

public class Meta {
     String user;
     String eList;



    public void seteList(String eList) {
        this.eList = eList;
    }

    public String getUser() {

        return user;
    }
    public String setUser(String user)
    {
        return this.user = user;
    }
    public String geteList() {
        return eList;
    }

    public Meta deserializeData(String str)
    {
        Gson gson = new Gson();
        try {
            HashMap hashMapResponse = new Gson().fromJson(str, HashMap.class);
            Meta meta = new Meta();
            meta.setUser((String) hashMapResponse.get("user"));
            meta.seteList((String) hashMapResponse.get("lstEmployees"));
            return meta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
