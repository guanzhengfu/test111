package com.example.test111.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.nio.charset.Charset;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class AuthFilterCustom extends OncePerRequestFilter {
  public static final String TOKEN_PREFIX = "Bearer ";
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String base64Token = request.getHeader("Authorization");
    if(StringUtils.isEmpty(base64Token)){
      log.info("未找到token信息");
      filterChain.doFilter(request,response);
      return;
    }
    Claims body = Jwts.parser()
          .setSigningKey("signingKey".getBytes(Charset.defaultCharset()))
          .parseClaimsJws(base64Token.replace(TOKEN_PREFIX, ""))
          .getBody();
    String username = String.valueOf(body.get("user_name"));
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        username,
        123456,
        AuthorityUtils.createAuthorityList("ROLE_USER")
    );
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//    将authenticationToken填充到安全上下文
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request,response);
  }
}
