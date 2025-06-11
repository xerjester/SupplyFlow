import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class revireturnFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	DefaultTableModel model;
	private JTable table;
	private JTextField textID;
	String url = "jdbc:mysql://localhost/rrrdb";
	Connection conn;
	private JTextField textOrderSearch;

	public revireturnFrame() {
		setTitle("Receive And Return");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				loadData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 838, 417);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 89, 802, 186);
		contentPane.add(scrollPane);
		model = new DefaultTableModel();
		
		table = new JTable();
		model.setColumnIdentifiers(new String[]{"OrderID", "ProductName", "OrderDate", "Amounts", "TotalPrice", "Supplier"});
		scrollPane.setViewportView(table);
		table.setModel(model);
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				table.getValueAt(row, 0).toString();
				textID.setText(table.getValueAt(row, 0).toString());
        	}
        });
		
		JLabel lblProductManagements = new JLabel("รับ - คืนสินค้า");
		lblProductManagements.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductManagements.setFont(new Font("Angsana New", Font.BOLD, 28));
		lblProductManagements.setBounds(299, 11, 225, 44);
		contentPane.add(lblProductManagements);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/back.png")));
		btnBack.setBounds(10, 11, 46, 33);
		contentPane.add(btnBack);
		
		JButton btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
        btnBGcolor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose Background Color", contentPane.getBackground());
                if (newColor != null) {
                    contentPane.setBackground(newColor);
                }
            }
        });
		btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBGcolor.setBounds(679, 21, 133, 23);
		contentPane.add(btnBGcolor);
		
		JButton btnReceive = new JButton("Receive Product");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				receiveProduct();
			}
		});
		btnReceive.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnReceive.setBounds(10, 320, 126, 44);
		contentPane.add(btnReceive);
		
		JButton btnReturn = new JButton("Return Product");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnProduct();
			}
		});
		btnReturn.setBounds(686, 320, 126, 44);
		contentPane.add(btnReturn);
		
		textID = new JTextField();
		textID.setBounds(98, 286, 113, 23);
		contentPane.add(textID);
		textID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("OrderID : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 286, 87, 23);
		contentPane.add(lblNewLabel);
		
		JLabel lblOrderSearch = new JLabel("Order Search : ");
		lblOrderSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrderSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOrderSearch.setBounds(10, 57, 128, 19);
		contentPane.add(lblOrderSearch);
		
		textOrderSearch = new JTextField();
		textOrderSearch.setText("Enter Order ID");
		textOrderSearch.setColumns(10);
		textOrderSearch.setBounds(148, 58, 140, 20);
		contentPane.add(textOrderSearch);
		textOrderSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			Search2();
        		}
        	}
        });
		textOrderSearch.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			textOrderSearch.setText("");
        		}
        	}
		});
		
		JButton btnSearch2 = new JButton("Search");
		btnSearch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search2();
			}
		});
		btnSearch2.setBounds(300, 55, 89, 23);
		contentPane.add(btnSearch2);
	}
	public void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            String sql = "SELECT o.OrderID, o.SProductID, o.OrderDate, o.PAmounts, o.TotalPrice, spm.SProductName, sm.name AS SupplierName " +
                    "FROM ordersupplier o " +
                    "JOIN supplierproductmanagements spm ON o.SProductID = spm.SProductID " +
                    "JOIN suppliermanagements sm ON o.ID = sm.ID";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()) {
                String rows[] = {
                		rs.getString("OrderID"),
                		rs.getString("SProductName"),
                		rs.getString("OrderDate"),
                		rs.getString("PAmounts"),
                		rs.getString("TotalPrice"),
                		rs.getString("SupplierName")
                        };
                model.addRow(rows);
            }
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
	}
	public void receiveProduct() {
	    if (textID.getText().trim().isEmpty()) {
	        System.out.println("Please choose Order");
	        return;
	    }

	    String orderID = textID.getText().trim();
	    String selectSQL = "SELECT os.SProductID, spm.SProductName, spm.Price, spm.ProductTypeID, os.PAmounts " +
	                       "FROM ordersupplier os " +
	                       "JOIN supplierproductmanagements spm ON os.SProductID = spm.SProductID " +
	                       "WHERE os.OrderID = ?";
	    String checkProductSQL = "SELECT Amounts FROM productmanagements WHERE ProductName = ?";
	    String updateProductSQL = "UPDATE productmanagements SET Amounts = Amounts + ? WHERE ProductName = ?";
	    String insertProductSQL = "INSERT INTO productmanagements (ProductName, Price, ProductTypeID, Amounts) VALUES (?, ?, ?, ?)";
	    String insertReceiveSQL = "INSERT INTO receiveproduct (OrderID) VALUES (?)";
	    String deleteSQL = "DELETE FROM ordersupplier WHERE OrderID = ?";

	    try (Connection conn = DriverManager.getConnection(url, "root", "");
	         PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
	         PreparedStatement checkStmt = conn.prepareStatement(checkProductSQL);
	         PreparedStatement updateStmt = conn.prepareStatement(updateProductSQL);
	         PreparedStatement insertProductStmt = conn.prepareStatement(insertProductSQL);
	         PreparedStatement insertReceiveStmt = conn.prepareStatement(insertReceiveSQL);
	         PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {

	        selectStmt.setString(1, orderID);
	        ResultSet rs = selectStmt.executeQuery();

	        while (rs.next()) {
	            String productName = rs.getString("SProductName");
	            String productTypeID = rs.getString("ProductTypeID");
	            double price = rs.getDouble("Price");
	            int amounts = rs.getInt("PAmounts");

	            checkStmt.setString(1, productName);
	            ResultSet checkProductRs = checkStmt.executeQuery();

	            if (checkProductRs.next()) {
	                int existingAmount = checkProductRs.getInt("Amounts");
	                int newAmount = existingAmount + amounts;

	                updateStmt.setInt(1, amounts);
	                updateStmt.setString(2, productName);
	                updateStmt.executeUpdate();
	            } else {
	                insertProductStmt.setString(1, productName);
	                insertProductStmt.setDouble(2, price);
	                insertProductStmt.setString(3, productTypeID);
	                insertProductStmt.setInt(4, amounts);
	                insertProductStmt.executeUpdate();
	            }
	            insertReceiveStmt.setString(1, orderID);
	            insertReceiveStmt.executeUpdate();

	            deleteStmt.setString(1, orderID);
	            deleteStmt.executeUpdate();
	        }

	        loadData();
	        System.out.println("รับสินค้าและบันทึกข้อมูลสำเร็จ");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void returnProduct() {
	    if (textID.getText().trim().isEmpty()) {
	        System.out.println("Please choose Order");
	        return;
	    }

	    String orderID = textID.getText().trim();
	    String selectSQL = "SELECT os.SProductID, spm.SProductName, spm.Price, spm.ProductTypeID, os.PAmounts " +
	                       "FROM ordersupplier os " +
	                       "JOIN supplierproductmanagements spm ON os.SProductID = spm.SProductID " +
	                       "WHERE os.OrderID = ?";
	    String insertReturnSQL = "INSERT INTO returnproduct (OrderID) VALUES (?)";
	    String deleteSQL = "DELETE FROM ordersupplier WHERE OrderID = ?";

	    String checkProductTypeSQL = "SELECT COUNT(*) FROM producttypemanagements WHERE ProductTypeID = ?";

	    try (Connection conn = DriverManager.getConnection(url, "root", "");
	         PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
	         PreparedStatement checkStmt = conn.prepareStatement(checkProductTypeSQL)) {

	        selectStmt.setString(1, orderID);
	        ResultSet rs = selectStmt.executeQuery();

	        if (rs.next()) {
	            String productTypeID = rs.getString("ProductTypeID");

	            checkStmt.setString(1, productTypeID);
	            ResultSet checkRs = checkStmt.executeQuery();
	            checkRs.next();
	            int count = checkRs.getInt(1);

	            if (count == 0) {
	                System.out.println("Error: ProductTypeID " + productTypeID + "producttypemanagements not found!");
	                return;
	            }

	            try (PreparedStatement insertReturnStmt = conn.prepareStatement(insertReturnSQL);
	                 PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL)) {

	                insertReturnStmt.setString(1, orderID);
	                insertReturnStmt.executeUpdate();

	                deleteStmt.setString(1, orderID);
	                deleteStmt.executeUpdate();
	                loadData();
	                System.out.println("คืนสินค้าและบันทึกข้อมูลสำเร็จ");
	            }

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
    public void Search2() {
        String searchID = textOrderSearch.getText().trim();
        String sql = "SELECT o.OrderID, o.SProductID, o.OrderDate, o.PAmounts, o.TotalPrice, spm.SProductName, sm.name AS SupplierName " +
                     "FROM ordersupplier o " +
                     "JOIN supplierproductmanagements spm ON o.SProductID = spm.SProductID " +
                     "JOIN suppliermanagements sm ON o.ID = sm.ID " +
                     "WHERE o.OrderID = ?";

        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter an Order ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();

            model.setRowCount(0);

            if (rs.next()) {
                String[] row = {
                    rs.getString("OrderID"),
                    rs.getString("SProductID"),
                    rs.getString("OrderDate"),
                    rs.getString("PAmounts"),
                    rs.getString("TotalPrice"),
                    rs.getString("SupplierName") 
                };
                model.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No order found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
	    

