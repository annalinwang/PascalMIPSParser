package ast;
import scanner.*;
import environment.Environment;
import java.util.Scanner;

/**
 * Readln reads a line of code, taking in a string.
 * @author Anna Wang
 * @version 3/20/18
 */
public class Readln extends Statement
{
    private String id;
    
    /**
     * Constructor for objects of class readln
     * @param id   the id
     */
    public Readln(String id)
    {
        this.id = id;
    }
    
    /**
     * Executes a readln statement.
     * @param env is the environment the AST Tree is in.
     */
    public void exec(environment.Environment env)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter value of " + id + ": ");
        env.setVariable(id, Integer.parseInt(sc.nextLine()));
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
