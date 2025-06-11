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

public class typeManage extends JFrame {
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
    private JTextField txtSearch;

    public typeManage() {
    	setLocationRelativeTo(null);
        setTitle("ProductsType");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 710, 469);

        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 0, 0));
        contentPane.setBackground(new Color(202, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ProductTypeID", "ProductTypeName"});
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
				int row = table.getSelectedRow();
				table.getValueAt(row, 0).toString();
				textSpID.setText(table.getValueAt(row, 0).toString());
				textSpName.setText(table.getValueAt(row, 1).toString());
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
		btnImport.setFont(new Font("Tahoma", Font.PLAIN, 10));
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
        
        JLabel lblProductSearch = new JLabel("Type Search : ");
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
		                String spID = model.getValueAt(i, 0).toString();
		                String spName = model.getValueAt(i, 1).toString();

		                if (spID.isEmpty() || spName.isEmpty()) {
		                    continue;
		                }

		                String sql = "INSERT INTO producttypemanagements (ProductTypeID, ProductTypeName) " +
		                             "VALUES (?, ?) " +
		                             "ON DUPLICATE KEY UPDATE ProductTypeName = ?";
		                PreparedStatement myStmt = conn.prepareStatement(sql);

		                myStmt.setString(1, spID);
		                myStmt.setString(2, spName);
		                myStmt.setString(3, spName);
		                myStmt.executeUpdate();
		            }
		            JOptionPane.showMessageDialog(null, "Save Complete!");

		            conn.close();
		            loadData();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error!");
		        }
		    }
		});

		btnSave.setBounds(429, 199, 57, 23);
		contentPane.add(btnSave);
        txtSearch.setText("Enter Type ID");
        txtSearch.setColumns(10);
        txtSearch.setBounds(148, 202, 140, 20);
        contentPane.add(txtSearch);

        lblProductManagements = new JLabel("จัดการประเภทสินค้า");
        lblProductManagements.setHorizontalAlignment(SwingConstants.CENTER);
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
					conn = DriverManager.getConnection(url,"root","");
					String sql = "update producttypemanagements set ProductTypeID = ?, ProductTypeName = ? where ProductTypeID = ?";
					PreparedStatement myStmt = conn.prepareStatement(sql);
					int row = table.getSelectedRow();
					myStmt.setString(1,textSpID.getText());
					myStmt.setString(2,textSpName.getText());
					myStmt.setString(3,textSpID.getText());
			        if(myStmt.executeUpdate() > 0) {
			        	JOptionPane.showMessageDialog(null, "Update Completed!!");
			        }
			        textSpID.setText("");
			        textSpName.setText("");
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
        textSpID.setBounds(122, 88, 140, 20);
        contentPane.add(textSpID);
        textSpID.setColumns(10);
        
        JLabel lblSpID = new JLabel("Type ID : ");
        lblSpID.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSpID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSpID.setBounds(-70, 88, 179, 14);
        contentPane.add(lblSpID);
        
        JLabel lblSupplierProductName = new JLabel("Type Name : ");
        lblSupplierProductName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSupplierProductName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSupplierProductName.setBounds(-70, 113, 179, 14);
        contentPane.add(lblSupplierProductName);
        
        textSpName = new JTextField();
        textSpName.setColumns(10);
        textSpName.setBounds(122, 112, 140, 20);
        contentPane.add(textSpName);
        
        JButton btnBGcolor = new JButton("เปลี่ยนสีพื้นหลัง");
        btnBGcolor.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnBGcolor.setBounds(552, 18, 133, 23);
        contentPane.add(btnBGcolor);
        JButton btnExport = new JButton("Export CSV");
        btnExport.setFont(new Font("Tahoma", Font.PLAIN, 10));
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
            	insertData();
            }
        });
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int selectedRow = table.getSelectedRow();
                textSpID.setText(table.getValueAt(selectedRow, 0).toString());
                textSpName.setText(table.getValueAt(selectedRow, 1).toString());
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
            }
        });
    }
    
    public void loadData() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            String sql = "SELECT * FROM producttypemanagements";
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery(sql);
            model.setRowCount(0);
            while (rs.next()) {
                String rows[] = {
                        rs.getString("ProductTypeID"),
                        rs.getString("ProductTypeName"),
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
			String sql = "delete from producttypemanagements where ProductTypeID  = ?";
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
        if (spID.isEmpty() || spName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter your information!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,"root","");
			
	        String checkSql = "SELECT COUNT(*) FROM producttypemanagements WHERE ProductTypeID = ?";
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
			
			String sql = "insert into producttypemanagements values(?,?)";
	        PreparedStatement myStmt = conn.prepareStatement(sql);
	        myStmt.setString(1, textSpID.getText());
	        myStmt.setString(2, textSpName.getText());
	        if(myStmt.executeUpdate() > 0) {
	        	JOptionPane.showMessageDialog(null, "Insert Completed!!");
	        }
	        textSpID.setText("");
	        textSpName.setText("");
	        loadData();
	        myStmt.close();
	        conn.close();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}
    public void Search() {
        String searchID = txtSearch.getText().trim();
        if (searchID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a Product Type ID to search!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(url, "root", "");
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM producttypemanagements WHERE ProductTypeID = ?")) {
            
            ps.setString(1, searchID);
            ResultSet rs = ps.executeQuery();
            
            model.setRowCount(0);
            if (rs.next()) {
                String[] row = {
                    rs.getString("ProductTypeID"),
                    rs.getString("ProductTypeName")
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
        try (FileWriter writer = new FileWriter("productsType.csv")) {
            for (int i = 0; i < table.getRowCount(); i++) {
                writer.append(table.getValueAt(i, 0).toString()).append(",");
                writer.append(table.getValueAt(i, 1).toString()).append(",");
                writer.append("\n");
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

                    boolean isDuplicate = false;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        if (table.getValueAt(i, 0).toString().equals(data[0])) {
                            isDuplicate = true;
                            break;
                        }
                    }
                    if (!isDuplicate) {
                        model.addRow(data);
                    }
                }
                JOptionPane.showMessageDialog(null, "CSV file imported successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
