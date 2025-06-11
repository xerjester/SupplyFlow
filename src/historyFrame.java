import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class historyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table1;
	private JTable table2;
	private JTextField txtReceiveSearch;
	private JTextField txtReturnSearch;
	DefaultTableModel model1,model2;
	Connection conn;
	String url = "jdbc:mysql://localhost/rrrdb";
	recGraph rcg;
	retGraph rct;

	public historyFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				loadData1();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				loadData2();
			}
		});
		setTitle("History");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 664);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		model1 = new DefaultTableModel();
		model1.setColumnIdentifiers(new String[]{"ReceiveID", "OrderID", "ReceiveDate"});
		model2 = new DefaultTableModel();
		model2.setColumnIdentifiers(new String[]{"ReturnID", "OrderID", "ReturnDate"});
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 142, 489, 192);
		contentPane.add(scrollPane1);
		
		table1 = new JTable();
		scrollPane1.setViewportView(table1);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 422, 489, 192);
		contentPane.add(scrollPane2);
		
		table2 = new JTable();
		scrollPane2.setViewportView(table2);
		
		JButton btnSearch1 = new JButton("Search");
		btnSearch1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReceiveSearch();
			}
		});
		btnSearch1.setBounds(300, 108, 89, 23);
		contentPane.add(btnSearch1);
		JButton btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
		btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBGcolor.setBounds(366, 11, 133, 23);
		contentPane.add(btnBGcolor);
        btnBGcolor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Background Color", contentPane.getBackground());
                if (newColor != null) {
                    contentPane.setBackground(newColor);
                }
            }
        });
		
		txtReceiveSearch = new JTextField();
		txtReceiveSearch.setText("Enter Receive ID");
		txtReceiveSearch.setColumns(10);
		txtReceiveSearch.setBounds(148, 111, 140, 20);
		contentPane.add(txtReceiveSearch);
		
		JLabel lblRecieve = new JLabel("Receive Search : ");
		lblRecieve.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRecieve.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblRecieve.setBounds(10, 110, 128, 19);
		contentPane.add(lblRecieve);
		
		JButton btnSearch2 = new JButton("Search");
		btnSearch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnSearch();
			}
		});
		btnSearch2.setBounds(300, 388, 89, 23);
		contentPane.add(btnSearch2);
		
		txtReturnSearch = new JTextField();
		txtReturnSearch.setText("Enter Return ID");
		txtReturnSearch.setColumns(10);
		txtReturnSearch.setBounds(148, 391, 140, 20);
		contentPane.add(txtReturnSearch);
		
		JLabel lblReturnSearch = new JLabel("Return Search : ");
		lblReturnSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblReturnSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReturnSearch.setBounds(10, 390, 128, 19);
		contentPane.add(lblReturnSearch);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData1();
			}
		});
		btnShowAll.setBounds(410, 108, 89, 23);
		contentPane.add(btnShowAll);
		
		JButton btnShowAll_1 = new JButton("Show All");
		btnShowAll_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData2();
			}
		});
		btnShowAll_1.setBounds(410, 388, 89, 23);
		contentPane.add(btnShowAll_1);
		
		JButton btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/back.png")));
		btnBack.setBounds(10, 11, 46, 33);
		contentPane.add(btnBack);
		
		JLabel lblProductManagements = new JLabel("รายการรับ - คืนสินค้า");
		lblProductManagements.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductManagements.setFont(new Font("Angsana New", Font.BOLD, 28));
		lblProductManagements.setBounds(131, 40, 225, 44);
		contentPane.add(lblProductManagements);
		txtReceiveSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			ReceiveSearch();
        		}
        	}
        });
		txtReceiveSearch.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			txtReceiveSearch.setText("");
        		}
        	}
		});
		txtReturnSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			ReturnSearch();
        		}
        	}
        });
		txtReturnSearch.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			txtReturnSearch.setText("");
        		}
        	}
		});
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		table1.setModel(model1);
		table2.setModel(model2);
		
		JButton btnShowGraph = new JButton("Show Graph");
		btnShowGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rcg = new recGraph();
		        int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		        int frameWidth = rcg.getWidth();
		        int xPosition = screenWidth - frameWidth;
		        int yPosition = 100;
		        rcg.setLocation(xPosition,yPosition);
				rcg.setVisible(true);
				rcg.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		btnShowGraph.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnShowGraph.setBounds(410, 74, 89, 23);
		contentPane.add(btnShowGraph);
		
		JButton btnShowGraph2 = new JButton("Show Graph");
		btnShowGraph2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rct = new retGraph();
		        int screenWidth = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		        int frameWidth = rct.getWidth();
		        int xPosition = screenWidth - frameWidth;
		        int yPosition = 100;
		        rct.setLocation(xPosition,yPosition);
		        rct.setVisible(true);
		        rct.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
		});
		btnShowGraph2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnShowGraph2.setBounds(410, 354, 89, 23);
		contentPane.add(btnShowGraph2);
	}
    public void loadData1() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT * FROM receiveproduct";
            
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model1.setRowCount(0);

            while (rs.next()) {
                String rows[] = {
                        rs.getString("ReceiveID"),
                        rs.getString("OrderID"),
                        rs.getString("ReceiveDate")
                };
                model1.addRow(rows);
            }
            
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void loadData2() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT * FROM returnproduct";
            
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model2.setRowCount(0);

            while (rs.next()) {
                String rows[] = {
                        rs.getString("ReturnID"),
                        rs.getString("OrderID"),
                        rs.getString("ReturnDate")
                };
                model2.addRow(rows);
            }
            
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }
    public void ReceiveSearch() {
        String searchID = txtReceiveSearch.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Receive ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM receiveproduct WHERE ReceiveID = ?")) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model1.setRowCount(0);
            if (rs.next()) {
                String[] row = {
                		rs.getString("ReceiveID"),
                        rs.getString("OrderID"),
                        rs.getString("ReceiveDate")
                };
                model1.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No Receive found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void ReturnSearch() {
        String searchID = txtReturnSearch.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Return ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM returnproduct WHERE ReturnID = ?")) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model2.setRowCount(0);
            if (rs.next()) {
                String[] row = {
                		rs.getString("ReturnID"),
                        rs.getString("OrderID"),
                        rs.getString("ReturnDate")
                };
                model2.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No Return found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
