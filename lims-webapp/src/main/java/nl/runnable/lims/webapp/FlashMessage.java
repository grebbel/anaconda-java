package nl.runnable.lims.webapp;

import java.io.Serializable;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Represents a message to be put in Flash scope using {@link RedirectAttributes}.
 * 
 * @author Laurens Fridael
 * 
 */
public class FlashMessage implements Serializable {

	private static final long serialVersionUID = -1652904382907982833L;

	private final String message;

	private final String cssClass;

	public static FlashMessage forMessageAndCssClass(final String message, final String cssClass) {
		return new FlashMessage(message, cssClass);
	}

	private FlashMessage(final String message, final String cssClass) {
		this.cssClass = cssClass;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getCssClass() {
		return cssClass;
	}

	@Override
	public String toString() {
		return "FlashMessage [message=" + message + ", cssClass=" + cssClass + "]";
	}

}
