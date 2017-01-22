package tokenJWT;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import modelo.Rol;
import modelo.Usuario;

@Component(value = "securityFilter")
@Order(Ordered.LOWEST_PRECEDENCE)
public class TokenJWTFilter implements Filter{
	
	private TokenJWTManager tokenManagerSecurity = new TokenJWTManager();

	private LoginService loginService = new LoginService();

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String[] splitPath = req.getRequestURI().split("/");
		
		String path = "";
		
		if(splitPath.length > 0) {
			path = splitPath[splitPath.length -1];
		}

		if(req.getMethod().equals("GET")){
			// sigue la cadena de ejecucion
			chain.doFilter(req, response);
		}
		else{
			if("login".equals(path)) {
				// sigue la cadena de ejecucion hacia el login
				chain.doFilter(req, response);
			} else {
				// valido token
				String jwt = req.getHeader("Authorization");
				try {
					// Si la validacion es correcta y el token no expiro, parsea el
					// contenido del token y devuelve el user
					Usuario user = tokenManagerSecurity.parseJWT(jwt);
					
					// Seteo el user en un atributo nuevo, de esta forma
					// ya estaria disponible para el resto de los controllers
					request.setAttribute("user", user);

					chain.doFilter(req, response);
	
				} catch (ExpiredJwtException e) {
					System.out.println(
							"El token ya no es valido, expiro su tiempo de validez: " + jwt + " " + e.getMessage());
	
					// ejecuta el logout para invalidar la session en banca
					if (e.getClaims().containsKey("content")) {
						Usuario user = tokenManagerSecurity.getContentJWT(e.getClaims().get("content").toString());
						System.out.println(
								"Expiro el tiempo de ejecucion del token, por lo que se invalida la session del usuario (logout) "
										+ e.getMessage());
						loginService.logout(user);
					}
					((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().close();
	
				} catch (SignatureException e) {
					System.out.println("No es un token valido, token: " + jwt + " " + e.getMessage());
					((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().close();
				} catch (Exception e) {
					System.out.println("No es un token valido, token: " + jwt + " " + e.getMessage());
					((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().close();
				}
	
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
