package ast;
import environment.*;

/**
 * Variable class evaluates a string containing the string n.
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class Variable extends Expression
{
    private String name;
    /**
     * Constructor for objects of class Variable, taking in a string
     * @param n the string
     */
    public Variable(String n)
    {
        name = n;
    }

    /**
     * Evaluates the value.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        return env.getVariable(name);
    }

    /**
     * Prints out one line of code to the file, translating a variable to MIPS
     * @param e    the Emitter that outputs the MIPS file.
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(name))
        {
            e.emit("lw $v0 " + e.getOffset(name) + "($sp)");
        }
        else
        {
            e.emit("lw $v0 " + name);
        }
    }
}