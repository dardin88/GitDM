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
public class QueryEmptyException extends RuntimeException
{
    public QueryEmptyException()
    {
        super();
    }
    
    public QueryEmptyException(String s)
    {
        super(s);
    }
    
}
