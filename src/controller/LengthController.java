package controller;

import net.webservicex.LengthUnit;
import net.webservicex.LengthUnitSoap;

public class LengthController {
	private LengthUnitSoap lengthUnitSoap;
	public LengthController() {
		LengthUnit factory = new LengthUnit();
		lengthUnitSoap = factory.getLengthUnitSoap();
	}
	
	public LengthUnitSoap getSoap(){
		return lengthUnitSoap;
	}
}
