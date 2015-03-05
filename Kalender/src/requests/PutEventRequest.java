package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PutEventRequest implements Request {

    private String name = "";
    private String desc = "";
    private int in_room = -1;
    private int admin = -1;
    private String[] time = new String[]{"",""};
    private String date = "";

    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray time = new JSONArray();

        time.add(this.time[0]);
        time.add(this.time[1]);
        
        content.put("name", name);
        content.put("desc", desc);
        content.put("in_room", in_room);
        content.put("admin", admin);
        content.put("date", date);
        content.put("time", time);

        main.put("request", "event");
        main.put("type", "put");
        main.put("content", content);

        return main.toJSONString();
    }

    public int getIn_room() {
        return in_room;
    }

    public void setIn_room(int in_room) {
        this.in_room = in_room;
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
}
