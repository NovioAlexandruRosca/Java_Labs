package types.homework;

public class CommonException extends Exception {
    // Author: Rosca Alexandru-David 
    // Group: A4
    // Year: 2nd
    public CommonException(String message) {
        super((message == null) ? "Error" : message);
    }
}