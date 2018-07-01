package environment;
import java.util.HashMap;
import java.util.*;
import ast.*;

/**
 * Creates an environment or a location to store variables
 *
 * @author Anna Wang
 * @version April 1 2018
 */
public class Environment
{
    // instance variables - replace the example below with your own
    private HashMap <String, Integer> variables;
    private HashMap <String, ProcedureDeclaration> procedures;
    private Environment parent;

    /**
     * Creates an environment with a HashMap for variables and procedures
     * and a parent environment
     * @param parent    the parent environment. null if none
     */
    public Environment(Environment parent)
    {
        variables = new HashMap<>();
        procedures = new HashMap<>();
        this.parent = parent;
    }
 
    /**
     * Returns the parent environment
     * @return  the parent environment
     */
    public Environment getParent()
    {
        return parent;
    }
    
    /**
     * Sets the a variable to a value
     * @param variable  the name of the variable being set
     * @param value     the value to be set
     */
    public void declareVariable (String variable, int value)
    {
        variables.put(variable, value);
    }
    
    /**
     * Sets the variable to value. If the variable is in the current environment
     * then it's set. Else, it's declared in the global environment
     * @param variable  the variable
     * @param value     the value
     */
    public void setVariable(String variable, int value)
    {
        Environment env = this.parent;
        if (variables.containsKey(variable) || env==null)
        {
            declareVariable(variable, value);
        }
        else
        {
            while (env.getParent()!=null)
            {
                env = env.getParent();
            }
            env.declareVariable(variable, value);
        }

    }

    /**
     * returns the value associated with the given variable
     * @param variable  the variable
     */
    public int getVariable(String variable)
    {
        if (variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        return parent.getVariable(variable);
    }

    /**
     * Gets the procedure with the name call
     * @param call  the name of the procedure
     */
    public ProcedureDeclaration getProcedure(String call)
    {
        if (procedures.containsKey(call))
        {
            return procedures.get(call);
        }
        return parent.getProcedure(call);
    }

    /**
     * Sets a procedure with a name to dec
     * @param name  the name of the procedure
     * @param dec   the declaration
     */
    public void setProcedure(String name, ProcedureDeclaration dec)
    {
        procedures.put(name, dec);
    }
}
