package pe.edu.upc.dessmo.tools;

import javax.servlet.http.HttpServletRequest;

public class UtilityDessmo {

    public static String GenerarUrl(HttpServletRequest request) {

        String url = request.getRequestURL().toString();

        return url.replace(request.getServletPath(), "");
    }
}
