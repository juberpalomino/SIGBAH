package pe.com.sigbah.web.controller.common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import pe.com.sigbah.common.bean.UsuarioBean;
import pe.com.sigbah.common.util.Constantes;
import pe.com.sigbah.web.filter.ServletUtility;

/**
 * @className: Utilidades.java
 * @description:
 * @date: 22 de jun. de 2017
 * @author: SUMERIO.
 */
public class Utilidades {
	private static boolean instanciated = false;
	private static Utilidades instancia;

	private Utilidades() {
	}

	/**
	 * @return
	 */
	public static Utilidades getInstancia() {
		if (!Utilidades.instanciated) {
			Utilidades.instancia = new Utilidades();
			Utilidades.instanciated = true;
		}
		return Utilidades.instancia;
	}

	/**
	 * @param response
	 * @param usuario
	 */
	public void saveUserInCookie(HttpServletResponse response, UsuarioBean usuario) {
		String serial = usuario.getUsuario();// +"/" +
		// usuario.getDePrenombres();
		// usuario.getParametrosGlobales().getCoLocal()+"/"+
		// usuario.getParametrosGlobales().getDeLocal();
		// +"/"+usuario.getRemoteAttribs().getSessionId();
		agregarCookie(response, Constantes.USER_COOKIE_ID, serial, true);
	}

	private void agregarCookie(HttpServletResponse response, String id, String valor, boolean expire) {
		Cookie cookie = new Cookie(id, valor);
		/*
		 * if(expire){ cookie.setMaxAge(10); }
		 */
		response.addCookie(cookie);
	}

	/**
	 * @param request
	 * @return
	 */
	public UsuarioBean loadUserFromCookie(HttpServletRequest request) {
		UsuarioBean usuario = new UsuarioBean();
		String user = giveMeCoockieValue(request, Constantes.USER_COOKIE_ID);
		splitAndPopuleUser(user, usuario);

		return usuario;
	}

	/**
	 * @param request
	 * @return
	 */
	public UsuarioBean loadUserFromSession(HttpServletRequest request) {
		return (UsuarioBean) ServletUtility.getInstancia().loadSessionAttribute(request, "usuario");
	}

	/**
	 * @param user
	 * @param usuario
	 */
	public void splitAndPopuleUser(String user, UsuarioBean usuario) {
		if (!user.equals("")) {
			String str[] = user.split("/");
			if (str.length == 6) {
				usuario.setUsuario(str[0]);
			}
		}
	}

	/**
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public String giveMeCoockieValue(HttpServletRequest request, String cookieName) {
		String retval = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					retval = cookies[i].getValue();
					break;
				}
			}
		}
		return retval;
	}

	/**
	 * @param in
	 * @param response
	 * @param contentType
	 * @throws Exception
	 */
	public void readHttpAndWriteInOutputStream(InputStream in, HttpServletResponse response, String contentType)
			throws Exception {
		try {
			long h = 0;
			int length = -1;
			byte[] buffer = new byte[4096];

			response.setContentType(contentType);
			ServletOutputStream ouputStream = response.getOutputStream();
			while ((length = in.read(buffer)) != -1) {
				ouputStream.write(buffer, 0, length);
				ouputStream.flush();
				h += length;
			}
			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public StringBuffer readHttpAndWriteInOutput(InputStream in) throws Exception {
		StringBuffer sf = new StringBuffer();
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(in));

			String line = "";
			while ((line = input.readLine()) != null) {
				sf.append(line.trim());
			}
		} catch (Exception e) {
			throw e;
		}
		return sf;
	}

	/**
	 * @param response
	 * @param urlReporte
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String procesaReportePrimerIntento(HttpServletResponse response, String urlReporte)
			throws IOException, ServletException {
		String urlRetval = null;
		HttpURLConnection m_con = null;
		InputStream in = null;
		String contentType = null;
		try {
			java.net.URL url = new java.net.URL(urlReporte.toString().replace(" ", "%20"));
			m_con = (HttpURLConnection) url.openConnection();

			if (m_con != null) {
				contentType = m_con.getHeaderField("Content-Type");
				if (contentType != null && contentType.equals("application/pdf")) {
					in = new BufferedInputStream(m_con.getInputStream());
					readHttpAndWriteInOutputStream(in, response, contentType);
				} else {
					urlRetval = "forward:srReporteContingencia.do";
				}
			} else {
				urlRetval = "forward:srReporteContingencia.do";
			}
		} catch (Exception e) {
			urlRetval = "forward:srReporteContingencia.do";
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (m_con != null) {
					m_con.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return urlRetval;
	}

	/**
	 * @param response
	 * @param urlReporte
	 * @param model
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String procesaReporteSegundoIntento(HttpServletResponse response, String urlReporte, Model model)
			throws IOException, ServletException {

		String urlRetval = null;

		ServletOutputStream ouputStream = null;
		HttpURLConnection m_con = null;
		InputStream in = null;
		String contentType = null;
		try {
			java.net.URL url = new java.net.URL(urlReporte.toString().replace(" ", "%20"));
			m_con = (HttpURLConnection) url.openConnection();

			if (m_con != null) {
				contentType = m_con.getHeaderField("Content-Type");
				if (contentType != null && contentType.equals("application/pdf")) {
					in = new BufferedInputStream(m_con.getInputStream());
					readHttpAndWriteInOutputStream(in, response, contentType);
				} else {
					urlRetval = "reportError";
					StringBuffer sf = readHttpAndWriteInOutput(m_con.getInputStream());
					model.addAttribute("textError", sf.toString());
				}
			} else {
				urlRetval = "reportError";
				model.addAttribute("textError",
						"<H4>No se puede conectar al servidor de reportes, esta fuera de servicio o hay problemas de conexion</h4>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.reset();
			urlRetval = "reportError";
			model.addAttribute("textError", "<H4>Error interno en la ejecución del reporte</h4>");
			model.addAttribute("detailError", e.getStackTrace());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (m_con != null) {
					m_con.disconnect();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return urlRetval;
	}

	/**
	 * Ajusta campos a la derecha de una cadena pasada por parametro
	 * 
	 * @param szBuffer
	 *            String que contiene la cadena a ajustar sus campos
	 * @param tam_dest
	 *            Tamaño de la cadena
	 * @param szCar
	 *            Caracter de relleno
	 * @return La cadena ajustada a la derecha
	 */
	public static String AjustaCampoDerecha(String szBuffer, int tam_dest, String szCar) {
		int iLen;
		String szDest = "";
		for (int i = 0; i < tam_dest; i++) {
			szDest = szDest.concat(szCar);
		}
		iLen = szBuffer.length();
		if (iLen > tam_dest) {
			// El número es excesivamente largo (desbordamiento).
			// Truncarlo e indicar el error en el valor de retorno
			iLen = tam_dest;
		}
		szDest = szDest.substring(0, (tam_dest - iLen)) + szBuffer;
		return szDest;

	}

	/**
	 * Ajusta una cadena a la izquierda
	 * 
	 * @param szBuffer
	 *            Es el tamaño la cadena
	 * @param tam_dest
	 *            Es el tamaño de la cadena
	 * @return Es el campo formateado
	 */
	public static String AjustaCampoIzquierda(String szBuffer, int tam_dest, String szCar) {
		int iLen = szBuffer.length();
		StringBuffer sbdest = new StringBuffer();
		for (int i = 0; i < tam_dest - iLen; i++) {
			sbdest.append(szCar);
		}
		if (iLen > tam_dest) {
			// El número es excesivamente largo (desbordamiento).
			// Truncarlo e indicar el error en el valor de retorno
			iLen = tam_dest;
		}
		return szBuffer + sbdest;

	}

	/**
	 * borra los espacios en blanco repetidos
	 *
	 * @param text
	 *            input to format
	 * @return String string with single whitespaces
	 */
	public static String reduceWhitespace(String text) {
		char[] chars = text.trim().toCharArray();
		int start = 0;
		int end = 0;
		int occ = 0;

		// replace n occurrences with one
		for (int i = 0; i < chars.length; i++) {
			if (Character.isWhitespace(chars[i])) {
				occ++;
			} else {
				occ = 0;
			}

			if (occ < 2) {
				chars[end++] = chars[i];
			}
		} // end for

		// return formatted string
		return new String(chars, start, end);
	}

	/**
	 * @param length
	 * @return
	 */
	public static String generateRandomNumber2(int length) {
		StringBuffer buffer = new StringBuffer();
		String characters = "12345678901234567890";

		try {
			int charactersLength = characters.length();
			for (int i = 0; i < length; i++) {
				double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
		} catch (Exception e) {
			buffer.append("0");
		}

		return buffer.toString();
	}

	/**
	 * @param length
	 * @return
	 */
	public static String generateRandomNumber(int length) {
		String randomString = "";
		try {
			double multi = Math.pow(10.0, length);
			int randomValue = (int) (Math.random() * multi);
			randomString = String.valueOf(randomValue);
		} catch (Exception e) {
			randomString = "0";
		}

		return randomString;
	}

}
