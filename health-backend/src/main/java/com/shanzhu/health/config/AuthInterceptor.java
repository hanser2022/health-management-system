package com.shanzhu.health.config;

import com.alibaba.fastjson2.JSON;
import com.shanzhu.health.entity.User;
import com.shanzhu.health.mapper.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserMapper userMapper;

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/user/login",
            "/user/Wxlogin",
            "/user/register"
    );

    private static final Set<String> ADMIN_PATHS = Set.of(
            "/role/list",
            "/role/all"
    );

    private static final Set<String> ADMIN_PREFIXES = Set.of(
            "/role/",
            "/menu",
            "/detail/addDetail",
            "/detail/updateDetail",
            "/detail/deleteDetailById"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (PUBLIC_PATHS.contains(requestURI)) {
            return true;
        }

        String token = request.getHeader("X-Token");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }

        if (token == null || token.isEmpty()) {
            writeError(response, 20003, "未登录，请先登录");
            return false;
        }

        try {
            User loginUser = jwtConfig.parseToken(token, User.class);
            if (loginUser == null || loginUser.getId() == null) {
                writeError(response, 20003, "登录信息无效，请重新登录");
                return false;
            }

            List<String> roleNames = userMapper.getRoleNameByUserId(loginUser.getId());

            if (requiresAdmin(requestURI, method) && !roleNames.contains("admin")) {
                writeError(response, 20004, "权限不足，无法访问该功能");
                return false;
            }

            request.setAttribute("currentUser", loginUser);
            request.setAttribute("currentRoles", roleNames);
            return true;
        } catch (Exception e) {
            writeError(response, 20003, "登录已过期，请重新登录");
            return false;
        }
    }

    private boolean requiresAdmin(String uri, String method) {
        if (ADMIN_PATHS.contains(uri)) {
            return true;
        }

        for (String prefix : ADMIN_PREFIXES) {
            if (uri.startsWith(prefix)) {
                return true;
            }
        }

        if ("/user/list".equals(uri)) return true;
        if ("/user/add".equals(uri) && "POST".equals(method)) return true;
        if (uri.matches("/user/\\d+") && "DELETE".equals(method)) return true;
        if ("/user/getBodyList".equals(uri)) return true;
        if (uri.startsWith("/user/getBodyById/")) return true;
        if ("/user/updateBody".equals(uri)) return true;
        if (uri.startsWith("/user/deleteBodyById/")) return true;
        if ("/sport/add".equals(uri)) return true;
        if ("/sport/update".equals(uri)) return true;
        if (uri.matches("/sport/\\d+") && "DELETE".equals(method)) return true;

        return false;
    }

    private void writeError(HttpServletResponse response, int code, String message) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        result.put("data", "");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
