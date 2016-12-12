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
public class ProjectNameNullException extends RuntimeException
{
    public ProjectNameNullException()
    {
        super();
    }
    
    public ProjectNameNullException(String s)
    {
        super(s);
    }
    
}
