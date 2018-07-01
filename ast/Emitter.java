package ast;
import java.util.*;
import java.io.*;

/**
 * Emitter outputs MIPS instructions to a file.
 * 
 * @author Anna Wang
 * @version April 1 2018
 */
public class Emitter
{
    private PrintWriter out;
    private int nextLabel = 0;
    private ProcedureDeclaration current;
    private int excessStackHeight;

    /**
     * Creates an emitter for writing to a new file with given name
     * @param outputFileName is the file name
     */
    public Emitter(String outputFileName)
    {
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Prints one line of code to file (with non-labels indented)
     * @param code is the code to print out
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * Closes the file.  should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }

    /**
     * Pushes value onto stack of addresses by 
     * first subtracting 4 addresses
     * @param reg   the string to push
     */
    public void emitPush(String reg)
    {
        this.emit("subu $sp, $sp, 4");
        this.emit("sw " + reg + ", ($sp)" + "\t\t" +
            "#pushes value onto stack of addresses");
        excessStackHeight++;
    }

    /**
     * Pops value off of stack of addresses by
     * first adding 4 addresses
     * @param reg   the string to pop
     */
    public void emitPop(String reg)
    {
        this.emit("lw " + reg + ", ($sp)");
        this.emit("addu $sp, $sp, 4" + "\t\t" + 
            "#pop value off stack of addresses");
        excessStackHeight--;
    }

    /**
     * Gets the label number of the if statement
     * @return the label number of the if statement
     */
    public int getLabel()
    {
        return nextLabel;
    }

    /**
     * Gets the label number after incrementing it
     * @return the label number of the if statement
     */
    public int nextLabelID()
    {
        nextLabel++;
        return nextLabel;
    }

    /**
     * Remembers proc as current procedure context
     * @param proc  the procedure to remember
     */
    public void setProcedureContext(ProcedureDeclaration proc)
    {
        this.current = proc;
        excessStackHeight = 0;
    }

    /**
     * Clears the current procedure context (remember null)
     */
    public void clearProcedureContext()
    {
        this.current = null;
    }

    /**
     * Sees if the given variable corresponds to a local or global variable name
     * @return true if the given variable corresponds to a local 
     * variable name (which for now means that it's the name of one 
     * of the current procedure declaration's parameter names) or a 
     * global variable name (we'll assume all unknown names are global).
     * 
     * @param varName   the name of the variable
     */
    public boolean isLocalVariable(String varName)
    {
        if (current == null)
        {
            return false;
        }
        if (current.getName().equals(varName))
        {
            return true;
        }
        List<String> vars = current.getParams();
        for (int i = 0; i < vars.size(); i++)
        {
            if (varName.equals(vars.get(i)))
            {
                return true;
            }
        }
        List<String> localVars = current.getLocalVars();
        if (localVars.indexOf(varName) != -1)
        {
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the parameter that matches the given localVarName. 
     * precondition: localVarName is the name of a local variable 
     * for the procedure currently being compiled
     * @param localVarName  the name of the local variable
     * @return the index
     */
    public int getOffset(String localVarName)
    {
        List <String> params = current.getParams();
        List <String> localVars = current.getLocalVars();
        int loc = 0;
        //System.out.println(excessStackHeight);
        if (isLocalVariable(localVarName))
        {
            if (current.getName().equals(localVarName))
            {
                loc = excessStackHeight;
            }
            else if (current.getParams().contains(localVarName))
            {
                loc += excessStackHeight + current.getParams().size() - 
                    current.getParams().indexOf(localVarName);
            }
            else if (current.getLocalVars().contains(localVarName))
            {
                loc += excessStackHeight + 
                    current.getLocalVars().indexOf(localVarName) - 1;
            }
        }
        return loc * 4;
    }
}