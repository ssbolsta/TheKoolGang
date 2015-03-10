package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ModifyEventRequest implements Request {
    
    private String name = "";
    private String desc = "";
    private int admin = -1;
    private int room = -1;
    private String[] time = new String[]{"",""};
    private String date = "";
    private ArrayList<Integer> add_members = new ArrayList<Integer>();
    private ArrayList<Integer> remove_members = new ArrayList<Integer>();
    private ArrayList<Integer> add_groups = new ArrayList<Integer>();
    private ArrayList<Integer> remove_groups = new ArrayList<Integer>();

    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray time = new JSONArray();
        JSONArray add_members = new JSONArray();
        JSONArray remove_members = new JSONArray();
        
        add_members.addAll(this.add_members);
        remove_members.addAll(this.remove_members);

        time.add(this.time[0]);
        time.add(this.time[1]);

        content.put("name", name);
        content.put("desc", desc);
        content.put("admin", admin);
        content.put("room", room);
        content.put("date", date);
        content.put("time", time);
        content.put("add_members", add_members);
        content.put("remove_members", remove_members);

        main.put("request", "event");
        main.put("type", "modify");
        main.put("content", content);

        return main.toJSONString();
    }
    
    public boolean addMemeberToAdd(int pnr) {
        return add_members.add(new Integer(pnr));
    }
    
    public boolean addGroupsToAdd(int pnr) {
        return add_groups.add(new Integer(pnr));
    }
    
    public ArrayList<Integer> getMeembersToAdd() {
        return add_members;
    }
    
    public ArrayList<Integer> getGroupsToAdd() {
        return add_groups;
    }
    
    public boolean removeMemberToAdd(int pnr) {
        return add_members.remove(new Integer(pnr));
    }

    public boolean addMemeberToRemove(int pnr) {
        return remove_members.add(new Integer(pnr));
    }

    public boolean removeGroupToAdd(int pnr) {
        return add_groups.remove(new Integer(pnr));
    }

    public boolean addGroupToRemove(int pnr) {
        return remove_members.add(new Integer(pnr));
    }

    public ArrayList<Integer> getMeembersToRemove() {
        return remove_members;
    }

    public ArrayList<Integer> getGroupsToRemove() {
        return remove_groups;
    }

    public boolean removeMemberToRemove(int pnr) {
        return remove_members.remove(new Integer(pnr));
    }

    public boolean removeGroupsToRemove(int gid) {
        return remove_groups.remove(new Integer(gid));
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
    
}
