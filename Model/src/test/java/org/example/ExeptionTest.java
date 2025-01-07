package org.example;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExeptionTest {
    @Test
    public void NoBoardFoundExceptionTest() {
        try{
            throw new IOException("Błąd");
        } catch(IOException e){
            ObjectNotFoundException exeption = new ObjectNotFoundException("wyjątek", e);
            assertEquals(exeption.getMessage(), "wyjątek");
            assertEquals(exeption.getCause().getClass(), IOException.class);
        }
    }

    @Test
    public void TransactionFailedExceptionTest() {
        try{
            throw new IOException("Błąd");
        } catch(IOException e){
            TransactionFailedException exeption = new TransactionFailedException("wyjątek", e);
            assertEquals(exeption.getMessage(), "wyjątek");
            assertEquals(exeption.getCause().getClass(), IOException.class);
        }
    }
}
