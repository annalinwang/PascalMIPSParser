package ast;
import environment.*;

/**
 * Writeln writes a line, taking in an expression.
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Constructor for objects of class writeln
     * @param exp   the expression to write
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Executes a writeln statement.
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        System.out.println(exp.eval(env));
    }

    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0, line");
        e.emit("li $v0 4");
        e.emit("syscall");
    }
}
