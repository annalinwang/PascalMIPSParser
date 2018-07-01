package parser;
import environment.*;
import java.io.*;
import java.util.*;
import ast.*;

/**
 * ParserTester tests Parser.
 *
 * @author Anna Wang
 * @version 2/8/18
 */
public class ParserTester
{
    /**
     * @param args  argument
     */
    public static void main (String[]args) throws 
    FileNotFoundException, scanner.ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(
                new File("/Users/Anna/Documents/Compilers/Lab/parser/parserTest8.txt"));
        scanner.Scanner lex = new scanner.Scanner(inStream);
        Parser p = new Parser(lex);
        Environment env = new Environment(null);
        Program prog = p.parseProgram();
        prog.compile("thing.s");
    }
}
