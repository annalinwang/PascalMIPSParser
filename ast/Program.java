package ast;
import java.util.*;
import environment.*;
import java.io.*;
import ast.*;

/**
 * Program defines a program with procedure declarations and a statement
 *
 * @author Anna Wang
 * @version April 1 2018
 */
public class Program
{
    private List<ProcedureDeclaration> procedures;
    private Statement statement;
    private List<String> variables;

    /**
     * Creates a Program with procedures and a statement
     * @param procedures    the list of procedures
     * @param statement     the main statement
     * @param vars          the list of variables
     */
    public Program(List<ProcedureDeclaration> procedures, 
    Statement statement, List<String> vars)
    {
        this.procedures = procedures;
        this.statement = statement;
        variables = vars;
    }

    /**
     * Executes the procedure declarations and then the main statement
     * @param env   the environment for storing variables
     */
    public void exec (Environment env)
    {
        for (int i = 0; i < procedures.size(); i++)
        {
            procedures.get(i).exec(env);
        }
        statement.exec(env);
    }

    /**
     * Prints out the code to the file,.
     * @param name    the name of the output.
     */
    public void compile (String name)
    {
        Emitter e = new Emitter(name);
        e.emit("#author Anna Wang");
        e.emit("#version 3/20/18");
        e.emit(".data");
        e.emit("line: .asciiz " + "\"\\n\"");
        for (int i = 0; i < variables.size(); i++)
        {
            e.emit(variables.get(i) + ": .word 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        statement.compile(e);
        e.emit("li $v0, 10");
        e.emit("syscall");
        for (int i = 0; i < procedures.size(); i++)
        {
            procedures.get(i).compile(e);
        }  
        e.close();
    }
}
