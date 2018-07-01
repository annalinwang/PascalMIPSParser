package ast;

/**
 * Condition evaluates a condition, taking in two expressions and a string.
 * @author Anna Wang
 * @version 3/20/18
 */
public class Condition
{
    private Expression expression1;
    private Expression expression2;
    private String op;

    /**
     * Constructor for objects of class condition
     * @param operator the string operator
     * @param first   the first expression
     * @param second   the second expression
     */
    public Condition(Expression first, Expression second, String operator)
    {
        expression1 = first;
        expression2 = second;
        op = operator;
    }

    /**
     * Evaluates the value.
     * @param env is the environment the AST is in.
     * @return the value
     */
    public int eval(environment.Environment env)
    {
        int x = expression1.eval(env);
        int y = expression2.eval(env);
        if (op.equals("="))
        {
            if (x == y)
            {
                return 1;
            }
        }
        else if (op.equals("<"))
        {
            if (x < y)
            {
                return 1;
            }
        }
        else if (op.equals(">"))
        {
            if (x > y)
            {
                return 1;
            }
        }
        else if (op.equals("<="))
        {
            if (x <= y)
            {
                return 1;
            }
        }
        else if (op.equals(">="))
        {
            if (x >= y)
            {
                return 1;
            }
        }
        else if (op.equals("<>"))
        {
            if (x != y)
            {
                return 1;
            }
        }
        return -1;
    }

    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     * @param label     the name of the label/place to jump to
     */
    public void compile(Emitter e, String label)
    {
        expression1.compile(e);
        e.emit("move $t1 $v0");
        expression2.compile(e);
        if (op.equals("="))
        {
            e.emit("beq $t1 $v0 " + label);
        }
        else if (op.equals("<>"))
        {
            e.emit("bne $t1 $v0 " + label);
        }
        else if (op.equals("<"))
        {
            e.emit("blt $t1 $v0 " + label);
        }
        else if (op.equals(">"))
        {
            e.emit("bgt $t1 $v0 " + label);
        }
        else if (op.equals("<="))
        {
            e.emit("ble $t1 $v0 " + label);
        }
        else if (op.equals(">="))
        {
            e.emit("bge $t1 $v0 " + label);
        }
    }
}
