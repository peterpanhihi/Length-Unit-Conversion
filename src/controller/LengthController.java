package controller;

import net.webservicex.LengthUnit;
import net.webservicex.LengthUnitSoap;

public class LengthController {
	private LengthUnit factory;
	private LengthUnitSoap lengthUnitSoap;
	public LengthController() {
		factory = new LengthUnit();
		lengthUnitSoap = factory.getLengthUnitSoap();
	}
	
	public LengthUnitSoap getSoap(){
		return lengthUnitSoap;
	}
}
