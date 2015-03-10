package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ModifyGroupRequest implements Request {

    private int id = -1;
    private String name = "";
    private int admin = -1;
    private ArrayList<Integer> add_members = new ArrayList<Integer>();
    private ArrayList<Integer> remove_members = new ArrayList<Integer>();
    private ArrayList<Integer> add_groups = new ArrayList<Integer>();
    private ArrayList<Integer> remove_groups = new ArrayList<Integer>();

    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray add_members = new JSONArray();
        JSONArray remove_members = new JSONArray();
        JSONArray add_groups = new JSONArray();
        JSONArray remove_groups = new JSONArray();

        add_members.addAll(this.add_members);
        remove_members.addAll(this.remove_members);
        add_members.addAll(this.add_groups);
        remove_members.addAll(this.remove_groups);

        content.put("name", name);
        content.put("id", id);
        content.put("admin", admin);
        content.put("add_members", add_members);
        content.put("remove_members", remove_members);
        content.put("add_groups", add_groups);
        content.put("remove_groups", remove_groups);

        main.put("request", "group");
        main.put("type", "modify");
        main.put("content", content);

        return main.toJSONString();
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean addMemeberToAdd(int pnr) {
        return add_members.add(new Integer(pnr));
    }

    public ArrayList<Integer> getMeembersToAdd() {
        return add_members;
    }

    public boolean removeMemberToAdd(int pnr) {
        return add_members.remove(new Integer(pnr));
    }

    public boolean addMemeberToRemove(int pnr) {
        return remove_members.add(new Integer(pnr));
    }

    public ArrayList<Integer> getMeembersToRemove() {
        return remove_members;
    }

    public boolean removeMemberToRemove(int pnr) {
        return remove_members.remove(new Integer(pnr));
    }

    public boolean addGroupToAdd(int gnr) {
        return add_groups.add(new Integer(gnr));
    }

    public ArrayList<Integer> getGroupsToAdd() {
        return add_groups;
    }

    public boolean removeGroupsToAdd(int gnr) {
        return add_groups.remove(new Integer(gnr));
    }

    public boolean addGroupsToRemove(int gnr) {
        return remove_groups.add(new Integer(gnr));
    }

    public ArrayList<Integer> getGroupsToRemove() {
        return remove_groups;
    }

    public boolean removeGroupsToRemove(int gnr) {
        return remove_groups.remove(new Integer(gnr));
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