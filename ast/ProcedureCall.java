package ast;
import java.util.*;
import environment.*;

/**
 * ProcedureCall makes a procedure with parameters and return values
 *
 * @author Anna Wang
 * @version April 1 2018
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> params;

    /**
     * Creates a ProcedureCall with a name and parameters
     * @param name      the name of procedure
     * @param params    the parameters
     */
    public ProcedureCall(String name, List<Expression>params)
    {
        this.name = name;
        this.params = params;
    }

    /**
     * Evaluates the ProcedureCall, making a sub environment to store
     * variables and executes the proceduredeclaration and returns value
     * @param env   the Environment storing the variables
     * @return      the value of the procedure
     */
    public int eval(Environment env)
    {
        Environment subEnvironment = new Environment(env);
        ProcedureDeclaration p = env.getProcedure(name);
        List<String> parameters = p.getParams();
        subEnvironment.declareVariable(name, 0);
        for (int i = 0; i < params.size(); i++)
        {
            subEnvironment.declareVariable(parameters.get(i), 
                params.get(i).eval(env));
        }
        p.getStatement().exec(subEnvironment);
        return subEnvironment.getVariable(name);
    }
    
    /**
     * Prints out one line of code to the file
     * @param e    the Emitter that writes the file.
     */
    public void compile(Emitter e)
    {
        e.emitPush("$ra");
        for (int i = 0; i < params.size(); i++)
        {
            params.get(i).compile(e);
            e.emitPush("$v0");
        }
        e.emit("jal proc" + name);
        for (int i = 0; i < params.size(); i++)
        {
            e.emitPop("$a0");
        }
        e.emitPop("$ra");
    }
}
