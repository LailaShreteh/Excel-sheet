package excel.exceptions;
/**
 * Exception Invalid 
 * @author lailaShreteh
 *
 */
public class BadFormulaException extends Exception {
/**
	 * 
	 */
	private static final long serialVersionUID = 8655666187495144056L;
private String message = "Sorry Bad Formula Exception X_X";
	
	public BadFormulaException(Error error) {
		super(error);
	}
	public String getErrorMessage() {
		return message;
	}
}
