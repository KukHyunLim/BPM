package mainUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class mainUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private mainUI frame;
	
	String rootPath= "";
	Object[][] filelist;
	File[] fileList;
	
	private JTable table;
	private DefaultTableModel model;
	ButtonGroup group = new ButtonGroup();
	int selected_rbtn_index = -1;
	private JTextField code_input_textField;

	public static void main(String[] args) {
		try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
		} 
	    catch (UnsupportedLookAndFeelException e) {
	       // handle exception
	    }
	    catch (ClassNotFoundException e) {
	       // handle exception
	    }
	    catch (InstantiationException e) {
	       // handle exception
	    }
	    catch (IllegalAccessException e) {
	       // handle exception
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public mainUI() {
		initialize();
		LoadTableData();
	}

	private void initialize() {
		frame = this;
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		frame.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BorderLayout(0, 0));
		
		JButton barcode_create_btn = new JButton("\uBC14\uCF54\uB4DC \uC0DD\uC131");
		barcode_create_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click_btn_accept();
			}
		});
		
		northPanel.add(barcode_create_btn, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		northPanel.add(panel);
		
		JPanel panel_1 = new JPanel();
		northPanel.add(panel_1, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("\uCF54\uB4DC\uC785\uB825");
		panel_1.add(lblNewLabel);
		
		code_input_textField = new JTextField();
		code_input_textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// CSV에서 코드 가져오기
				String item_name = LoadItem(code_input_textField.getText());
				String item_code = code_input_textField.getText();
				if(item_code.equals("19900301")){
					JOptionPane.showMessageDialog(frame, "1포병여단 2016 6월30일 전역 체계운용장교 만듦");
				}
				if(item_name.length()==0){
					JOptionPane.showMessageDialog(frame, "물품이 없습니다. 물품이 있는지 확인해 주세요.");
				} else {
					popup_barcode popup = new popup_barcode(frame, item_name, item_code);
					popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					popup.setVisible(true);
				}
				code_input_textField.setText("");
			}
		});
		panel_1.add(code_input_textField);
		code_input_textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane);
		
		model = new DefaultTableModel(new Object[]{"물품명", "날짜", "입출고 구분", "비고", "수량"},0);
		table = new JTable(model);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel("* \uC785\uCD9C\uACE0 \uAE30\uB85D\uC740 \uC2E4\uD589\uD30C\uC77C\uACFC \uAC19\uC740 \uD3F4\uB354\uC5D0 \uC0DD\uC131\uB429\uB2C8\uB2E4.");
		panel_2.add(lblNewLabel_1);
	}

	private void click_btn_accept(){
		popup_create_barcode popup = new popup_create_barcode(frame);
		popup.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		popup.setVisible(true);
	}

	public void setTableItem(String name, String in_out, String info, String number){
		Object[] data = {name,getDate(),in_out,info,number}; 
		model.addRow(data);
	}
	
	public void changeItemCount(String code, int Count){
		int item_count_index = 2;
		int select_item_index = 0;
		
		CSVReader reader;
		try {
			reader = new CSVReader(new FileReader("물품목록.csv"));
			List<String[]> csvBody = reader.readAll();
	        for(int i = 0; i < csvBody.size() ; i++){
	        	if(code.equals(csvBody.get(i)[0])){
	        		select_item_index = i;
	        		break;
	        	}
	        }
	        int selected_item_count = Integer.parseInt(csvBody.get(select_item_index)[item_count_index]);
	        csvBody.get(select_item_index)[item_count_index]=(selected_item_count+Count)+"";
	        reader.close();

	        CSVWriter writer = new CSVWriter(new FileWriter("물품목록.csv"));
	        writer.writeAll(csvBody);
	        writer.flush();
	        writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDate(){
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
		Date currentTime = new Date ( );
		return formatter.format ( currentTime );
	}
	
	public void LoadTableData(){
		try {
			CSVReader reader = new CSVReader(new FileReader("물품변경이력.csv"));
			List<String[]> csvBody = reader.readAll();
			for(int i = 0; i < csvBody.size() ; i++){
				model.addRow(csvBody.get(i));
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
	
	public String LoadItem(String code){
		String itemname = "";
		try {
			CSVReader reader = new CSVReader(new FileReader("물품목록.csv"));
			List<String[]> csvBody = reader.readAll();
			for(int i = 0; i < csvBody.size() ; i++){
				if(code.equals(csvBody.get(i)[0])){
					itemname = csvBody.get(i)[1];
	        		break;
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
		
		return itemname;
	}
}