import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class recGraph extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    Connection conn;
    String url = "jdbc:mysql://localhost/rrrdb";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	recGraph frame = new recGraph();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public recGraph() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try {
            Connection conn = DriverManager.getConnection(url, "root", "");
            String query = "SELECT ReceiveDate, COUNT(ReceiveID) AS ReceiveCount FROM receiveproduct GROUP BY ReceiveDate";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String date = rs.getString("ReceiveDate");
                int returnCount = rs.getInt("ReceiveCount");
                dataset.addValue(returnCount, "Receive Count", date);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Receive Product Chart", 
                "Date",               
                "Number of Receives",  
                dataset             
        );
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(780, 550));
        contentPane.add(chartPanel);
    }
}
