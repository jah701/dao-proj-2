package internet.shop.web.filters;

import internet.shop.controller.user.LoginController;
import internet.shop.lib.Injector;
import internet.shop.model.Role;
import internet.shop.model.User;
import internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    private static final Map<String, Set<Role.RoleName>> protectedUrls = new HashMap<>();
    private static final Injector injector = Injector.getInstance("internet.shop");
    private UserService userService
            = (UserService) injector.getInstance(UserService.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/all", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/add", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/delete", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/manage", Set.of(Role.RoleName.ADMIN));
        protectedUrls.put("/user/orders/all", Set.of(Role.RoleName.USER));
        protectedUrls.put("/order/details", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/products", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/products/add", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/products/delete", Set.of(Role.RoleName.USER));
        protectedUrls.put("/shopping-cart/complete-order", Set.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String requestedUrl = req.getServletPath();

        Long userId = (Long) req.getSession().getAttribute(LoginController.USER_ID);
        if (!protectedUrls.containsKey(requestedUrl)
                || isAuthorized(userService.get(userId), protectedUrls.get(requestedUrl))) {
            filterChain.doFilter(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/views/access-denied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorized(User user, Set<Role.RoleName> authorizedRoles) {
        for (Role.RoleName authorizedRole : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (authorizedRole.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
