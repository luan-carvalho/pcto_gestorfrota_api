package br.edu.ifto.gestorfrotaapi.infra.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import br.edu.ifto.gestorfrotaapi.infra.security.service.TokenService;
import br.edu.ifto.gestorfrotaapi.user.exception.UserNotFoundException;
import br.edu.ifto.gestorfrotaapi.user.model.enums.UserStatus;
import br.edu.ifto.gestorfrotaapi.user.model.valueObjects.Cpf;
import br.edu.ifto.gestorfrotaapi.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var token = this.recoverToken(request);

        if (token != null) {

            var cpf = tokenService.validateToken(token);

            UserDetails user = userRepository.findByCpfAndStatus(new Cpf(cpf), UserStatus.ACTIVE)
                    .orElseThrow(() -> new UserNotFoundException());

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null)
            return null;
        return authHeader.replace("Bearer ", "");
    }
}
