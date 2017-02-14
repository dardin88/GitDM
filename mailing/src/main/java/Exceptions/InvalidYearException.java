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
public class InvalidYearException extends RuntimeException{
    
    public InvalidYearException()
    {
        super();
    }
    
    public InvalidYearException(String s)
    {
        super(s);
    }
}
