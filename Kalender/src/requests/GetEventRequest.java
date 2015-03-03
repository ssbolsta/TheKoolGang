package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GetEventRequest implements Request {

    private int id = -1;
    private int user_member = -1;
    private int group_member = -1;
    private String[] date_range = new String[]{"",""};
    private int[] limit = new int[]{0,0};
    
    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray limit = new JSONArray();
        JSONArray date_range = new JSONArray();
        
        date_range.add(this.date_range[0]);
        date_range.add(this.date_range[1]);
        
        limit.add(this.limit[0]);
        limit.add(this.limit[1]);
        
        content.put("id", id);
        content.put("user_member", user_member);
        content.put("group_member", group_member);
        content.put("date_range", date_range);
        content.put("limit", limit);
        
        main.put("request", "event");
        main.put("type", "get");
        main.put("content", content);
        
        return main.toJSONString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_member() {
        return user_member;
    }

    public void setUser_member(int user_member) {
        this.user_member = user_member;
    }

    public int getGroup_member() {
        return group_member;
    }

    public void setGroup_member(int group_member) {
        this.group_member = group_member;
    }

    public String[] getDate_range() {
        return date_range;
    }

    public void setDate_range(String from, String to) {
        date_range[0] = from;
        date_range[1] = to;
    }

    public int[] getLimit() {
        return limit;
    }

    public void setLimit(int from, int to) {
        limit[0] = from;
        limit[1] = to;
    }
}