import javax.swing.*;
import java.net.URL;

public class test {
    public static void main(String[] args) {
        // ลองโหลดไฟล์ภาพจาก resources ใน classpath
        URL url = test.class.getClassLoader().getResource("resources/img/back.png");
        
        if (url != null) {
            System.out.println("File found: " + url);
            // ใช้ ImageIcon เพื่อนำไฟล์ภาพมาแสดง
            ImageIcon icon = new ImageIcon(url);
            // ตัวอย่างการใช้งาน icon (เช่น ใส่ลงในปุ่ม)
            JButton btnBack = new JButton(icon);
        } else {
            System.out.println("File not found!");
        }
    }
}
