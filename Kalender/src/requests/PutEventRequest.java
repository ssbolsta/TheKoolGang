package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by boye on 03.03.15.
 */
public class PutEventRequest implements Request {

    private String name = "";
    private String desc = "";
    private int admin = -1;
    private int room = -1;
    private String[] time = new String[]{"",""};
    private String date = "";
    private int[] limit = new int[]{0,0};

    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray limit = new JSONArray();
        JSONArray time = new JSONArray();

        time.add(this.time[0]);
        time.add(this.time[1]);

        limit.add(this.limit[0]);
        limit.add(this.limit[1]);
        
        content.put("name", name);
        content.put("desc", desc);
        content.put("admin", admin);
        content.put("room", room);
        content.put("date", date);
        content.put("time", time);
        content.put("limit", limit);

        main.put("request", "event");
        main.put("type", "get");
        main.put("content", content);

        return main.toJSONString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String from, String to) {
        time[0] = from;
        time[1] = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int[] getLimit() {
        return limit;
    }

    public void setLimit(int from, int to) {
        limit[0] = from;
        limit[1] = to;
    }
}
