package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by boye on 03.03.15.
 */
public class PutGroupRequest {

    private String name = "";
    private int admin = -1;

    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();

        content.put("name", name);
        content.put("admin", admin);

        main.put("request", "group");
        main.put("type", "put");
        main.put("content", content);

        return main.toJSONString();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}
