package ast;
import environment.*;

/**
 * Assignment does an assignment block, taking in a string and an expression.
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;
    /**
     * Constructor for objects of class Assignment
     * @param v   the variable
     * @param e   the value
    */
    public Assignment(String v, Expression e)
    {
        var = v;
        exp = e;
    }
    
    /**
     * Sets a value with a variable.
     * @param env is the environment the AST Tree is in.
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        if (e.isLocalVariable(var))
        {
            e.emit("sw $v0 " + e.getOffset(var) + "($sp)");
        }
        else
        {
            e.emit("sw $v0 " + var + "\t #loading value of expression to v0");
        }
        
    }
}