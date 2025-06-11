import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.AlphaComposite;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class mainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	sproductFrame sFrame;
	supplierManage smFrame;
	typeManage tmFrame;
	buyProduct bpFrame;
	revireturnFrame rrFrame;
	historyFrame hFrame;
	productFrame pFrame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			mainFrame frame = new mainFrame();
			public void run() {
				try {
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public mainFrame() {
		setTitle("Main");
		setBackground(new Color(128, 255, 255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 553);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(191, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 78, 896, 78);
		panel.setBackground(new Color(206, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Supplyflow");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(346, 21, 201, 32);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		panel.add(lblNewLabel);
		
		JButton btnProductM = new JButton("จัดการสินค้า");
		btnProductM.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnProductM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pFrame = new productFrame();
				pFrame.setLocationRelativeTo(null);
				setVisible(false);
				pFrame.setVisible(true);
				pFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				pFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
		});
		btnProductM.setBounds(58, 376, 170, 58);
		contentPane.add(btnProductM);
		
		JButton btnTypeM = new JButton("จัดการชนิดสินค้า");
		btnTypeM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tmFrame = new typeManage();
				tmFrame.setLocationRelativeTo(null);
				setVisible(false);
				tmFrame.setVisible(true);
				tmFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				tmFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
		});
		btnTypeM.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTypeM.setBounds(238, 376, 170, 58);
		contentPane.add(btnTypeM);
		
		JButton btnSupplier = new JButton("จัดการซัพพลายเออร์");
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smFrame = new supplierManage();
				smFrame.setLocationRelativeTo(null);
				setVisible(false);
				smFrame.setVisible(true);
				smFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				smFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
		});
		btnSupplier.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnSupplier.setBounds(505, 376, 170, 58);
		contentPane.add(btnSupplier);
        
        JButton btnRevRet = new JButton("รับ + คืนสินค้า");
        btnRevRet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rrFrame = new revireturnFrame();
				rrFrame.setLocationRelativeTo(null);
				setVisible(false);
				rrFrame.setVisible(true);
				rrFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				rrFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
        });
        btnRevRet.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnRevRet.setBounds(58, 445, 171, 58);
        contentPane.add(btnRevRet);
        
        JButton btnRevRet_1 = new JButton("รายการรับ + คืนสินค้า");
        btnRevRet_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
				hFrame = new historyFrame();
				hFrame.setLocationRelativeTo(null);
				setVisible(false);
				hFrame.setVisible(true);
				hFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		        hFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
        	}
        });
        btnRevRet_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnRevRet_1.setBounds(685, 443, 170, 58);
        contentPane.add(btnRevRet_1);
		
		JButton btnSupProducts = new JButton("จัดการซัพพลายเออร์\r\nสินค้า");
		btnSupProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sFrame = new sproductFrame();
				sFrame.setLocationRelativeTo(null);
				setVisible(false);
				sFrame.setVisible(true);
				sFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		        sFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
		});
		btnSupProducts.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSupProducts.setBounds(685, 376, 170, 58);
		contentPane.add(btnSupProducts);
		
		JButton btnOrder = new JButton("สั่งซื้อสินค้าจาก Supplier");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bpFrame = new buyProduct();
				bpFrame.setLocationRelativeTo(null);
				setVisible(false);
				bpFrame.setVisible(true);
				bpFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				bpFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                setVisible(true);
		            }
		        });
			}
		});
		btnOrder.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnOrder.setBounds(350, 448, 213, 52);
		contentPane.add(btnOrder);
		
        JLabel lblimg = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("resources/img/shopimg.jpg"));
                Image image = icon.getImage();
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        lblimg.setBounds(0, 0, 916, 514);
        contentPane.add(lblimg);
        
	}
}
