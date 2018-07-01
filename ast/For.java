package ast;

/**
 * For does a for loop block
 *
 * @author Anna Wang
 * @version 3/20/18
 */
public class For extends Statement
{
    private String id;
    private Expression start;
    private Expression end;
    private Statement statement;

    /**
     * Constructor for objects of a for statement
     * @param id is the id
     * @param st is the starting expression
     * @param en is the ending expression
     * @param state is the statement
     */
    public For(String id, Expression st, Expression en, Statement state)
    {  
        this.id = id;
        start = st;
        end = en;
        statement = state;
    }

    /**
     * Executes a For statement. 
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        env.setVariable(id, start.eval(env));
        for (int variable = start.eval(env); 
            variable < end.eval(env); variable++)
        {
            statement.exec(env);
            env.setVariable(id, env.getVariable(id) + 1);
        }
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
