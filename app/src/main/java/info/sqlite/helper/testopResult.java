package info.sqlite.helper;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Created by Cynthia.Khalil on 7/11/2017.
 */

public class testopResult {


     String message;

     String meta;

     Boolean result;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {

        return message;
    }

    public String getMeta() {
        return meta;
    }

    public Boolean getResult() {
        return result;
    }

    public testopResult deserializeData(String str)
    {
        Gson gson = new Gson();
        try {
            HashMap hashMapResponse = new Gson().fromJson(str, HashMap.class);
            testopResult response = new testopResult();
            response.setMessage((String) hashMapResponse.get("message"));
            response.setMeta((String) hashMapResponse.get("meta"));
            response.setResult((Boolean) hashMapResponse.get("result"));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


