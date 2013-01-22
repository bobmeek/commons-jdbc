package commons.jdbc.exception;
import java.sql.SQLException;

/**
 * Subclass of DatabaseException with specific knowledge of various PostgreSQL sql error codes.
 * Created on 22 may 2005, 15.15
 * @author Paolo Orru (paolo.orru@gmail.com)
 */
public class PostgreSQLException extends DatabaseException
{
	/**
	 * PostgreSQLException Constructor
	 * @param msg exception message
	 * @param e SQLException
	 */
	public PostgreSQLException (String msg, SQLException e)
	{
		super(msg, e);
	}

	/**
	 * PostgreSQLException Constructor
	 * @param msg exception message
	 */
	public PostgreSQLException (String msg)
	{
		super(msg);
	}

	/**
	 * Was db exception caused by a data integrity violation
	 * @return true or false
	 */
	public boolean isDataIntegrityViolation ()
	{
		//Class 23 - Integrity Constraint Violation
		return(SQLState.startsWith("23"));
	}

	/**
	 * Was db exception caused by a duplicate record (unique constraint) violation 
	 */
	public boolean isUniqueConstraintViolation()
	{
		return(sqlErrorCode == 23505);
	}


	/**
	 * Was db exception caused by bad sql grammer (a typo)
	 * return true or false
	 */
	public boolean isBadSQLGrammar ()
	{
		if (SQLState.equals("42000")) // SYNTAX ERROR OR ACCESS RULE VIOLATION
			return (true);
		else if (SQLState.equalsIgnoreCase("42601")) // SYNTAX ERROR
			return (true);
		else
			return(false);
	}

	/**
	 * Was db exception caused by referencing a non existent table or view
	 * @return true or false
	 */
	public boolean isNonExistentTableOrViewOrCol ()
	{
		if (SQLState.equalsIgnoreCase("42P01")) // UNDEFINED TABLE
			return (true);
		if (SQLState.equals("42703")) // UNDEFINED COLUMN
			return (true);
		else
			return(false);
	}

	/**
	 * Was db exception caused by referencing a invalid bind parameter name
	 * @return true or false
	 */
	public boolean isInvalidBindVariableName ()
	{
		return(false);
	}

	/**
	 * Was db exception caused by database being unavailable
	 * @return true or false
	 */
	public boolean isDatabaseUnavailable ()
	{
		if (SQLState.equalsIgnoreCase("3D000")) // INVALID CATALOG NAME
			return (true);
		if (SQLState.startsWith("08")) // Class 08 - Connection Exception
			return (true);
		else
			return(false);
	}

	/**
	 * Was db exception caused by a row lock error or some other sql query timeout
	 * return true or false
	 */
	public boolean isRowlockOrTimedOut ()
	{
		return(SQLState.equalsIgnoreCase("40P01")); // DEADLOCK DETECTED
	}

	/** was db exception caused by a an unbound variable (parameter)
	 * @return boolean
	 */
	public boolean isVarParameterUnbound ()
	{
		return(SQLState.equals ("22023")); // INVALID PARAMETER VALUE
	}
}
