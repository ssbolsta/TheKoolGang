package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GetRoomRequest implements Request {

    private int id = -1;
    private String name = "";
    private int room_of = -1;
    private int capacity = -1;
    private String date = "";
    private String[] time = new String[]{"",""};
    private int[] limit = new int[]{0,0};
    
    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONObject available = new JSONObject();
        JSONArray time = new JSONArray();
        JSONArray limit = new JSONArray();

        limit.add(this.limit[0]);
        limit.add(this.limit[1]);
        
        time.add(this.time[0]);
        time.add(this.time[1]);
        
        available.put("date", date);
        available.put("time", time);
        
        content.put("id", id);
        content.put("name", name);
        content.put("room_of", room_of);
        content.put("capacity", capacity);
        content.put("available", available);
        content.put("limit", limit);

        main.put("request", "room");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoom_of() {
        return room_of;
    }

    public void setRoom_of(int room_of) {
        this.room_of = room_of;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String from, String to) {
        time[0] = from;
        time[1] = to;
    }

    public int[] getLimit() {
        return limit;
    }

    public void setLimit(int from, int to) {
        limit[0] = from;
        limit[1] = to;
    }
    
}
