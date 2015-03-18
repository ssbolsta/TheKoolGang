package models;

import java.util.Comparator;
import models.Room;

public class RoomComparator implements Comparator<Room>{

	@Override
	public int compare(Room arg0, Room arg1) {
		return arg0.getCapacity() - arg1.getCapacity();
	}

}
