
import java.sql.*;
import java.util.Scanner;

public class OrderManagement {

    static final String URL =
        "jdbc:mysql://localhost:3306/ecommerce_db";

    static final String USER = "root";
    static final String PASSWORD = "Veer@200709";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection con =
                DriverManager.getConnection(URL, USER, PASSWORD)) {

            System.out.println("Connected to Ecommerce Database!");

            int choice;

            do {
                System.out.println("\n===== ORDER MANAGEMENT SYSTEM =====");
                System.out.println("1. Insert Three Orders");
                System.out.println("2. Search Orders by Status");
                System.out.println("3. Increase Order Quantity");
                System.out.println("4. Update Order Status");
                System.out.println("5. Total Order Value and Delete");
                System.out.println("0. Exit");

                System.out.print("Enter choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {

                    case 1:
                        insertOrders(con);
                        break;

                    case 2:
                        searchOrders(con);
                        break;

                    case 3:
                        updateQuantity(con);
                        break;

                    case 4:
                        updateStatus(con);
                        break;

                    case 5:
                        totalValueAndDelete(con);
                        break;

                    case 0:
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ==========================================
    // Q1 (a): INSERT AT LEAST THREE ORDERS
    // PreparedStatement + Duplicate ID Handling
    // ==========================================

    static void insertOrders(Connection con) throws SQLException {

        String sql = "INSERT INTO `Orders` VALUES (?, ?, ?, ?, ?, ?)";

        Order[] orders = {

            new Order(201, "Rahul", "Laptop",
                      2, 55000, "Pending"),

            new Order(202, "Aman", "Mouse",
                      5, 500, "Shipped"),

            new Order(203, "Priya", "Keyboard",
                      3, 1200, "Cancelled")
        };

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (Order o : orders) {

                ps.setInt(1, o.getOrderId());
                ps.setString(2, o.getCustomerName());
                ps.setString(3, o.getProductName());
                ps.setInt(4, o.getQuantity());
                ps.setDouble(5, o.getUnitPrice());
                ps.setString(6, o.getStatus());

                try {
                    ps.executeUpdate();

                    System.out.println(
                        "Order " + o.getOrderId() +
                        " inserted successfully!"
                    );

                } catch (SQLIntegrityConstraintViolationException e) {

                    System.out.println(
                        "Duplicate Order ID: " + o.getOrderId()
                    );
                }
            }
        }
    }


    // ==========================================
    // Q1 (b): SEARCH ORDERS BY STATUS
    // Quantity > 2
    // Sort by unit_price DESC
    // ==========================================

    static void searchOrders(Connection con) throws SQLException {

        System.out.print("Enter order status: ");
        String status = sc.nextLine();

        String sql =
            "SELECT * FROM `Orders` " +
            "WHERE status = ? AND quantity > 2 " +
            "ORDER BY unit_price DESC";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;
                    displayOrder(rs);
                }

                if (!found) {
                    System.out.println("No matching orders found!");
                }
            }
        }
    }


    // ==========================================
    // Q1 (c): INCREASE EXISTING ORDER QUANTITY
    // ==========================================

    static void updateQuantity(Connection con) throws SQLException {

        System.out.print("Enter Order ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter additional quantity: ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty <= 0) {
            System.out.println("Quantity must be positive!");
            return;
        }

        String sql =
            "UPDATE `Orders` SET quantity = quantity + ? " +
            "WHERE order_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Order quantity updated successfully!");
            } else {
                System.out.println("Order does not exist!");
            }
        }
    }


    // ==========================================
    // Q1 (d): UPDATE ORDER STATUS
    // Valid: Pending, Shipped, Delivered
    // Display updated order details
    // ==========================================

    static void updateStatus(Connection con) throws SQLException {

        System.out.print("Enter Order ID: ");
        int id = Integer.parseInt(sc.nextLine());

        String select =
            "SELECT * FROM `Orders` WHERE order_id = ?";

        try (PreparedStatement ps = con.prepareStatement(select)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    System.out.println("Order not found!");
                    return;
                }

                System.out.println("Existing Order Details:");
                displayOrder(rs);
            }
        }

        System.out.print("Enter new status: ");
        String status = sc.nextLine().trim();

        if (!status.equalsIgnoreCase("Pending") &&
            !status.equalsIgnoreCase("Shipped") &&
            !status.equalsIgnoreCase("Delivered")) {

            System.out.println("Invalid status!");
            return;
        }

        String validStatus =
            status.substring(0, 1).toUpperCase() +
            status.substring(1).toLowerCase();

        String update =
            "UPDATE `Orders` SET status = ? WHERE order_id = ?";

        try (PreparedStatement ps = con.prepareStatement(update)) {

            ps.setString(1, validStatus);
            ps.setInt(2, id);

            ps.executeUpdate();

            System.out.println("Order status updated successfully!");
        }

        // Display Updated Order Details

        try (PreparedStatement ps = con.prepareStatement(select)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("Updated Order Details:");
                    displayOrder(rs);
                }
            }
        }
    }


    // ==========================================
    // Q1 (e): TOTAL ORDER VALUE USING SUM
    // DELETE ONLY IF STATUS = CANCELLED
    // ==========================================

    static void totalValueAndDelete(Connection con)
            throws SQLException {

        String sumSql =
            "SELECT SUM(quantity * unit_price) AS total " +
            "FROM `Orders`";

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sumSql)) {

            if (rs.next()) {

                System.out.println(
                    "Total Order Value: " +
                    rs.getDouble("total")
                );
            }
        }

        System.out.print("Enter Order ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String deleteSql =
            "DELETE FROM `Orders` " +
            "WHERE order_id = ? AND status = 'Cancelled'";

        try (PreparedStatement ps = con.prepareStatement(deleteSql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println(
                    "Cancelled order deleted successfully!"
                );
            } else {
                System.out.println(
                    "Cannot delete! Order not found or status is not Cancelled."
                );
            }
        }
    }


    // ==========================================
    // HELPER METHOD: DISPLAY ORDER DETAILS
    // ==========================================

    static void displayOrder(ResultSet rs) throws SQLException {

        System.out.println(
            rs.getInt("order_id") + " | " +
            rs.getString("customer_name") + " | " +
            rs.getString("product_name") + " | " +
            rs.getInt("quantity") + " | " +
            rs.getDouble("unit_price") + " | " +
            rs.getString("status")
        );
    }
}
