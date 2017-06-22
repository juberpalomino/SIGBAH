package pe.com.sigbah.web.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @className: ServletUtility.java
 * @description:
 * @date: 21 de jun. de 2017
 * @author: SUMERIO.
 */
public class ServletUtility {
	
	private static boolean instanciated = false;
	private static ServletUtility servletUtility;

	/**
	 * @return
	 */
	public static ServletUtility getInstancia() {
		if (!ServletUtility.instanciated) {
			ServletUtility.servletUtility = new ServletUtility();
			ServletUtility.instanciated = true;
		}
		return ServletUtility.servletUtility;
	}

	/**
	 * @param request
	 * @param parametro
	 * @return
	 */
	public String recuperarParametro(HttpServletRequest request, String parametro) {
		return request.getParameter(parametro);
	}

	/**
	 * Recupera el id de tipo long asociado a un parametro de la página JSF
	 * 
	 * @param request
	 * @param parametro
	 * @return
	 */

	public Integer recuperarIdParametro(HttpServletRequest request, String parametro) {
		Integer i;
		try {
			i = Integer.parseInt(recuperarParametro(request, parametro));
		} catch (NumberFormatException e) {
			i = -1;
		}
		return i;
	}

	/**
	 * @param request
	 * @return
	 */
	public Enumeration requestParameterNames(HttpServletRequest request) {
		return request.getParameterNames();
	}

	/**
	 * @param request
	 * @return
	 */
	public Enumeration requestAttributesNames(HttpServletRequest request) {
		return request.getAttributeNames();
	}

	/**
	 * @param request
	 * @return
	 */
	public Enumeration sessionAttributesNames(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return session.getAttributeNames();
		}
		return null;
	}

	/**
	 * @param request
	 * @param name
	 * @return
	 */
	public String loadRequestParameter(HttpServletRequest request, String name) {
		if (request.getParameter(name) == null) {
			return "";
		} else {
			return request.getParameter(name);
		}
	}

	/**
	 * @param request
	 * @param name
	 * @return
	 */
	public String[] loadRequestParameterValues(HttpServletRequest request, String name) {
		return request.getParameterValues(name);
	}

	/**
	 * @param request
	 * @param name
	 * @param value
	 */
	public void saveRequestAttribute(HttpServletRequest request, String name, Object value) {
		request.setAttribute(name, value);

	}

	/**
	 * @param request
	 * @param name
	 * @return
	 */
	public Object loadRequestAttribute(HttpServletRequest request, String name) {
		return request.getAttribute(name);
	}

	/**
	 * @param request
	 * @return
	 */
	public boolean hasSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (session != null && !session.isNew());
	}

	/**
	 * @param request
	 * @return
	 */
	public HttpSession createSession(HttpServletRequest request) {
		return request.getSession(true);
	}

	/**
	 * @param request
	 */
	public void invalidateSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}

	/**
	 * @param request
	 * @param name
	 * @return
	 */
	public Object loadSessionAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}
		return session.getAttribute(name);
	}

	/**
	 * @param request
	 * @param name
	 * @param value
	 */
	public void saveSessionAttribute(HttpServletRequest request, String name, Object value) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session = createSession(request);
		}
		session.setAttribute(name, value);
	}

	/**
	 * @param request
	 * @param name
	 */
	public void removeAttribute(HttpServletRequest request, String name) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			request.getSession().removeAttribute(name);
		}
	}

	/**
	 * @param response
	 * @param type
	 */
	public void saveResponseContentType(HttpServletResponse response, String type) {
		response.setContentType(type);
	}

	/**
	 * @param response
	 * @param length
	 */
	public void saveResponseContentLength(HttpServletResponse response, int length) {
		response.setContentLength(length);
	}

	/**
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public OutputStream loadResponseOutputStream(HttpServletResponse response) throws IOException {
		return response.getOutputStream();
	}

	/**
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public PrintWriter loadResponseWriter(HttpServletResponse response) throws IOException {
		return response.getWriter();
	}

	/**
	 * @param response
	 * @param cacheControl
	 * @param NoCache
	 * @throws IOException
	 */
	public void saveHeader(HttpServletResponse response, String cacheControl, String NoCache) throws IOException {
		response.setHeader(cacheControl, NoCache);
	}

	/**
	 * @param request
	 * @return
	 */
	public String loadRemoteAddr(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

	/**
	 * @param request
	 * @return
	 */
	public String loadSessionId(HttpServletRequest request) {
		String retval = "";
		HttpSession session = request.getSession(false);
		if (session != null) {
			retval = session.getId();
		}
		return retval;
	}

	/**
	 * @param response
	 * @param pMensaje
	 * @throws Exception
	 */
	public void imprimeMensaje(HttpServletResponse response, String pMensaje) throws Exception {
		this.saveResponseContentType(response, "text/xml; charset=ISO-8859-1");
		this.saveHeader(response, "Cache-Control", "no-cache");

		PrintWriter out = this.loadResponseWriter(response);
		out.write(pMensaje);
		out.close();
	}

}
