package ast;
import java.util.*;

/**
 * ProcedureDeclaration makes a procedure declaration a name, 
 * parameters, and statements
 *
 * @author Anna Wang
 * @version April 1 2018
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private List<String> params;
    private Statement statement;
    private List<String> localVars;
    /**
     * Creates a procedure declaration
     * @param name      the name of the procedure
     * @param stmt      the statement of the procedure
     * @param params    the parameters of the procedure
     * @param vars      the list of local variables
     */
    public ProcedureDeclaration(String name, Statement stmt, 
    List<String>params, List<String>vars)
    {
        this.name = name;
        this.params = params;
        this.statement = stmt;
        this.localVars = vars;
    }

    /**
     * Returns the list of parameters
     * @return  the parameter list
     */
    public List<String> getParams()
    {
        return params;
    }

    /**
     * Returns the statement
     * @return  the statement
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Returns the local variables
     * @return  the local variables
     */
    public List<String> getLocalVars()
    {
        return localVars;
    }

    /**
     * Returns the name
     * @return  the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Executes the procedure, setting its value and environment
     * @param env   the Environment for storing variables
     */
    public void exec(environment.Environment env)
    {
        env.setProcedure(name, this);
    }

    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        e.emit("proc" + name + ":");
        e.emit ("li $v0 0");
        e.emitPush("$v0");
        e.setProcedureContext(this);       
        for (int i = 0; i < localVars.size(); i++)
        {
            e.emitPush("$t0");
        }
        e.emitPush("$ra");
        statement.compile(e); 
        e.emitPop("$ra");
        for (int i = 0; i < localVars.size(); i++)
        {
            e.emitPop("$a1");
        }                
        e.emit("jr $ra");
        e.emitPop("$v0");
        e.clearProcedureContext();
    }
}
