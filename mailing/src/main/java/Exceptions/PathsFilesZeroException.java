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
public class PathsFilesZeroException extends RuntimeException{
    
    public PathsFilesZeroException()
    {
        
    }
    
    public PathsFilesZeroException(String s)
    {
        super(s);
    }
}
