package exceptions; // пакет исключений

public class InterpolationException extends RuntimeException
{
    public InterpolationException()
    {
        super();
    }

    public InterpolationException(String message)
    {
        super(message);
    }
}