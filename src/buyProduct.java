import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.ImageIcon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class buyProduct extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table1;
	private JTable table2;
	private JTextField textID;
	private JTextField textAmounts;
	private String url = "jdbc:mysql://localhost/rrrdb";
	private Connection conn;
	DefaultTableModel model1,model2;
	JComboBox comboBox;
	JTextField textSearch;
	private JTextField textOrderSearch;
	
	public buyProduct() {
		setTitle("Order");
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				loadSupplierData();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 804, 557);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
        model1 = new DefaultTableModel();
        model1.setColumnIdentifiers(new String[]{"SProductID", "SProductName", "ProductTypeID", "Price"});
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 103, 768, 106);
		contentPane.add(scrollPane);
		
		table1 = new JTable();
		scrollPane.setViewportView(table1);
		table1.setModel(model1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 350, 768, 157);
		contentPane.add(scrollPane_1);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"None", "Sermthai", "MSU", "Thatphanom", "Bangkok"}));
		comboBox.setBounds(353, 239, 97, 22);
		contentPane.add(comboBox);
		
		table2 = new JTable();
		scrollPane_1.setViewportView(table2);
        model2 = new DefaultTableModel();
        model2.setColumnIdentifiers(new String[]{"OrderID", "ProductName", "OrderDate", "Amounts", "TotalPrice", "Supplier"});
        table2.setModel(model2);
        TableColumnModel columnModel = table2.getColumnModel();

        for (int i = 0; i < table2.getColumnCount(); i++) {
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
		textID = new JTextField();
		textID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAmounts.requestFocus();
			}
		});
		textID.setBounds(122, 240, 97, 20);
		contentPane.add(textID);
		textID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("SProductID : ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(-2, 240, 114, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblAmounts = new JLabel("Amounts : ");
		lblAmounts.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmounts.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAmounts.setBounds(-2, 265, 114, 14);
		contentPane.add(lblAmounts);
		
		textAmounts = new JTextField();
		textAmounts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		textAmounts.setColumns(10);
		textAmounts.setBounds(122, 265, 97, 20);
		contentPane.add(textAmounts);
		
		JButton btnBuy = new JButton("Add Order");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert();
			}
		});
		btnBuy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBuy.setBounds(10, 305, 102, 23);
		contentPane.add(btnBuy);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteData();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(122, 305, 89, 23);
		contentPane.add(btnCancel);
		
		JButton btnBack = new JButton("");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/back.png")));
		btnBack.setBounds(10, 11, 46, 33);
		contentPane.add(btnBack);
		
		JLabel lblProductManagements = new JLabel("สั่งของจากซัพพลายเออร์");
		lblProductManagements.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductManagements.setFont(new Font("Angsana New", Font.BOLD, 28));
		lblProductManagements.setBounds(278, 11, 225, 44);
		contentPane.add(lblProductManagements);
		
		JButton btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
		btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBGcolor.setBounds(645, 21, 133, 23);
		contentPane.add(btnBGcolor);
		
		JLabel lblSupplierId = new JLabel("Supplier : ");
		lblSupplierId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSupplierId.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSupplierId.setBounds(229, 240, 114, 14);
		contentPane.add(lblSupplierId);
		
		JLabel lblProductSearch = new JLabel("Product Search : ");
		lblProductSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProductSearch.setBounds(10, 71, 128, 19);
		contentPane.add(lblProductSearch);
		
		textSearch = new JTextField();
		textSearch.setText("Enter Product ID");
		textSearch.setColumns(10);
		textSearch.setBounds(148, 72, 140, 20);
		contentPane.add(textSearch);
		
		textSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			Search1();
        		}
        	}
        });
		textSearch.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			textSearch.setText("");
        		}
        	}
		});
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search1();
			}
		});
		btnSearch.setBounds(300, 69, 89, 23);
		contentPane.add(btnSearch);
		
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData1();
			}
		});
		btnShowAll.setBounds(689, 69, 89, 23);
		contentPane.add(btnShowAll);
		
		JButton btnSearch2 = new JButton("Search");
		btnSearch2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Search2();
			}
		});
		btnSearch2.setBounds(515, 305, 89, 23);
		contentPane.add(btnSearch2);
		
		textOrderSearch = new JTextField();
		textOrderSearch.setText("Enter Order ID");
		textOrderSearch.setColumns(10);
		textOrderSearch.setBounds(363, 308, 140, 20);
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
		
		JLabel lblOrderSearch = new JLabel("Order Search : ");
		lblOrderSearch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOrderSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblOrderSearch.setBounds(225, 307, 128, 19);
		contentPane.add(lblOrderSearch);
		
		JButton btnShowAll2 = new JButton("Show All");
		btnShowAll2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadData2();
			}
		});
		btnShowAll2.setBounds(689, 305, 89, 23);
		contentPane.add(btnShowAll2);
		
        table1.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
				int row = table1.getSelectedRow();
				table1.getValueAt(row, 0).toString();
				textID.setText(table1.getValueAt(row, 0).toString());
        	}
        });
        table2.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 127) {
        			deleteData();
        		}
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
	}
	public void loadData1() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            String sql = "SELECT * FROM supplierproductmanagements";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model1.setRowCount(0);
            while (rs.next()) {
                String rows[] = {
                        rs.getString("SProductID"),
                        rs.getString("SProductName"),
                        rs.getString("ProductTypeID"),
                        rs.getString("Price")
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
            String sql = "SELECT o.OrderID, o.SProductID, o.OrderDate, o.PAmounts, o.TotalPrice, spm.SProductName, sm.name AS SupplierName " +
                    "FROM ordersupplier o " +
                    "JOIN supplierproductmanagements spm ON o.SProductID = spm.SProductID " +
                    "JOIN suppliermanagements sm ON o.ID = sm.ID";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model2.setRowCount(0);
            while (rs.next()) {
                String rows[] = {
                		rs.getString("OrderID"),
                		rs.getString("SProductName"),
                		rs.getString("OrderDate"),
                		rs.getString("PAmounts"),
                		rs.getString("TotalPrice"),
                		rs.getString("SupplierName")
                        };
                model2.addRow(rows);
            }
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
	}
	public void insert() {
	    String spID = textID.getText().trim();
	    String spAmounts = textAmounts.getText().trim();

	    if (spID.isEmpty() || spAmounts.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Please enter the required information!", "Warning", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    try (Connection conn = DriverManager.getConnection(url, "root", "")) {
	        String priceQuery = "SELECT Price FROM supplierproductmanagements WHERE SProductID = ?";
	        try (PreparedStatement priceStmt = conn.prepareStatement(priceQuery)) {
	            priceStmt.setString(1, spID);
	            try (ResultSet rs = priceStmt.executeQuery()) {
	                if (rs.next()) {
	                    double price = rs.getDouble("Price");
	                    int amounts = Integer.parseInt(spAmounts);
	                    double totalPrice = price * amounts;

	                    String selectedSupplier = comboBox.getSelectedItem() != null ? comboBox.getSelectedItem().toString() : "None";
	                    if (selectedSupplier.equals("None")) {
	                        JOptionPane.showMessageDialog(null, "Please select a supplier.", "Warning", JOptionPane.WARNING_MESSAGE);
	                        return;
	                    }

	                    String supplierID = supplierMap.get(selectedSupplier);

	                    String insertQuery = "INSERT INTO OrderSupplier (SProductID, PAmounts, TotalPrice, ID) VALUES (?, ?, ?, ?)";
	                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
	                        insertStmt.setString(1, spID);
	                        insertStmt.setInt(2, amounts);
	                        insertStmt.setDouble(3, totalPrice);
	                        insertStmt.setString(4, supplierID);

	                        if (insertStmt.executeUpdate() > 0) {
	                            JOptionPane.showMessageDialog(null, "Order Inserted Successfully!");
	                        }
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(null, "Product ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        }
	        loadData2();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}

	public void deleteData() {
	    try {
	        int selectedRow = table2.getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(null, "Please select an OrderID to Cancel.", "Warning", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
	        
	        String orderID = table2.getValueAt(selectedRow, 0).toString();
	        conn = DriverManager.getConnection(url, "root", "");

	        String sql = "DELETE FROM ordersupplier WHERE OrderID = ?";
	        PreparedStatement myStmt = conn.prepareStatement(sql);
	        myStmt.setString(1, orderID);

	        if (JOptionPane.showConfirmDialog(null, "Are you sure you want to Cancel?", "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
	            myStmt.executeUpdate();
	        }

	        loadData2();
	        myStmt.close();
	        conn.close();
	        
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	}
    public void Search1() {
        String searchID = textSearch.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Product ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM supplierproductmanagements WHERE SProductID = ?")) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model1.setRowCount(0);
            if (rs.next()) {
                String[] row = {
                    rs.getString("SProductID"),
                    rs.getString("SProductName"),
                    rs.getString("ProductTypeID"),
                    rs.getString("Price")
                };
                model1.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No product found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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

            model2.setRowCount(0);

            if (rs.next()) {
                String[] row = {
                    rs.getString("OrderID"),
                    rs.getString("SProductID"),
                    rs.getString("OrderDate"),
                    rs.getString("PAmounts"),
                    rs.getString("TotalPrice"),
                    rs.getString("SupplierName")
                };
                model2.addRow(row);
            } else {
                JOptionPane.showMessageDialog(null, "No order found with ID: " + searchID, "Search Result", JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    private Map<String, String> supplierMap = new HashMap<>();

    public void loadSupplierData() {
        try (Connection conn = DriverManager.getConnection(url, "root", "");
             Statement sm = conn.createStatement();
             ResultSet rs = sm.executeQuery("SELECT id, name FROM suppliermanagements")) {
            
            comboBox.removeAllItems();
            comboBox.addItem("None");

            while (rs.next()) {
                String supplierID = rs.getString("id");
                String supplierName = rs.getString("name");
                supplierMap.put(supplierName, supplierID);

                comboBox.addItem(supplierName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
