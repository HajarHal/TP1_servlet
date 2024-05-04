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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        if ("submit".equals(action)) {
            String votreNom = request.getParameter("nom");
            if (votreNom != null && !votreNom.isEmpty()) {
                String nomPrenom = votreNom.toUpperCase();

                double amount = Math.random() * 10; 
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
        } else if ("delete".equals(action)) {
        	 doDelete(request, response);

            
        } else if ("update".equals(action)) {
        	 doPut(request, response);
            
        } else {
            out.println("<html><body>");
            out.println("<h1>Erreur: Action non reconnue.</h1>");
            out.println("</body></html>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String nomASupprimer = request.getParameter("nom");

        if (nomASupprimer != null && !nomASupprimer.isEmpty()) {
            try {
                deleteFromDatabase(nomASupprimer);
                out.println("<html><body>");
                out.println("<h1>Suppression réussie!</h1>");
                out.println("</body></html>");
            } catch (ServletException e) {
                out.println("<html><body>");
                out.println("<h1>Erreur lors de la suppression: " + e.getMessage() + "</h1>");
                out.println("</body></html>");
            }
        } else {
            out.println("<html><body>");
            out.println("<h1>Erreur: Veuillez fournir un nom valide.</h1>");
            out.println("</body></html>");
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String votreNom = request.getParameter("nom");
        if (votreNom != null && !votreNom.isEmpty()) {
            String nomPrenom = votreNom.toUpperCase();
         
            double newAmount = Math.random() * 10; 
            updateDatabase(nomPrenom, newAmount);

            out.println("<html><body>");
            out.println("<h1>Mise à jour réussie!</h1>");
            out.println("<p>Nouveau montant pour " + nomPrenom + ": " + newAmount + " millions de dollars!</p>");
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

    private void deleteFromDatabase(String userName) throws ServletException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM lottery_users WHERE nom = ?")) {
            statement.setString(1, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error deleting from database", e);
        }
    }

    private void updateDatabase(String userName, double newAmount) throws ServletException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE lottery_users SET gain = ? WHERE nom = ?")) {
            statement.setDouble(1, newAmount);
            statement.setString(2, userName);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error updating database", e);
        }
    }
}
