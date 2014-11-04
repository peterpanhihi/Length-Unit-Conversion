package controller;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import net.webservicex.LengthUnitSoap;
import net.webservicex.Lengths;

/**
 * Convert value of length unit to other length unit.
 * Multi-threading in Java Swing with SwingWorker to execute a long-running task.
 * 
 * @author Juthamas Utamaphethai
 *
 */
public class LengthsWorker extends SwingWorker<Double,Double>{
	/** number to convert */
	private double value;
	
	/** from measurement conversion */
	private Lengths fromUnit;
	
	/** to measurement conversion */
	private Lengths toUnit;
	
	/** SOAP client of length unit */
	private LengthUnitSoap soap;
	
	/** TextField to show output */
	private JTextField toTxt;
	
	/**
	 * Constructor of LengthWorker.
	 * @param toTxt TextField
	 * @param soap client
	 * @param value to convert
	 * @param from Length unit
	 * @param to Length unit
	 */
	public LengthsWorker(JTextField toTxt,LengthUnitSoap soap, double value, Lengths from, Lengths to) {
		this.soap = soap;
		this.value = value;
		this.fromUnit = from;
		this.toUnit = to;
		this.toTxt = toTxt;
	}
	
	@Override
	protected Double doInBackground() throws Exception {
		double answer = soap.changeLengthUnit(value, fromUnit, toUnit);
		return answer;
	}

	@Override
	protected void done() {
		try {
			toTxt.setText(get().toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
}
