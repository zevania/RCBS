package util.exception;



public class InvalidCredentialException extends Exception 
{
    public InvalidCredentialException() 
    {
    }

    
    
    public InvalidCredentialException(String msg) 
    {
        super(msg);
    }
}