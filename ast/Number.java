package ast;
import environment.*;

/**
 * Number class evaluates a number that contains a value.
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class Number extends Expression
{
    private int value;
    
    /**
     * Constructor for objects of class Number, taking in an integer
     * @param v   the number
    */
    public Number(int v)
    {
        value = v;
    }
    
    /**
     * Returns the value.
     * @param env is the environment the AST is in.
     * @return number
     */
    public int eval(environment.Environment env)
    {
        return value;
    }
    
    /**
     * Prints out one line of code to the file, loading the 
     * integer value into v0, 
     * @param e    the Emitter that writes the file.
     */
    public void compile (Emitter e)
    {
        e.emit("li $v0 " + Integer.toString(value));
    }
}
