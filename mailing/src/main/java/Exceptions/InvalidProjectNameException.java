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
public class InvalidProjectNameException extends RuntimeException
{
    public InvalidProjectNameException()
    {
        super();
    }
    
    public InvalidProjectNameException(String s)
    {
        super(s);
    }
}
