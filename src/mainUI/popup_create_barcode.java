package mainUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.opencsv.CSVReader;

import comp.barcode;
import net.sourceforge.barbecue.BarcodeException;

public class popup_create_barcode extends JDialog {

	/**
	 * 
	 */
	private JDialog dialogmain;
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	
	public String item_list_file = "";
	public String item_list_file_name = "";

	/**
	 * Launch the application.
	 */
	
	//private FileChooser chooser;
	
	public static void main(String[] args) {
		try {
			popup_create_barcode dialog = new popup_create_barcode(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public popup_create_barcode(mainUI mainframe) {
		setResizable(false);
		dialogmain = this;
		dialogmain.setBounds(100, 100, 450, 300);
		dialogmain.getContentPane().setLayout(new BorderLayout());
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.NORTH);
			{
				JLabel lblNewLabel = new JLabel("\uBC14\uCF54\uB4DC \uC0DD\uC131\uAE30");
				panel.add(lblNewLabel);
			}
		}
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialogmain.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(0, 1, 0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblNewLabel_1 = new JLabel("\uCF54\uB4DC\uB85C \uC0DD\uC131: \uCF54\uB4DC\uB97C \uC785\uB825\uD55C \uD6C4 \uC800\uC7A5\uC744 \uB204\uB974\uBA74 \uC774\uBBF8\uC9C0\uB85C \uC800\uC7A5\uB429\uB2C8\uB2E4.");
				panel.add(lblNewLabel_1);
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel lblNewLabel_2 = new JLabel("\uCF54\uB4DC");
					lblNewLabel_2.setBounds(14, 16, 28, 18);
					panel_1.add(lblNewLabel_2);
				}
				{
					textField = new JTextField();
					textField.setBounds(56, 13, 289, 24);
					panel_1.add(textField);
					textField.setColumns(10);
				}
				{
					JButton btnNewButton = new JButton("\uC800\uC7A5");
					btnNewButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(textField.getText().length()<=0){
								JOptionPane.showMessageDialog(dialogmain, "생성할 코드를 입력해 주세요.");
							}else{
								JFileChooser chooser = new JFileChooser();
								chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								
								if(chooser.showSaveDialog(dialogmain) == JFileChooser.APPROVE_OPTION){
									barcode codegenerator = new barcode();
									try {
										codegenerator.outputtingBarcodeAsPNG(chooser.getSelectedFile().getPath()+"/"+textField.getText()+".png", textField.getText());
										textField.setText("");
									} catch (BarcodeException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}							
							}
						}
					});
					btnNewButton.setBounds(359, 12, 61, 27);
					panel_1.add(btnNewButton);
				}
			}
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new GridLayout(0, 1, 0, 0));
			{
				JLabel lblNewLabel_3 = new JLabel("\uBB3C\uD488 \uBAA9\uB85D\uC5D0\uC11C \uC0DD\uC131 : \uD30C\uC77C\uC744 \uBD88\uB7EC\uC628 \uD6C4 \uC800\uC7A5\uC744 \uB204\uB974\uBA74 \uC0DD\uC131");
				panel.add(lblNewLabel_3);
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel lblNewLabel_4 = new JLabel("\uD30C\uC77C");
					lblNewLabel_4.setBounds(14, 14, 40, 18);
					panel_1.add(lblNewLabel_4);
				}
				{
					textField_1 = new JTextField();
					textField_1.setBounds(56, 11, 229, 24);
					textField_1.setEditable(false);
					panel_1.add(textField_1);
					textField_1.setColumns(10);
				}
				{
					JButton btnNewButton_1 = new JButton("\uC800\uC7A5");
					btnNewButton_1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(item_list_file.length()<=0){
								JOptionPane.showMessageDialog(dialogmain, "목록을 작성할 파일을 열어 주세요.");
							}else{
								JFileChooser chooser = new JFileChooser();
								chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
								
								if(chooser.showSaveDialog(dialogmain) == JFileChooser.APPROVE_OPTION){	
									saveBarcodeImages(item_list_file, chooser.getSelectedFile().getPath());
									JOptionPane.showMessageDialog(dialogmain, "저장이 완료 되었습니다.");
									textField_1.setText("");
									item_list_file = "";
								}
							}
						}
					});
					btnNewButton_1.setBounds(359, 10, 61, 27);
					panel_1.add(btnNewButton_1);
				}
				
				JButton btnNewButton_2 = new JButton("\uC5F4\uAE30");
				btnNewButton_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
						
						if(chooser.showOpenDialog(dialogmain) == JFileChooser.APPROVE_OPTION){
							item_list_file = chooser.getSelectedFile().getAbsolutePath();
							textField_1.setText(chooser.getSelectedFile().getAbsolutePath());
						}
					}
				});
				btnNewButton_2.setBounds(285, 10, 61, 27);
				panel_1.add(btnNewButton_2);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			dialogmain.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("닫기");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dialogmain.dispose();
					}
				});
				cancelButton.setActionCommand("닫기");
				buttonPane.add(cancelButton);
			}
		}
	}
	public void saveBarcodeImages(String path, String targetPath){
		try {
			CSVReader reader = new CSVReader(new FileReader(path));
			List<String[]> csvBody = reader.readAll();
			for(int i = 0; i < csvBody.size() ; i++){
				barcode codegenerator = new barcode();
				try {
					codegenerator.outputtingBarcodeAsPNG(targetPath+"/"+csvBody.get(i)[0]+".png", csvBody.get(i)[0]);
				} catch (BarcodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        reader.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
