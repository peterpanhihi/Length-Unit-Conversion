package view;

import net.webservicex.LengthUnit;
import net.webservicex.LengthUnitSoap;

/**
 * Start user interface of Length unit conversion and
 * get SOAP client from the factory inject to the UI.
 * 
 * @author Juthamas Utamaphethai
 *
 */
public class Main {
	public static void main(String[] args) {
			LengthUnit factory = new LengthUnit();
			LengthUnitSoap lengthUnitSoap = factory.getLengthUnitSoap();
			LengthsUI ui = new LengthsUI(lengthUnitSoap);
	}
}
