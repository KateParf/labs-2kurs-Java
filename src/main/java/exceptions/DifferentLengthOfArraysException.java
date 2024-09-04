package exceptions; // пакет исключений

public class DifferentLengthOfArraysException extends RuntimeException
{
    public DifferentLengthOfArraysException()
    {
        super();
    }

    public DifferentLengthOfArraysException(String message)
    {
        super(message);
    }
}