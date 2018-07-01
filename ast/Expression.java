package ast;
import environment.*;

/**
 * Expression is a parent of all expressions
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public abstract class Expression
{    
    /**
     * Evaluates the expression.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public abstract int eval(environment.Environment env);
    
    /**
     * Converts the expression to MIPS
     * @param e outputs the MIPS file
     */
    public abstract void compile (Emitter e);
    
}
