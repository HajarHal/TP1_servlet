package exple1;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class GreetingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        // Initialize database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/lottery";
            String username = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new ServletException("Error initializing database connection", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String votreNom = request.getParameter("nom");
        if (votreNom != null && !votreNom.isEmpty()) {
            String nomPrenom = votreNom.toUpperCase();

            // Générer un montant de gain aléatoire entre 0 et 10 millions
            double amount = Math.random() * 10; // Montant aléatoire entre 0 (inclus) et 10 (exclus)

            // Insérer le nom et le montant dans la base de données
            insertIntoDatabase(nomPrenom, amount);

            out.println("<html><body>");
            out.println("<h1>Greetings " + nomPrenom + "!</h1>");
            out.println("<p>Vous avez gagné: " + amount + " millions de dollars!</p>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h1>Erreur: Veuillez fournir un nom valide.</h1>");
            out.println("</body></html>");
        }
    }

    private void insertIntoDatabase(String userName, double amount) throws ServletException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO lottery_users (nom, gain) VALUES (?, ?)")) {
            statement.setString(1, userName);
            statement.setDouble(2, amount);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error inserting into database", e);
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle GET requests separately, but currently, the logic is in doPost for this example
        doPost(request, response);
    }

    @Override
    public void destroy() {
        // Close database connection
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
