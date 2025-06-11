import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class productFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	DefaultTableModel model;
	Connection conn;
	String url = "jdbc:mysql://localhost/rrrdb";
	private JButton btnUpdate;
	private JButton btnShowAll;
	private JButton btnDelete;
	private JButton btnClear;
	private JLabel lblProductSearch;
	private JTextField textSearchID;
	private JButton btnSearch;
	private JLabel lblProductManagements;
	private JButton btnBack;
	private JButton btnBGcolor;
	private JLabel lblProductId;
	private JLabel lblPrice;
	private JTextField textPrice;
	private JLabel lblProductName;
	private JLabel lblName;
	private JLabel lblID;
	private JButton btnSell;
	private JTextField txtSell;
	private JLabel lblProductName_1;
	public productFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				loadData();
			}
		});
		setTitle("Product");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 718, 524);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 230, 664, 244);
		contentPane.add(scrollPane);
		
		table = new JTable();
		model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[]{"ProductID", "Product Name", "Product Type Name", "Price", "Amounts"});
		table.setModel(model);
		scrollPane.setViewportView(table);
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                lblID.setText(table.getValueAt(selectedRow, 0).toString());
                lblName.setText(table.getValueAt(selectedRow, 1).toString());
                textPrice.setText(table.getValueAt(selectedRow, 3).toString());
            }
        });
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Update();
			}
		});
		btnUpdate.setBounds(20, 158, 89, 23);
		contentPane.add(btnUpdate);
		
		btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData();
			}
		});
		btnShowAll.setBounds(595, 196, 89, 23);
		contentPane.add(btnShowAll);
		
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete();
			}
		});
		btnDelete.setBounds(496, 196, 89, 23);
		contentPane.add(btnDelete);
		
		btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                lblID.setText("None");
                lblName.setText("None");
                textPrice.setText("");
			}
		});
		btnClear.setBounds(595, 158, 89, 23);
		contentPane.add(btnClear);
		
		lblProductSearch = new JLabel("Product Search : ");
		lblProductSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductSearch.setBounds(20, 198, 128, 19);
		contentPane.add(lblProductSearch);
		
		textSearchID = new JTextField();
		textSearchID.setText("Enter Product ID");
		textSearchID.setColumns(10);
		textSearchID.setBounds(158, 199, 140, 20);
		contentPane.add(textSearchID);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search();
			}
		});
		
		btnSearch.setBounds(308, 196, 89, 23);
		contentPane.add(btnSearch);
		
		lblProductManagements = new JLabel("จัดการสินค้าในร้าน");
		lblProductManagements.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductManagements.setFont(new Font("Angsana New", Font.BOLD, 28));
		lblProductManagements.setBounds(239, 11, 225, 44);
		contentPane.add(lblProductManagements);
		
		btnBack = new JButton("");
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/back.png")));
		btnBack.setBounds(20, 11, 46, 33);
		contentPane.add(btnBack);
		
		btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
		btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBGcolor.setBounds(551, 11, 133, 23);
		contentPane.add(btnBGcolor);
		
		lblProductId = new JLabel("Product ID : ");
		lblProductId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductId.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductId.setBounds(20, 85, 128, 19);
		contentPane.add(lblProductId);
		
		lblPrice = new JLabel("Price : ");
		lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPrice.setBounds(308, 85, 128, 19);
		contentPane.add(lblPrice);
		
		textPrice = new JTextField();
		textPrice.setColumns(10);
		textPrice.setBounds(446, 86, 140, 20);
		contentPane.add(textPrice);
		
		lblProductName = new JLabel("Product Name : ");
		lblProductName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductName.setBounds(20, 115, 128, 19);
		contentPane.add(lblProductName);
		
		lblName = new JLabel("None");
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(158, 115, 177, 19);
		contentPane.add(lblName);
		
		lblID = new JLabel("None");
		lblID.setHorizontalAlignment(SwingConstants.LEFT);
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblID.setBounds(158, 85, 177, 19);
		contentPane.add(lblID);
		
		btnSell = new JButton("Sell");
		btnSell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sell();
			}
		});
		btnSell.setBounds(496, 158, 89, 23);
		contentPane.add(btnSell);
		
		txtSell = new JTextField();
		txtSell.setHorizontalAlignment(SwingConstants.CENTER);
		txtSell.setColumns(10);
		txtSell.setBounds(363, 159, 123, 20);
		contentPane.add(txtSell);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnBGcolor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Background Color", contentPane.getBackground());
                if (newColor != null) {
                    contentPane.setBackground(newColor);
                }
            }
        });
		
		lblProductName_1 = new JLabel("กรอกจำนวนที่จะขาย : ");
		lblProductName_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductName_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductName_1.setBounds(173, 160, 180, 19);
		contentPane.add(lblProductName_1);
		textSearchID.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			Search();
        		}
        	}
        });
		textSearchID.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			textSearchID.setText("");
        		}
        	}
		});
	}
	public void loadData() {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(url, "root", "");

	        String sql = "SELECT p.ProductID, p.ProductName, ptm.ProductTypeName, p.Price, p.Amounts "
	                   + "FROM productmanagements p "
	                   + "JOIN producttypemanagements ptm ON p.ProductTypeID = ptm.ProductTypeID";

	        Statement sm = conn.createStatement();
	        ResultSet rs = sm.executeQuery(sql);
	        model.setRowCount(0);

	        while (rs.next()) {
	            String rows[] = {
	                rs.getString("ProductID"),
	                rs.getString("ProductName"),
	                rs.getString("ProductTypeName"),
	                rs.getString("Price"),
	                rs.getString("Amounts")
	            };
	            model.addRow(rows);
	        }
	        rs.close();
	        sm.close();
	    } catch (ClassNotFoundException | SQLException e1) {
	        e1.printStackTrace();
	    }
	}
	public void Delete() {
		try {
			conn = DriverManager.getConnection(url,"root","");
			String sql = "delete from productmanagements where ProductID  = ?";
			PreparedStatement myStmt = conn.prepareStatement(sql);
			myStmt.setString(1, lblID.getText());
			if(JOptionPane.showConfirmDialog(null,"Are you sure?","Confirmation",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				myStmt.executeUpdate();
			}
			loadData();
			myStmt.close();
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	public void Update() {
		try {
			conn = DriverManager.getConnection(url,"root","");
			String sql = "update productmanagements set Price = ? where ProductID = ?";
			PreparedStatement myStmt = conn.prepareStatement(sql);
			int row = table.getSelectedRow();
			myStmt.setString(1,textPrice.getText());
			myStmt.setString(2,lblID.getText());
	        if(myStmt.executeUpdate() > 0) {
	        	JOptionPane.showMessageDialog(null, "Update Completed!!");
	        }
	        lblID.setText("None");
	        lblName.setText("None");
	        textPrice.setText("");
	        loadData();
	        myStmt.close();
	        conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	public void Sell() {
	    try {
	        String sellAmountText = txtSell.getText().trim();
	        if (sellAmountText.isEmpty() || !sellAmountText.matches("\\d+")) {
	            JOptionPane.showMessageDialog(null, "Please enter a valid amount to sell.");
	            return;
	        }
	        int sellAmount = Integer.parseInt(sellAmountText);

	        if (sellAmount <= 0) {
	            JOptionPane.showMessageDialog(null, "Please enter a positive amount.");
	            return;
	        }

	        conn = DriverManager.getConnection(url, "root", "");
	        String selectSQL = "SELECT Amounts FROM productmanagements WHERE ProductID = ?";
	        PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
	        selectStmt.setString(1, lblID.getText());
	        ResultSet rs = selectStmt.executeQuery();

	        if (rs.next()) {
	            int currentAmount = rs.getInt("Amounts");
	            if (currentAmount < sellAmount) {
	                JOptionPane.showMessageDialog(null, "Not enough stock to sell.");
	                return;
	            }

	            int remainingAmount = currentAmount - sellAmount;
	            String updateSQL = "UPDATE productmanagements SET Amounts = ? WHERE ProductID = ?";
	            PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
	            updateStmt.setInt(1, remainingAmount);
	            updateStmt.setString(2, lblID.getText());
	            updateStmt.executeUpdate();

	            if (remainingAmount <= 0) {
	                String deleteSQL = "DELETE FROM productmanagements WHERE ProductID = ?";
	                PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
	                deleteStmt.setString(1, lblID.getText());
	                deleteStmt.executeUpdate();
	                JOptionPane.showMessageDialog(null, "Product has been sold out and removed.");
	            } else {
	                JOptionPane.showMessageDialog(null, "Product sold. Remaining stock: " + remainingAmount);
	            }
	            loadData();
	            txtSell.setText("");

	        }

	        rs.close();
	        selectStmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    public void Search() {
        String searchID = textSearchID.getText().trim();
        String sql = "SELECT p.ProductID, p.ProductName, ptm.ProductTypeName, p.Price, p.Amounts "
                + "FROM productmanagements p "
                + "JOIN producttypemanagements ptm ON p.ProductTypeID = ptm.ProductTypeID "
                + "WHERE p.ProductID = ?";
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Product ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model.setRowCount(0);
            if (rs.next()) {
                String[] row = {
    	                rs.getString("ProductID"),
    	                rs.getString("ProductName"),
    	                rs.getString("ProductTypeName"),
    	                rs.getString("Price"),
    	                rs.getString("Amounts")
                };
                model.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No product found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
