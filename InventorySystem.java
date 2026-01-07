import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InventorySystem extends JFrame {

    // Composition: The GUI *has* an Operations Manager
    private InventoryOperations ops;

    // GUI Components
    private JTextField txtId, txtName, txtPrice, txtQty, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    // Design Constants
    private final Color PRIMARY_COLOR = new Color(52, 73, 94);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    private final Color BG_COLOR = new Color(236, 240, 241);
    private final Font MAIN_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    public InventorySystem() {
        ops = new InventoryOperations(); // Initialize the Logic Class

        // --- WINDOW SETUP ---
        setTitle("Inventory Management System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        JLabel lblTitle = new JLabel("INVENTORY MANAGEMENT SYSTEM");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Main Container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- LEFT SIDE: INPUTS ---
        JPanel formPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new LineBorder(new Color(200, 200, 200), 1));
        formPanel.setPreferredSize(new Dimension(250, 0));

        txtId = createStyledField();
        txtName = createStyledField();
        txtPrice = createStyledField();
        txtQty = createStyledField();

        formPanel.add(createLabel("Item Details"));
        formPanel.add(createInputGroup("Item ID:", txtId));
        formPanel.add(createInputGroup("Name:", txtName));
        formPanel.add(createInputGroup("Price:", txtPrice));
        formPanel.add(createInputGroup("Quantity:", txtQty));

        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton btnAdd = createStyledButton("Add", ACCENT_COLOR);
        JButton btnUpdate = createStyledButton("Update", new Color(243, 156, 18));
        btnPanel.add(btnAdd); btnPanel.add(btnUpdate);
        formPanel.add(btnPanel);

        mainPanel.add(formPanel, BorderLayout.WEST);

        // --- CENTER: TABLE ---
        String[] cols = {"ID", "Name", "Price", "Quantity"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(MAIN_FONT);
        table.setShowGrid(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.BLACK);
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        // --- BOTTOM: DSA ACTIONS ---
        JPanel opsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        opsPanel.setBackground(Color.WHITE);

        JButton btnSort = createStyledButton("Sort ID", PRIMARY_COLOR);
        JButton btnDelete = createStyledButton("Delete", new Color(231, 76, 60));
        JButton btnSave = createStyledButton("Save", PRIMARY_COLOR);

        txtSearch = createStyledField();
        txtSearch.setPreferredSize(new Dimension(150, 35));
        JButton btnSearch = createStyledButton("Search Name", PRIMARY_COLOR);

        opsPanel.add(btnSort);
        opsPanel.add(new JLabel("Search:"));
        opsPanel.add(txtSearch);
        opsPanel.add(btnSearch);
        opsPanel.add(btnDelete);
        opsPanel.add(btnSave);

        mainPanel.add(opsPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);


        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtId.setText(tableModel.getValueAt(row, 0).toString());
                    txtName.setText(tableModel.getValueAt(row, 1).toString());
                    txtPrice.setText(tableModel.getValueAt(row, 2).toString());
                    txtQty.setText(tableModel.getValueAt(row, 3).toString());
                    txtId.setEditable(false);
                }
            }
        });

        btnAdd.addActionListener(e -> {
            try {
                ops.addItem(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                );
                refreshTable();
                clearInputs();
                txtId.setEditable(true);
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid Input"); }
        });

        btnUpdate.addActionListener(e -> {
            try {
                if (txtId.getText().isEmpty()) return;

                ops.updateItem(
                        Integer.parseInt(txtId.getText()),
                        txtName.getText(),
                        Double.parseDouble(txtPrice.getText()),
                        Integer.parseInt(txtQty.getText())
                );
                refreshTable();
                clearInputs();
                txtId.setEditable(true);
                JOptionPane.showMessageDialog(this, "Item Updated!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Select an item to edit");
            }
        });

        btnSort.addActionListener(e -> {
            ops.sortById();
            refreshTable();
            JOptionPane.showMessageDialog(this, "Sorted by ID");
        });

        btnSearch.addActionListener(e -> {
            String target = txtSearch.getText();
            if (target.isEmpty()) return;

            ops.sortByName(); // Sort needed for binary search
            refreshTable();

            int index = ops.binarySearchByName(target);
            if (index != -1) {
                table.setRowSelectionInterval(index, index);
                table.scrollRectToVisible(new Rectangle(table.getCellRect(index, 0, true)));

                Item item = ops.getList().get(index);
                String status = (item.getQuantity() < 50) ? "⚠️ Low Stock" : "✅ Sufficient";

                JOptionPane.showMessageDialog(this,
                        "Found: " + item.getName() + "\nID: " + item.getId() + "\nQty: " + item.getQuantity() + "\nStatus: " + status);
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int id = (int) tableModel.getValueAt(row, 0);
                ops.deleteItem(id);
                refreshTable();
                clearInputs();
            }
        });

        btnSave.addActionListener(e -> {
            try {
                ops.saveData();
                JOptionPane.showMessageDialog(this, "Saved!");
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : ops.getList()) {
            tableModel.addRow(new Object[]{item.getId(), item.getName(), item.getPrice(), item.getQuantity()});
        }
    }

    private void clearInputs() {
        txtId.setText(""); txtName.setText(""); txtPrice.setText(""); txtQty.setText("");
    }

    // --- GUI Helpers (Styling) ---
    private JPanel createInputGroup(String label, JTextField field) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 16));
        l.setForeground(PRIMARY_COLOR);
        return l;
    }

    private JTextField createStyledField() {
        JTextField f = new JTextField();
        f.setFont(MAIN_FONT);
        f.setBorder(BorderFactory.createCompoundBorder(new LineBorder(new Color(200,200,200)), new EmptyBorder(5,5,5,5)));
        return f;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setBackground(bg); b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setFocusPainted(false);
        return b;
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new InventorySystem().setVisible(true));
    }
}