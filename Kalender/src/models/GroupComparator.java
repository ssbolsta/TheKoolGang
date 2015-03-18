package models;

import java.util.Comparator;
import models.Group;

public class GroupComparator implements Comparator<Group>{

	@Override
	public int compare(Group arg0, Group arg1) {
		return arg0.getName().compareToIgnoreCase(arg1.getName());
	}

}
