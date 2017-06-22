package pe.com.sigbah.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;

import pe.com.sigbah.common.bean.UsuarioBean;

/**
 * @className: SessionFilter.java
 * @description: 
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class SessionFilter implements Filter {

    private String urlLogin;
//    private String urlIndex;
//    private String urlInicio;
    private String urlExpira;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        HttpServletResponse hRes = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();
        int x = 0;
        if (uri != null) {
            x = uri.lastIndexOf("/");
            if (x > 0) {
                uri = uri.substring(x);
            }
        }

        HttpSession sesion = httpServletRequest.getSession(false);

        if (uri.equals(this.urlLogin)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(urlLogin);
            if (sesion == null) {
                dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            } else {
                  UsuarioBean usuario = (UsuarioBean) ServletUtility.getInstancia().loadSessionAttribute(httpServletRequest, "usuario");
                  if(usuario != null  && StringUtils.hasLength(usuario.getUsuario()) && StringUtils.hasLength(usuario.getSessionId())) {
                       dispatcher = request.getRequestDispatcher("/principal");
                       dispatcher.forward(request, response);
                  } else {
                        dispatcher.forward(request, response);
                  }
            }
        } else if (uri.equals("/logout")) {
            chain.doFilter(request, response);
        } else if (uri.equals("/distrito")) {
            chain.doFilter(request, response);
        } else if (uri.equals("/inicioErr")) {
            chain.doFilter(request, response);
        } else if (uri.equals("/sesionExpira")) {
            chain.doFilter(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(urlExpira);
            if (sesion == null) {
                dispatcher.forward(request, response);
            } else {
                UsuarioBean usuario = (UsuarioBean) ServletUtility.getInstancia().loadSessionAttribute(httpServletRequest, "usuario");
                if (usuario != null && StringUtils.hasLength(usuario.getUsuario()) && StringUtils.hasLength(usuario.getSessionId())) {
                    chain.doFilter(request, response);
                } else {
                    dispatcher.forward(request, response);
                }
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.urlLogin = filterConfig.getInitParameter("urlLogin");
        //this.urlIndex = filterConfig.getInitParameter("urlIndex");
        //this.urlInicio = filterConfig.getInitParameter("urlInicio");
        this.urlExpira = filterConfig.getInitParameter("urlExpira");

        if (urlLogin == null || urlLogin.trim().length() == 0) {
            //Error al cargar la url de login
            throw new ServletException("No se ha configurado URL de login");
        }
    }

    @Override
    public void destroy() {
    	
    }
}
