package com.gateway.jwt;

import java.util.List;
 
import javax.crypto.SecretKey;
 
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
 
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;
 
@Component
public class JwtFilterForAdmin extends AbstractGatewayFilterFactory<JwtFilterForAdmin.Config> {
 
	public static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg=\r\n";
 
	public static class Config {
	}
 
	public JwtFilterForAdmin() {
		super(Config.class);
	}
 
	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			List<String> headers = request.getHeaders().get(HttpHeaders.AUTHORIZATION);
			if (headers == null) {
				return handleAuthError(exchange, "Authorization header not provided.");
			}
			String token = headers.get(0);
			if (token == null || !token.startsWith("Bearer ")) {
				return handleAuthError(exchange, "Invalid Token.");
			}
			token = token.substring(7);
			JwtParser parser = Jwts.parser().verifyWith(getSignKey()).build();
			String subject = null;
			try {
				subject = parser.parseSignedClaims(token).getPayload().getSubject();
			} catch (Exception e) {
				return handleAuthError(exchange, "Invalid Token.");
			}
			String userId = subject.split(",")[0];
			String role = subject.split(",")[1];
			if (!role.equals("admin")) {
				return handleAuthError(exchange, "Access not allowed.");
			}
			ServerHttpRequest modifiedRequest = request.mutate().header("userDetails", userId, role).build();
			return chain.filter(exchange.mutate().request(modifiedRequest).build());
		};
	}
 
	private Mono<Void> handleAuthError(ServerWebExchange exchange, String errorMessage) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
		String body = "{\"error\": \"" + errorMessage + "\"}";
		return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
	}
 
	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
 
}
