package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import controller.LengthsWorker;
import net.webservicex.LengthUnitSoap;
import net.webservicex.Lengths;

/**
 * User interface of Length Unit Conversion.
 * 
 * @author Juthamas Utamaphethai
 *
 */
public class LengthsUI extends JFrame{
	/** SOAP client of length unit conversion */
	private LengthUnitSoap lengthUnitSoap;
	
	/** Multi-threading for Length unit conversion */
	private LengthsWorker worker;
	
	/**
	 * Components of user interface.
	 * @param lengthUnitSoap SOAP client
	 */
	public LengthsUI( final LengthUnitSoap lengthUnitSoap) {
		super("Length Unit Conversion");
		this.lengthUnitSoap = lengthUnitSoap;
		setBounds(300,200,720,550);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel label1 = new JLabel("From");
		label1.setBounds(30, 26, 116, 37);
		label1.setFont (label1.getFont ().deriveFont (30.0f));
		getContentPane().add(label1);
		
		JLabel label2 = new JLabel("To");
		label2.setBounds(386, 26, 116, 37);
		label2.setFont (label2.getFont ().deriveFont (30.0f));
		getContentPane().add(label2);
		
		final JTextField fromTxt = new JTextField();
		fromTxt.setBounds(30, 75, 315, 20);
		getContentPane().add(fromTxt);
		fromTxt.setColumns(10);
		
		JLabel label3 = new JLabel("=");
		label3.setBounds(357, 64, 16, 37);
		label3.setFont (label3.getFont ().deriveFont (20.0f));
		getContentPane().add(label3);
		
		final JTextField toTxt = new JTextField();
		toTxt.setBounds(385, 75, 315, 20);
		getContentPane().add(toTxt);
		toTxt.setColumns(10);
		toTxt.setEditable(false);
		
		// ScrollPane1
		JScrollPane scroll1 = new JScrollPane();
		scroll1.setBounds(30, 130, 315, 315);

		// List1
		final JList list1 = new JList(Lengths.values());
		list1.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scroll1.setViewportView(list1);
		getContentPane().add(scroll1);
		
		// ScrollPane2
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setBounds(385, 130, 315, 315);

		// List2
		final JList list2 = new JList(Lengths.values());
		list2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scroll2.setViewportView(list2);
		getContentPane().add(scroll2);
		
		JButton btn1 = new JButton("CONVERT");
		btn1.setBounds(174, 474, 370, 35);
		getContentPane().add(btn1);

		// Event Click
		btn1.addActionListener(new ActionListener() {
			Lengths fromUnit, toUnit;
			@Override
			public void actionPerformed(ActionEvent e) {
				int[] selFromList = list1.getSelectedIndices();
				for(int index : selFromList){
					fromUnit = (Lengths) list1.getModel().getElementAt(index);
				}
				int[] selToList = list2.getSelectedIndices();
				for(int index : selToList){
					toUnit = (Lengths) list2.getModel().getElementAt(index);
				}
				try{
					worker = new LengthsWorker(toTxt, lengthUnitSoap, Double.parseDouble(fromTxt.getText()), fromUnit, toUnit); 
				}
				catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null,"shoule be a number.");
				}
				worker.execute();
			}
		});
		
		this.setVisible(true);
		this.setResizable(false);
	}
}
