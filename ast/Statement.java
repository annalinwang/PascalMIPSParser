package ast;
import environment.*;

/**
 * Statement is a parent of all statements
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public abstract class Statement
{
    /**
     * Executes a statement. 
     * @param env is the environment the AST Tree is in.
     */
    public abstract void exec(environment.Environment env);
    
    /**
     * Converts the expression to MIPS
     * @param e outputs the MIPS file
     */
    public abstract void compile (Emitter e);
}