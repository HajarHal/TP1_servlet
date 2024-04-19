package exple1;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/hello")
public class BlacklistFilter implements Filter {

    private List<String> blacklist;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialise la liste noire avec des noms interdits (à adapter selon vos besoins)
        blacklist = new ArrayList<>();
        blacklist.add("john");
        blacklist.add("jane");
        blacklist.add("doe");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String nom = httpRequest.getParameter("nom");

        // Vérifie si le nom est dans la liste noire
        if (nom != null && blacklist.contains(nom.toLowerCase())) {
            // Si le nom est dans la liste noire, renvoyer une erreur ou rediriger vers une page d'erreur
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } else {
            // Sinon, laissez la requête passer à la servlet suivante dans la chaîne (GreetingServlet)
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Méthode de nettoyage du filtre
    }
}
