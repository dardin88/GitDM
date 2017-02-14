/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author depiano.it
 */
public class InvalidMonthException extends RuntimeException{
    
    public InvalidMonthException()
    {
        super();
    }
    
    public InvalidMonthException(String s)
    {
        super(s);
    }
}
