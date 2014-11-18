package controller;

import net.webservicex.LengthUnit;
import net.webservicex.LengthUnitSoap;
/**
 * Controller gets SOAP client from the factory.
 * 
 * @author Juthamas Utamaphethai
 *
 */
public class LengthController {
	/** LengthUnit connects to SOAP client  */
	private LengthUnit factory;
	
	/** SOAP client */
	private LengthUnitSoap lengthUnitSoap;
	
	public LengthController() {
		factory = new LengthUnit();
		lengthUnitSoap = factory.getLengthUnitSoap();
	}
	
	public LengthUnitSoap getSoap(){
		return lengthUnitSoap;
	}
}
