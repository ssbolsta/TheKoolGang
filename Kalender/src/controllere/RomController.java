package controllere;

import java.time.LocalDate;
import java.time.LocalTime;


public class RomController {
	
	public void searchForRooom(String spaces, LocalDate date, LocalTime start, LocalTime end){
		try{
			Integer.parseUnsignedInt(spaces);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
	}
}
