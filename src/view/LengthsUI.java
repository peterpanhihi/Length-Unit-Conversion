package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import controller.LengthController;
import net.webservicex.LengthUnitSoap;
import net.webservicex.Lengths;

/**
 * User interface of Length Unit Conversion.
 * 
 * @author Juthamas Utamaphethai
 *
 */
public class LengthsUI extends JFrame{
	private JLabel imageLabel;
	private ImageIcon loader;
	
	/** TextField to show output */
	private JTextField toTxt;
	
	/** from measurement conversion */
	private Lengths fromUnit;
	
	/** to measurement conversion */
	private Lengths toUnit;
	
	private JTextField fromTxt;
	
	private LengthController controller;
	
	/** SOAP client of length unit conversion */
	private LengthUnitSoap lengthUnitSoap;
	
	private Timer timer;
	
	/**
	 * Components of user interface.
	 * @param lengthUnitSoap SOAP client
	 */
	public LengthsUI() {
		super("Length Unit Conversion");
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
		
		fromTxt = new JTextField();
		fromTxt.setBounds(30, 75, 315, 20);
		getContentPane().add(fromTxt);
		fromTxt.setColumns(10);
		
		JLabel label3 = new JLabel("=");
		label3.setBounds(357, 64, 16, 37);
		label3.setFont (label3.getFont ().deriveFont (20.0f));
		getContentPane().add(label3);
		
		toTxt = new JTextField();
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
		list1.setSelectedIndex(0);
		scroll1.setViewportView(list1);
		getContentPane().add(scroll1);
		
		// ScrollPane2
		JScrollPane scroll2 = new JScrollPane();
		scroll2.setBounds(385, 130, 315, 315);

		// List2
		final JList list2 = new JList(Lengths.values());
		list2.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list2.setSelectedIndex(0);
		scroll2.setViewportView(list2);
		getContentPane().add(scroll2);
		
		//Loading image
		imageLabel = new JLabel();
		loader = new ImageIcon(this.getClass().getResource("loader.gif"));
        imageLabel.setBounds(355, 450, 16, 16);
        getContentPane().add(imageLabel);
		
		//Convert button
		JButton btn1 = new JButton("CONVERT");
		btn1.setBounds(174, 474, 370, 35);
		getContentPane().add(btn1);

		// Event Click
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayLoader();
				int[] selFromList = list1.getSelectedIndices();
				for(int index : selFromList){
					fromUnit = (Lengths) list1.getModel().getElementAt(index);
				}
				int[] selToList = list2.getSelectedIndices();
				for(int index : selToList){
					toUnit = (Lengths) list2.getModel().getElementAt(index);
				}
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						new LengthsWorker().execute();
					}
				});
	
			}
		});
		
		this.setVisible(true);
		this.setResizable(false);
		
		connectSoap();
	}
	
	public void connectSoap(){
		try{
			controller = new LengthController();
			lengthUnitSoap = controller.getSoap();
		}catch(Exception e){
			showInternetError();
		}
	}
	
	public void displayLoader(){
		 imageLabel.setIcon(loader);
	}
	
	public void showInternetError(){
		JPanel panel = new JPanel();
		
		Object[] options = {"Exit","Retry"};
		int error = JOptionPane.showOptionDialog(panel,"Internet Connection Error","ERROR",
				JOptionPane.YES_NO_OPTION,JOptionPane.ERROR_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[1]); //default button title
		if(error == JOptionPane.YES_OPTION){
			System.exit(1);
		}else{
			connectSoap();
		}
	}
	
	public class LengthsWorker extends SwingWorker<Double,Void>{
		/** number to convert */
		private double value;
		private double answer;
		
		@Override
		protected Double doInBackground() throws Exception {
			value = 0;
			startTimer();
			try{
				value = Double.parseDouble(fromTxt.getText());
				toTxt.setText("");
			}
			catch(NumberFormatException ex){
				timer.stop();
				JOptionPane.showMessageDialog(null,"Please fill a length to convert.");
				resetValue();
			}
			try{
			answer = lengthUnitSoap.changeLengthUnit(value, fromUnit, toUnit);
			}catch(Exception e){
				showInternetError();
			}
			return answer;
		}

		@Override
		protected void done(){
			try {
				toTxt.setText(get().toString());
				timer.stop();
				resetValue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		public void startTimer(){
			timer = new Timer(1000*15, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					showInternetError();
					resetValue();
				}
			});
			
			timer.start();
		}
		
		public void resetValue(){
			if(timer != null){
		    	imageLabel.setIcon(null);
		    	timer.stop();
		    }
		}
	}
}
