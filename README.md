📦 Inventory Management System (Java)
A robust, GUI-based Inventory Management System built using Java Swing and Object-Oriented Programming (OOP) principles. This application enables efficient tracking of products, stock levels, and pricing while implementing fundamental Data Structures and Algorithms (DSA) for optimized performance.

🚀 Key Features
🛠️ CRUD Operations
Create: Add new products with ID, Name, Price, and Quantity.
Read: View all inventory items in a clean, scrollable table.
Update: Edit existing product details (Price, Quantity, Name) seamlessly.
Delete: Remove obsolete items from the inventory.
🔍 Advanced Search & Sort
Smart Search: Implements Binary Search for instant product lookup by name (Space-insensitive & Case-insensitive).
Sorting: Auto-sorts inventory by ID or Name using Bubble Sort.
💾 Data Persistence
Auto-Save/Load: Automatically saves data to a local text file (inventory.txt) and reloads it upon startup, ensuring no data is lost between sessions.
🎨 User Interface
Modern Design: Built with Java Swing, featuring a clean color palette (Dark Blue/Emerald Green) and Times New Roman typography.
Interactive: Visual feedback for low stock levels and successful operations.
🧠 Technical Architecture
This project strictly adheres to Software Engineering principles, separating the User Interface (UI) from the Business Logic.

1. Object-Oriented Programming (OOP)
Abstraction: An Interface (InventoryActions) defines the contract for all operations, hiding implementation details from the GUI.
Encapsulation: The Item class uses private fields with getters/setters to protect data integrity.
Composition: The GUI class (InventorySystem) "has-a" logic manager (InventoryOperations), promoting loose coupling.
Polymorphism: The system relies on the interface type (InventoryActions) rather than the concrete class.
2. Data Structures & Algorithms (DSA)
ArrayList (Dynamic Array): Used to store inventory items, allowing the list to grow or shrink dynamically as needed.
Bubble Sort (O(n²)): Implemented to organize data by ID (for display) and by Name (as a prerequisite for Binary Search).
Binary Search (O(log n)): Used for high-speed searching. It drastically reduces the time complexity compared to a standard linear search.
Linear Search: Used for finding items by ID during Update and Delete operations.
📂 Project Structure
Item.java: The Data Model representing a single product.
InventoryActions.java: The Interface (Abstraction layer) defining available operations.
InventoryOperations.java: The Logic Class implementing DSA algorithms and file handling.
InventorySystem.java: The Main Class containing the Swing GUI code and event listeners.
⚙️ How to Run
Prerequisites: Ensure you have the Java Development Kit (JDK) installed.
Compile:
javac InventorySystem.java InventoryOperations.java InventoryActions.java Item.java
Run:
java InventorySystem
🔮 Future Improvements
Implement Quick Sort or Merge Sort for better performance on large datasets.
Migrate data storage from a text file to a SQL Database (MySQL/SQLite).
Add user authentication (Login/Signup).
Developed by Selish doultani & Duroop kumar
