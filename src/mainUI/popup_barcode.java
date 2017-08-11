package mainUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class popup_barcode extends JDialog {

	/**
	 * 
	 */
	public mainUI mainframe;
	
	private JDialog dialogmain;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			popup_barcode dialog = new popup_barcode(null, null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public popup_barcode(mainUI main, String item_name, String item_code) {
		setResizable(false);
		this.mainframe = main;
		dialogmain = this;
		dialogmain.setBounds(100, 100, 450, 165);
		dialogmain.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogmain.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel item_name_Label = new JLabel(item_name);
			contentPanel.add(item_name_Label, BorderLayout.NORTH);
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.WEST);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JRadioButton in_radio_btn = new JRadioButton("\uC785\uACE0");
				in_radio_btn.setActionCommand( in_radio_btn.getText() );
				buttonGroup.add(in_radio_btn);
				panel.add(in_radio_btn);
			}
			{
				JRadioButton out_radio_btn = new JRadioButton("\uCD9C\uACE0");
				out_radio_btn.setActionCommand( out_radio_btn.getText() );
				out_radio_btn.setSelected(true);
				buttonGroup.add(out_radio_btn);
				panel.add(out_radio_btn);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.EAST);
			{
				JLabel lblNewLabel = new JLabel("\uC218\uB7C9");
				panel.add(lblNewLabel);
			}
			{
				textField = new JTextField();
				panel.add(textField);
				textField.setColumns(10);
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			{
				JLabel label = new JLabel("\uBE44\uACE0");
				panel.add(label);
			}
			{
				textField_1 = new JTextField();
				panel.add(textField_1);
				textField_1.setColumns(10);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			dialogmain.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("\uC800\uC7A5");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String selected_in_out = buttonGroup.getSelection().getActionCommand();
						int count = 0;
						String[] data = {item_name,mainframe.getDate(),selected_in_out,textField_1.getText(),textField.getText()};
						
						if(textField.getText().length()==0){
							JOptionPane.showMessageDialog(dialogmain, "수량을 입력해 주세요");
						}else{
							if(selected_in_out=="입고"){
								count = Integer.parseInt(textField.getText());
							}else{
								count = -Integer.parseInt(textField.getText());
							}
							//CSV에 로그 파일을 이어서 계속 씀
							CSVReader reader;
							try {
								reader = new CSVReader(new FileReader("물품변경이력.csv"));
								List<String[]> csvBody = reader.readAll();
								csvBody.add(data);
						        reader.close();

						        CSVWriter writer = new CSVWriter(new FileWriter("물품변경이력.csv"));
						        writer.writeAll(csvBody);
						        writer.flush();
						        writer.close();
							} catch (FileNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//main frame에 추가
							mainframe.setTableItem(item_name,selected_in_out,textField_1.getText(),textField.getText());
							mainframe.changeItemCount(item_code, count);

							dialogmain.dispose();
						}
					}
					
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("\uCDE8\uC18C");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialogmain.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
