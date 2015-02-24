package kalender.kontroller;

import java.time.LocalDate;
import java.time.LocalTime;

public class test {

	public static void main(String[] args) {
		RomController g = new RomController();
		g.searchForRooom("-1", LocalDate.of(2015, 3, 25), LocalTime.of(11, 0), LocalTime.of(13, 0));
	}
}
