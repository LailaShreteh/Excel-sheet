package excel.exceptions;
/**
 * Exception not implemented error  
 * @author lailaShreteh
 *
 */
public class NotImplementedError extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = 8655666187495144056L;
private String message = "Sorry not implemented Exception X_X";
	
	public NotImplementedError(Error error) {
		super(error);
	}
	public String getErrorMessage() {
		return message;
	}
}
