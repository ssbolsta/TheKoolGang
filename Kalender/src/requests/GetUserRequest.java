package requests;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GetUserRequest implements Request {
    
    private int id = -1;
    private String first_name = "";
    private String last_name = "";
    private String username = "";
    private String password = "";
    private int member_of = -1;
    private int participant_of = -1;
    private String email = "";
    private int phone = -1;
    private int[] limit = new int[]{0,0};
    
    
    @Override
    public String toString() {
        JSONObject main = new JSONObject();
        JSONObject content = new JSONObject();
        JSONArray limit = new JSONArray();
        
        limit.add(this.limit[0]);
        limit.add(this.limit[1]);
        
        content.put("id", id);
        content.put("first_name", first_name);
        content.put("last_name", last_name);
        content.put("username", username);
        content.put("password", password);
        content.put("member_of", member_of);
        content.put("participant_of", participant_of);
        content.put("email", email);
        content.put("phone", phone);
        content.put("limit", limit);
        
        main.put("request", "user");
        main.put("type", "get");
        main.put("content", content);
        
        return main.toJSONString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getMember_of() {
        return member_of;
    }

    public void setMember_of(int member_of) {
        this.member_of = member_of;
    }

    public int getParticipant_of() {
        return participant_of;
    }

    public void setParticipant_of(int participant_of) {
        this.participant_of = participant_of;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int[] getLimit() {
        return limit;
    }

    public void setLimit(int from, int to) {
        limit[0] = from;
        limit[1] = to;
    }
    
}