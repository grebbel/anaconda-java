package nl.runnable.lims.webapp.jsp;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;

/**
 * JSP tag handler for including multiple Handlebars scripts matching a given location pattern.
 * 
 * @author Laurens Fridael
 * 
 */
public class HandlebarsScriptIncludeTag extends SimpleTagSupport {

	/* Configuration */

	private String locationPattern;

	private String namePrefix;

	private String role;

	/* Operations */

	@Override
	public void doTag() throws JspException, IOException {
		final PageContext context = (PageContext) getJspContext();
		if (StringUtils.hasText(getRole())) {
			if (userHasRole() == false) {
				return;
			}
		}
		final ServletContextResourcePatternResolver resolver = new ServletContextResourcePatternResolver(
				context.getServletContext());
		for (final Resource resource : resolver.getResources(getLocationPattern())) {
			writeScriptTag(resource, context.getOut());
		}
	}

	private boolean userHasRole() {
		boolean hasRole = false;
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		for (final GrantedAuthority authority : authentication.getAuthorities()) {
			if (authority.getAuthority().equals(getRole())) {
				hasRole = true;
				break;
			}
		}
		return hasRole;
	}

	private void writeScriptTag(final Resource resource, final JspWriter out) throws IOException {
		String name = resource.getFilename();
		if (name.lastIndexOf('.') > -1) {
			name = name.substring(0, name.lastIndexOf('.'));
		}
		if (StringUtils.hasText(getNamePrefix())) {
			name = getNamePrefix() + name;
		}
		out.write(String.format("<script type=\"text/x-handlebars\" data-template-name=\"%s\">", name));
		out.newLine();
		out.write(FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), "utf-8")));
		out.write("</script>");
		out.newLine();
	}

	/* Configuration */

	public void setLocationPattern(final String path) {
		this.locationPattern = path;
	}

	public String getLocationPattern() {
		return locationPattern;
	}

	public void setNamePrefix(final String prefix) {
		this.namePrefix = prefix;
	}

	public String getNamePrefix() {
		return namePrefix;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

}
