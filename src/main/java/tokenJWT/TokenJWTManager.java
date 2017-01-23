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
		
		UserInfoToken jsonE = new UserInfoToken(usuario);
		
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

	public UserInfoToken parseJWT(String jwt) {

		Claims claims = Jwts.parser().setSigningKey(secretKey)
									.parseClaimsJws(jwt)
									.getBody();
		String content = claims.get("content").toString();
		return this.getContentJWT(content);
	}

	public UserInfoToken getContentJWT(String contentJson) {
		try{
			return mapper.readValue(contentJson, UserInfoToken.class);
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
