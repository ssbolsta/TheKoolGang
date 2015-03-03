package requests;

/**
 * Created by boye on 03.03.15.
 */
public class GetRoomRequest implements Request {

    private int id = -1;
    private String name = "";
    private int room_of = -1;
    private int participant_of = -1;
    private String email = "";
    private int phone = -1;
    private int[] limit = new int[]{0,0};
    
}
