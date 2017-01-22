package tokenJWT;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

@Service
public class TokenJWTManager {

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	private String secretKey = "C228D5E0E4676590B1EE28EE689FF5360E29340DF9F0AFCAB9BC536E2425D9DA";

	/**
	 * tiempo de expiracion del token
	 */
	private Long tiempoMaxSeg = 3600l;

	/**
	 * El mapper utilizado para pasar de un ServiceRequest a json y viceversa
	 */
	private ObjectMapper mapper = new ObjectMapper();

	public TokenJWTManager() {
		super();
	}

	private static class EntityJson{
		
		private long userID;
    	private String user;
    	private long rolID;
    	
    	public EntityJson(){}

		public long getUserID() {
			return userID;
		}

		public void setUserID(long userID) {
			this.userID = userID;
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public long getRolID() {
			return rolID;
		}

		public void setRolID(long rolID) {
			this.rolID = rolID;
		}

		@Override
		public String toString() {
			return "EntityJson [userID=" + userID + ", user=" + user + ", rolID=" + rolID + "]";
		}

	}
	
	/**
	 * Genera el token de autenticacion a partir de un usuario autenticado. El
	 * usuario se guarda como dato dentro del token. De esta forma se recibe en
	 * cada request.
	 * 
	 * @return token de autenticacion como String
	 * @throws Exception
	 */
	/**
	 * @param serviceRequest
	 * @return
	 * @throws Exception
	 */
	public String createJWT(Usuario usuario) throws Exception {

		//String subject = mapper.writeValueAsString(usuario);
		
		EntityJson jsonE = new EntityJson();
		jsonE.setUser(usuario.getUser());
		jsonE.setUserID(usuario.getId());
		jsonE.setRolID(usuario.getRol().getId());
		
		String subject = mapper.writeValueAsString(jsonE);
		
		// The JWT signature algorithm we will be using to sign the token

		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// Let's set the JWT Claims
		JwtBuilder builder = Jwts.builder()
								.setIssuedAt(now)
								.claim("content", subject)
								.signWith(SignatureAlgorithm.HS512, secretKey);
		
		// if it has been specified, let's add the expiration
		if (getTiempoMaxSeg() >= 0) {
			long ttlMillis = getTiempoMaxSeg() * 1000;
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}

		// Builds the JWT and serializes it to a compact, URL-safe string
		String jwt = builder.compact();
		return jwt;

	}

	/**
	 * Parsea el token, verifica que sea valido, y finalmente devuelve el user.
	 * 
	 * @param jwt
	 *            token jwt
	 * @return El user si el token es valido y no expiro
	 * @throws ServiceException
	 */
	public Usuario parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(secretKey)
									.parseClaimsJws(jwt)
									.getBody();
		String content = claims.get("content").toString();
		return this.getContentJWT(content);
	}

	public Usuario getContentJWT(String contentJson) {
		EntityJson json = null;
		Usuario user = null;
		try{
			json = mapper.readValue(contentJson, EntityJson.class);
			//verificar que usuarioDAO no este en null el autowired no anda bien
			user = usuarioDAO.getById(json.getUserID());
			return user;
		} catch (Exception e) {
			System.out.println("Error intentando parsear el payload del token: " + contentJson + e.getMessage());
			throw new IllegalStateException("Error de parseo. El payload del token no puede parsearse");
		}

	}

	public Long getTiempoMaxSeg() {
		return tiempoMaxSeg;
	}

	public void setTiempoMaxSeg(Long segundos) {
		this.tiempoMaxSeg = segundos;
	}
	
}
