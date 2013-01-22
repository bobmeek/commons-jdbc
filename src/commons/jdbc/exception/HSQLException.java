package commons.jdbc.exception;

import java.sql.SQLException;

/**
 * Subclass of DatabaseException with specific knowledge of various HSQL sql error codes.
 * Created on March 7, 2008
 * @author Jeff Smith
 */
public class HSQLException extends DatabaseException
{
	/**
	 * HSQLException Constructor
	 * @param msg exception message
	 * @param e SQLException
	 */
	public HSQLException (String msg, SQLException e)
	{
		super(msg, e);
	}

	/**
	 * HSQLException Constructor
	 * @param msg exception message
	 */
	public HSQLException (String msg)
	{
		super(msg);
	}

	/**
	 * Was db exception caused by bad sql grammer (a typo)
	 * return true or false
	 */
	public boolean isBadSQLGrammar()
	{
		return ( ((sqlErrorCode >= 67) && (sqlErrorCode <= 71)) || sqlErrorCode == 5 || sqlErrorCode == 11 || sqlErrorCode == 12 || 
				     sqlErrorCode == 13 || sqlErrorCode == 58 || sqlErrorCode == 74 || sqlErrorCode == 121);
	}

	/**
	 * Was db exception caused by a data integrity violation
	 * @return true or false
	 */
	public boolean isDataIntegrityViolation()
	{
		return((sqlErrorCode == 8) || (sqlErrorCode == 177));
	}

	/**
	 * Was db exception caused by database being unavailable
	 * @return true or false
	 */
	public boolean isDatabaseUnavailable()
	{
		return(((sqlErrorCode >= 1) && (sqlErrorCode <= 4)) || sqlErrorCode == 94);
	}

	/**
	 * Was db exception caused by referencing a invalid bind parameter name
	 * @return true or false
	 */
	public boolean isInvalidBindVariableName()
	{
		return(sqlErrorCode == 216);
	}

	/**
	 * Was db exception caused by referencing a non existent table or view
	 * @return true or false
	 */
	public boolean isNonExistentTableOrViewOrCol()
	{
		return(sqlErrorCode == 22 || sqlErrorCode == 28 || sqlErrorCode == 53);
	}

	
	/**
	 * Was db exception caused by a row lock error or some other sql query timeout
	 * return true or false
	 */
	public boolean isRowlockOrTimedOut()
	{
		return false;
	}

	/**
	 * Was db exception caused by a duplicate record (unique constraint) violation 
	 */
	public boolean isUniqueConstraintViolation()
	{
		return((sqlErrorCode == 9) || (sqlErrorCode == 104));
	}

	/** was db exception caused by a an unbound variable (parameter)
	 * @return boolean
	 */
	public boolean isVarParameterUnbound()
	{
		return false;
	}

}
