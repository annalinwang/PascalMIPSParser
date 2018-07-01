package ast;
import environment.*;

/**
 * BinOp evaluates a binary operator, taking in a string and two expressions
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;
    
    /**
     * Constructor for objects of class BinOp
     * @param s the string
     * @param expression1   the first expression
     * @param expression2   the second expression
     */
    public BinOp(String s, Expression expression1, Expression expression2)
    {
        op = s;
        exp1 = expression1;
        exp2 = expression2;
    }
    
    /**
     * Evaluates the value.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        if (op.equals("*"))
        {
            return exp1.eval(env) * exp2.eval(env);
        }
        else if (op.equals("+"))
        {
            return exp1.eval(env) + exp2.eval(env);
        }
        else if (op.equals("-"))
        {
            return exp1.eval(env) - exp2.eval(env);
        }
        else //if (op.equals("/"))
        {
            return exp1.eval(env) / exp2.eval(env);
        }
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (op.equals("+"))
        {
            e.emit("addu $v0, $t0, $v0" + "\t #adds");
        }
        else if (op.equals("-"))
        {
            e.emit("subu $v0, $t0, $v0" + "\t #subtracts");
        }
        else if (op.equals("*"))
        {
            e.emit("mult $v0, $t0");
            e.emit("mflo $v0" + "\t #multiplies");
        }
        else //if (op.equals("/"))
        {
            e.emit("div $v0, $t0");
            e.emit("mflo $v0" + "\t #divides");
        }
    }
}
