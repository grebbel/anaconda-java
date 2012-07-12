package nl.runnable.lims.webapp.jsp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.util.FileCopyUtils;

public class HandlebarsScriptTag extends SimpleTagSupport {

	private String templateName;

	private String suffix = ".html";

	private String prefix = "handlebars/";

	@Override
	public void doTag() throws JspException, IOException {
		final PageContext context = (PageContext) getJspContext();
		final JspWriter out = context.getOut();
		out.write(String.format("<script type=\"text/x-handlebars\" data-template-name=\"%s\">", getTemplateName()));
		out.newLine();
		String path = ((HttpServletRequest) context.getRequest()).getServletPath();
		path = URI.create(path).resolve(String.format("%s%s%s", getPrefix(), templateName, getSuffix())).toString();
		final InputStream resource = context.getServletContext().getResourceAsStream(path);
		if (resource == null) {
			throw new JspException("Could not find resource " + path);
		}
		out.write(FileCopyUtils.copyToString(new InputStreamReader(resource, "utf-8")));
		out.write("</script>");
		out.newLine();
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

}
