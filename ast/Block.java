package ast;
import java.util.*;
import environment.*;

/**
 * Block does a block of code, taking in a list of statements
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class Block extends Statement
{
    List<Statement> stmts;
    
    /**
     * Constructor for objects of class Block
     * @param s the list of statements
    */
    public Block(List<Statement> s)
    {
        stmts = s;
    }
    
    /**
     * Evaluates statements
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        for (int i = 0; i < stmts.size(); i++)
        {
            stmts.get(i).exec(env);
        }
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        for (int i = 0; i < stmts.size(); i++)
        {
            stmts.get(i).compile(e);
        }
    }
}
