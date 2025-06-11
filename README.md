# SupplyFlow

เป็นโปรแกรมจำลองการสั่งซื้อสินค้าจาก Supplier
ใช้ภาษา Java
และใช้เครื่องมืออย่าง SQL
*เวลาใช้งานต้องใช้ร่วมกันกับ phpmyadmin เท่านั้น สามารถ import ไฟล์ sql ที่อยู่ในโฟลเดอร์ได้*
*ทุกๆรายการข้อมูลจะสามารถ Import และ Export เป็นไฟล์ CSV ได้*

ระบบส่วนใหญ่เป็นแบบ Manual

การจัดการซัพพลายเออร์     - จะเป็นการเพิ่มลบอัพเดตแก้ไขข้อมูล
                         ของสถานที่ที่เราจะสิ่งสินค้ามาจากสถานที่นั้นๆ

จัดการประเภทสินค้า        - คือการเพิ่มลบแก้ไขข้อมูลของประเภทของสินค้า
                         เพื่อที่จะให้ตารางอื่นๆสามารถเรียกใช้ข้อมูลได้

จัดการซัพพลายเออร์สินค้า   - เป็นการเพิ่มลบแก้ไขข้อมูลของสินค้าในซัพพลายเออร์
                         เพื่อให้ร้านค้าสามารถสั่งสินค้าจากซัพพลายเออร์ได้

สั่งของจากซัพพลายเออร์    - คือเมนูสำหรับการสั่งซื้อสินค้าจากซัพพลายเออร์เข้าสู่ร้านค้า
                         โดยสามารถคลิกเพื่อเลือกสินค้า ใส่จำนวนและสถานที่ที่ต้องการสั่งแล้วกด Add Order
                         เพื่อทำการสั่งซื้อ

รับ - คืนสินค้า           - คือเมนูแสดงรายการออเดอร์ สามารถกด Receive Product เพื่อรับสินค้าเข้าร้าน และ Return Product เพื่อคืนสินค้าได้

การจัดการสินค้า           - คือเมนูจัดการสินค้าในร้าน โดยจะสามารถแก้ไขราคา และลบสินค้าได้เท่านั้น กรณีอยากเพิ่มสินค้าต้องสั่งซื้อเอาอย่างเดียว
                         โดยที่เมนูนี้จะสามารถกรอกจำนวนที่จะขายแล้วกด Sell ได้ เมื่อกด Sell จำนวนสินค้าชนิดที่ขายในร้านจะลดลง
                         หาก สินค้าหมดหรือจำนวนสินค้าเท่ากับ 0 จะทำการลบสินค้านั้นๆทันที

รายการรับ - คืนสินค้า      - คือการแสดงรายการรับคืนสินค้าทั้งหมด สามารถค้นหา และแสดงกราฟได้

--- English ---
A Simulation Program for Ordering Products from Suppliers
Language Used: Java
Tools Used: SQL
Note: The system must be used with phpMyAdmin only, and the SQL file located in the folder can be imported.
All data records can be imported and exported as CSV files.

Most of the system is manual.

Supplier Management
Allows for adding, deleting, updating, and editing supplier information
(the places where the store will order products from).

Product Category Management
Allows for adding, deleting, and editing product category information
so that other tables can refer to these categories.

Supplier Product Management
Enables adding, deleting, and editing of products provided by suppliers,
allowing the store to order products from them.

Order from Supplier
A menu for placing orders from suppliers to the store.
Users can select products, enter quantities, and choose the supplier location.
Then click "Add Order" to place the order.

Receive / Return Products
This menu displays order records.
Users can click "Receive Product" to accept products into the store,
or "Return Product" to return items.

Store Product Management
A menu for managing products available in the store.
Only price editing and product deletion are allowed here.
To add new products, the user must place an order.

In this menu, users can input the quantity they want to sell and click "Sell".
When selling, the product quantity in the store decreases.
If a product runs out (quantity = 0), it will be automatically deleted.

Receive / Return Records
Displays all records of received and returned products.
Users can search and generate graphs based on the data.
