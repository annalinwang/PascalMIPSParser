package ast;

/**
 * While does an while loop block
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class While extends Statement
{
    private Condition condition;
    private Statement statement;
    
    /**
     * Constructor for objects of a while statement
     * @param c is the condition
     * @param s is the statement
     */
    public While(Condition c, Statement s)
    {  
        condition = c;
        statement = s;
    }
    
    /**
     * Executes a While statement. 
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        while (condition.eval(env) == 1)
        {
            statement.exec(env);
        }
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        String label = "loop";
        condition.compile(e, label);
        e.emit("j exit");
        e.emit(label + ":");
        statement.compile(e);
        condition.compile(e, label);
        e.emit("exit:");
    }
}
