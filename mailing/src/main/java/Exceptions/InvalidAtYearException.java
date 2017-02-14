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
public class InvalidAtYearException extends RuntimeException{
    
    public InvalidAtYearException(){
        super();
    }
    
    public InvalidAtYearException(String s)
    {
        super(s);
    }
}
