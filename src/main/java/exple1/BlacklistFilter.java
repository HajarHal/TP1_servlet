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
        blacklist = new ArrayList<>();
        blacklist.add("lamiae");
        blacklist.add("chaimae");
        blacklist.add("yahya");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String nom = httpRequest.getParameter("nom");

        if (nom != null && blacklist.contains(nom.toLowerCase())) {
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
