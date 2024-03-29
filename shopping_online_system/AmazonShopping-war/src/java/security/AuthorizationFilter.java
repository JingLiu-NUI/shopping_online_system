package security;

 
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter
{
     public AuthorizationFilter() {
	}
        //override intialising method 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

        //filteration in jsf page
        //if user is not logged-in it won't allow to open any page
        //if logged in as customer it will take you to the customer functionality
        //if logged as admin, it will take u to the admin page
        //if cutomer trying to open admin page it will give an error message 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			if ( reqURI.contains("/login.xhtml")
					|| (ses != null && ses.getAttribute("username") != null)
					|| reqURI.contains("admin")
                                        || reqURI.contains("update_Delete_products")
					|| reqURI.contains("javax.faces.resource"))
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
}
