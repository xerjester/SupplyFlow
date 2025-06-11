import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import com.mysql.cj.jdbc.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class sproductFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JLabel lblProductManagements;
    private String url = "jdbc:mysql://localhost/rrrdb";
    private Connection conn;
    private JTextField textSpID;
    private JTextField textSpName;
    private JTextField textPrice;
    private JTextField txtSearch;
    JComboBox comboBox;

    public sproductFrame() {
    	setLocationRelativeTo(null);
        setTitle("SupplierProducts");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 710, 469);

        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 0, 0));
        contentPane.setBackground(new Color(202, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel lblPrice = new JLabel("Price : ");
        comboBox = new JComboBox();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"SProductID", "SProductName", "ProductTypeName", "Price"});
        textPrice = new JTextField();
        textPrice.setText((String) null);
        textPrice.setColumns(10);
        textPrice.setBounds(544, 67, 140, 20);
        contentPane.add(textPrice);
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				table.getValueAt(row, 0).toString();
				textSpID.setText(table.getValueAt(row, 0).toString());
				textSpName.setText(table.getValueAt(row, 1).toString());
				comboBox.setSelectedItem(table.getValueAt(row, 2).toString());
				textPrice.setText(table.getValueAt(row, 3).toString());
        	}
        });
        table.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 127) {
        			deleteData();
        		}
        	}
        });
		JButton btnImport = new JButton("Import CSV");
		btnImport.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnImport.setForeground(new Color(0, 128, 192));
		btnImport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				importFromCSV();
			}
		});
		btnImport.setBounds(496, 198, 89, 23);
		contentPane.add(btnImport);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 233, 674, 186);
        contentPane.add(scrollPane);
        lblPrice.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblPrice.setBounds(355, 66, 179, 19);
        contentPane.add(lblPrice);
        contentPane.add(textPrice);
        
        JLabel lblProductSearch = new JLabel("Product Search : ");
        lblProductSearch.setHorizontalAlignment(SwingConstants.RIGHT);
        lblProductSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblProductSearch.setBounds(10, 201, 128, 19);
        contentPane.add(lblProductSearch);
        txtSearch = new JTextField();
        txtSearch.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int code = e.getKeyCode();
        		if(code == 10) {
        			Search();
        		}
        	}
        });
        txtSearch.addMouseListener(new MouseAdapter() {
        	int count = 0;
        	@Override
        	public void mousePressed(MouseEvent e) {
        		count++;
        		if(count == 1) {
        			txtSearch.setText("");
        		}
        	}
		});
		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(0, 128, 0));
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnSave.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            conn = DriverManager.getConnection(url, "root", "");

		            DefaultTableModel model = (DefaultTableModel) table.getModel();
		            for (int i = 0; i < model.getRowCount(); i++) {
		                String spID = model.getValueAt(i, 0).toString().trim();
		                String spName = model.getValueAt(i, 1).toString().trim();
		                String productTypeName = model.getValueAt(i, 2).toString().trim();
		                String priceStr = model.getValueAt(i, 3).toString().trim();

		                if (spID.isEmpty() || spName.isEmpty() || productTypeName.isEmpty() || priceStr.isEmpty()) {
		                    continue;
		                }

		                double price = Double.parseDouble(priceStr);
		                String productTypeID = null;
		                String query = "SELECT ProductTypeID FROM producttypemanagements WHERE LOWER(TRIM(ProductTypeName)) = LOWER(TRIM(?))";
		                try (PreparedStatement stmt = conn.prepareStatement(query)) {
		                    stmt.setString(1, productTypeName);
		                    ResultSet rs = stmt.executeQuery();
		                    if (rs.next()) {
		                        productTypeID = rs.getString("ProductTypeID");
		                    } else {
		                        JOptionPane.showMessageDialog(null, "ProductType '" + productTypeName + "' not found!", "Error", JOptionPane.ERROR_MESSAGE);
		                        continue;
		                    }
		                }
		                String sql = "INSERT INTO supplierproductmanagements (SProductID, SProductName, ProductTypeID, Price) " +
		                             "VALUES (?, ?, ?, ?) " +
		                             "ON DUPLICATE KEY UPDATE SProductName = ?, ProductTypeID = ?, Price = ?";
		                try (PreparedStatement myStmt = conn.prepareStatement(sql)) {
		                    myStmt.setString(1, spID);
		                    myStmt.setString(2, spName);
		                    myStmt.setString(3, productTypeID);
		                    myStmt.setDouble(4, price);
		                    myStmt.setString(5, spName);
		                    myStmt.setString(6, productTypeID);
		                    myStmt.setDouble(7, price);
		                    myStmt.executeUpdate();
		                }
		            }

		            JOptionPane.showMessageDialog(null, "Save Complete!");
		            conn.close();
		            loadData();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (NumberFormatException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Invalid Price Format!", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});


		btnSave.setBounds(429, 199, 57, 23);
		contentPane.add(btnSave);
        txtSearch.setText("Enter Product ID");
        txtSearch.setColumns(10);
        txtSearch.setBounds(148, 202, 140, 20);
        contentPane.add(txtSearch);

        lblProductManagements = new JLabel("จัดการสินค้าในซัพพลายเออร์");
        lblProductManagements.setFont(new Font("Angsana New", Font.BOLD, 28));
        lblProductManagements.setBounds(237, 11, 225, 44);
        contentPane.add(lblProductManagements);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(496, 161, 89, 23);
        contentPane.add(btnDelete);
        JButton btnBack = new JButton("");
        btnBack.setIcon(new ImageIcon(getClass().getClassLoader().getResource("resources/img/back.png")));
        btnBack.setBounds(10, 11, 46, 33);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnBack);
        
        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Search();
            }
        });

        btnSearch.setBounds(298, 199, 89, 23);
        contentPane.add(btnSearch);
        
        JButton btnInsert = new JButton("Insert");
        btnInsert.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		insertData();
        	}
        });
        btnInsert.setBounds(10, 161, 89, 23);
        contentPane.add(btnInsert);
        
        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = DriverManager.getConnection(url, "root", "");
                    String sql = "UPDATE supplierproductmanagements SET SProductID = ?, SProductName = ?, ProductTypeID = ?, Price = ? WHERE SProductID = ?";
                    PreparedStatement myStmt = conn.prepareStatement(sql);
                    int row = table.getSelectedRow();
                    myStmt.setString(1, textSpID.getText());
                    myStmt.setString(2, textSpName.getText());
                    String selectedTypeName = comboBox.getSelectedItem().toString();

                    String getProductTypeIDQuery = "SELECT ProductTypeID FROM producttypemanagements WHERE ProductTypeName = ?";
                    PreparedStatement stmt = conn.prepareStatement(getProductTypeIDQuery);
                    stmt.setString(1, selectedTypeName);
                    ResultSet rs = stmt.executeQuery();

                    String productTypeID = "";
                    if (rs.next()) {
                        productTypeID = rs.getString("ProductTypeID");
                    }
                    stmt.close();
                    rs.close();
                    myStmt.setString(3, productTypeID);
                    myStmt.setString(4, textPrice.getText());
                    myStmt.setString(5, textSpID.getText());
                    if (myStmt.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Update Completed!!");
                    }

                    textSpID.setText("");
                    textSpName.setText("");
                    comboBox.setSelectedItem("None");
                    textPrice.setText("");

                    loadData();

                    myStmt.close();
                    conn.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        btnUpdate.setBounds(109, 161, 89, 23);
        contentPane.add(btnUpdate);
        
        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
		        textSpID.setText("");
		        textSpName.setText("");
		        comboBox.setSelectedItem("None");
		        textPrice.setText("");
        	}
        });
        btnDelete.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		deleteData();
        	}
        });
        btnClear.setBounds(595, 161, 89, 23);
        contentPane.add(btnClear);
        
        textSpID = new JTextField();
        textSpID.setBounds(202, 67, 140, 20);
        contentPane.add(textSpID);
        textSpID.setColumns(10);
        
        JLabel lblSpID = new JLabel("Supplier Product ID : ");
        lblSpID.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSpID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSpID.setBounds(10, 67, 179, 14);
        contentPane.add(lblSpID);
        
        JLabel lblSupplierProductName = new JLabel("Supplier Product Name : ");
        lblSupplierProductName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSupplierProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSupplierProductName.setBounds(10, 92, 179, 14);
        contentPane.add(lblSupplierProductName);
        
        textSpName = new JTextField();
        textSpName.setColumns(10);
        textSpName.setBounds(202, 91, 140, 20);
        contentPane.add(textSpName);
        
        JLabel lblProductTypeId = new JLabel("Product Type  : ");
        lblProductTypeId.setHorizontalAlignment(SwingConstants.RIGHT);
        lblProductTypeId.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblProductTypeId.setBounds(10, 117, 179, 19);
        contentPane.add(lblProductTypeId);
        
        JButton btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
        btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBGcolor.setBounds(552, 18, 133, 23);
        contentPane.add(btnBGcolor);
        comboBox.setBounds(202, 117, 96, 22);
        contentPane.add(comboBox);
        JButton btnExport = new JButton("Export CSV");
        btnExport.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnExport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		exportToCSV();
        	}
        });
        btnExport.setBounds(595, 198, 89, 23);
        contentPane.add(btnExport);
        JButton btnShowAll = new JButton("Show All");
        btnShowAll.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		loadData();
        	}
        });
        btnShowAll.setBounds(398, 161, 89, 23);
        contentPane.add(btnShowAll);
        textSpID.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textSpName.requestFocus();
            }
        });

        textSpName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	comboBox.requestFocus();
            }
        });

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	textPrice.requestFocus();
            }
        });
        textPrice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertData();
			}
		});
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                textSpID.setText(table.getValueAt(selectedRow, 0).toString());
                textSpName.setText(table.getValueAt(selectedRow, 1).toString());
                textPrice.setText(table.getValueAt(selectedRow, 3).toString());
				comboBox.setSelectedItem(table.getValueAt(selectedRow, 2).toString());
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


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                loadData();
                loadProductTypes();
            }
        });
    }
    
    public void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT spm.SProductID, spm.SProductName, ptm.ProductTypeName, spm.Price "
                         + "FROM supplierproductmanagements spm "
                         + "JOIN producttypemanagements ptm ON spm.ProductTypeID = ptm.ProductTypeID";
            
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model.setRowCount(0);

            while (rs.next()) {
                String rows[] = {
                        rs.getString("SProductID"),
                        rs.getString("SProductName"),
                        rs.getString("ProductTypeName"),
                        rs.getString("Price")
                };
                model.addRow(rows);
            }
            
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void deleteData() {
		try {
			conn = DriverManager.getConnection(url,"root","");
			String sql = "delete from supplierproductmanagements where SProductID  = ?";
			PreparedStatement myStmt = conn.prepareStatement(sql);
			myStmt.setString(1, textSpID.getText());
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
    public void insertData() {
        String spID = textSpID.getText().trim();
        String spName = textSpName.getText().trim();
        String ptPrice = textPrice.getText().trim();
        String selectedType = comboBox.getSelectedItem() != null ? comboBox.getSelectedItem().toString() : "None";

        if (spID.isEmpty() || spName.isEmpty() || selectedType.equals("None") || ptPrice.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter your information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");

            String checkSql = "SELECT COUNT(*) FROM supplierproductmanagements WHERE SProductID = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, spID);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "This ID is duplicate,Please enter another!");
                rs.close();
                checkStmt.close();
                conn.close();
                return;
            }
            rs.close();
            checkStmt.close();

            String productTypeID = null;

            String productTypeSql = "SELECT ProductTypeID FROM producttypemanagements WHERE ProductTypeName = ?";
            PreparedStatement productTypeStmt = conn.prepareStatement(productTypeSql);
            productTypeStmt.setString(1, selectedType);
            ResultSet productTypeRs = productTypeStmt.executeQuery();

            if (productTypeRs.next()) {
                productTypeID = productTypeRs.getString("ProductTypeID");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Product Type. Please select a valid type.", "Error", JOptionPane.ERROR_MESSAGE);
                productTypeRs.close();
                productTypeStmt.close();
                conn.close();
                return;
            }
            productTypeRs.close();
            productTypeStmt.close();

            String sql = "INSERT INTO supplierproductmanagements (SProductID, SProductName, ProductTypeID, Price) VALUES (?, ?, ?, ?)";
            PreparedStatement myStmt = conn.prepareStatement(sql);
            myStmt.setString(1, spID);
            myStmt.setString(2, spName);
            myStmt.setString(3, productTypeID);
            myStmt.setString(4, ptPrice);

            int result = myStmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Insert Completed!!");
            } else {
                JOptionPane.showMessageDialog(null, "Insert Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            textSpID.setText("");
            textSpName.setText("");
            comboBox.setSelectedItem("None");
            textPrice.setText("");
            loadData();
            myStmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void Search() {
        String searchID = txtSearch.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Product ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM supplierproductmanagements WHERE SProductID = ?")) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model.setRowCount(0);
            if (rs.next()) {
                String[] row = {
                    rs.getString("SProductID"),
                    rs.getString("SProductName"),
                    rs.getString("ProductTypeID"),
                    rs.getString("Price")
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
    private void exportToCSV() {
        try (FileWriter writer = new FileWriter("products.csv")) {
            for (int i = 0; i < table.getRowCount(); i++) {
                writer.append(table.getValueAt(i, 0).toString()).append(",");
                writer.append(table.getValueAt(i, 1).toString()).append(",");
                writer.append(table.getValueAt(i, 2).toString()).append(",");
                writer.append(table.getValueAt(i, 3).toString()).append("\n");
            }
            JOptionPane.showMessageDialog(null, "CSV file exported successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void importFromCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    model.addRow(data);
                }
                JOptionPane.showMessageDialog(null, "CSV file imported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public void loadProductTypes() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");

            String sql = "SELECT ProductTypeName FROM producttypemanagements";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);

            comboBox.removeAllItems();
            comboBox.addItem("None");

            while (rs.next()) {
                String productTypeName = rs.getString("ProductTypeName");
                comboBox.addItem(productTypeName);
            }
            rs.close();
            sm.close();
        } catch (ClassNotFoundException | SQLException e1) {
            e1.printStackTrace();
        }
    }


}
