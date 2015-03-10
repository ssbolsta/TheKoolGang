package models;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person>{

	@Override
	public int compare(Person arg0, Person arg1) {
		return arg0.getFullNameProperty().get().compareToIgnoreCase(arg1.getFullNameProperty().get());
	}

}
