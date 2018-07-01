package ast;
import environment.*;

/**
 * If does an if block
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class If extends Statement
{
    private Condition condition;
    private Statement statement;
    /**
     * Constructor for objects of an if statement
     * @param c is the condition
     * @param s is the statement
     */
    public If(Condition c, Statement s)
    {
        condition = c;
        statement = s;
    }
    
    /**
     * Executes an If statement. 
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        if (condition.eval(env) == 1)
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
        String label = "endif" + e.nextLabelID();
        condition.compile(e, label);
        int l = e.getLabel() + 1;
        e.emit("j merge" + label);
        e.emit(label + ":");
        statement.compile(e);        
        e.emit("j merge" + label);
        e.emit("merge" + label + ":");       
    }
}
