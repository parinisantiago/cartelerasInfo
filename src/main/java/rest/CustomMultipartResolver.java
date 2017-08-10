package rest;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CustomMultipartResolver extends CommonsMultipartResolver {

	@Override
    public boolean isMultipart(HttpServletRequest request) {
       String method = request.getMethod().toLowerCase();
       if (!Arrays.asList("put", "post").contains(method)) {
          return false;
       }
       String contentType = request.getContentType();
       return (contentType != null &&contentType.toLowerCase().startsWith("multipart/"));
    }
}
