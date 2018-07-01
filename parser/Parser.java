package parser;
import java.util.HashMap;
import java.util.*;
import ast.*;
import environment.*;
import scanner.*;

/**
 * Parser Class creates a parser that will parse 
 * an input file and return the values
 * @author Anna Wang
 * @version March 6
 */
public class Parser
{
    private scanner.Scanner scan;
    private String currentToken;
    private HashMap <String, Expression> map;

    /**
     * Creates a parser object 
     * @param x the scanner that will scan the input
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Parser(scanner.Scanner x) throws ScanErrorException
    {
        scan = x;
        currentToken = x.nextToken();
        map = new HashMap<String, Expression>();
    }

    /**
     * Takes in the expected token and if it matches current token, 
     * replaces current token by asking scanner for next token. 
     * If they don't match, it instead throws an illegal argument expression
     * @param expectedToken the expected token
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    private void eat(String expectedToken) throws ScanErrorException
    {
        if (expectedToken.equals(currentToken))
        {
            currentToken = scan.nextToken();
        }
        else
        {
            throw new IllegalArgumentException("Expected " + expectedToken
                + " but found " + currentToken); //change later
        }
    }

    /**
     * With the precondition that the current token is an integer, this
     * parses that number, effectively eating it.
     * @return the value of the parsed integer
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    private Expression parseNumber() throws ScanErrorException
    {
        Expression num = new ast.Number(Integer.parseInt(currentToken));
        eat(currentToken);
        return num;
    }

    /**
     * Parses an expression with addition or subtraction.
     * @return the value of the parsed expression
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseExpression () throws ScanErrorException
    {
        Expression val = parseTerm();
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                //val = val + parseTerm();
                val = new BinOp("+", val, parseTerm());
            }
            if (currentToken.equals("-"))
            {
                eat("-");
                //val = val - parseTerm();
                val = new BinOp("-", val, parseTerm());
            }
        }
        return val;
    }

    /**
     * Parses a statement containing numbers or symbols 
     * like parenthesis or others, effectively using recursion 
     * to return the expression.
     * @return the value of the parsed factor.
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseFactor() throws ScanErrorException
    {
        if (currentToken.equals("-"))
        {
            eat("-");
            //return -1 * parseFactor();
            return new BinOp("-", new ast.Number(0), parseFactor());
        }
        else if (currentToken.equals("("))
        {
            eat("(");
            Expression num = parseExpression();
            eat(")");
            return num;
        }
        else if (scan.isDigit(currentToken.charAt(0)))
            return parseNumber();
        else
        {
            String var = currentToken;
            eat(currentToken);
            if (currentToken.equals("("))
            {
                List<Expression> params = new ArrayList<Expression>();
                eat("(");
                while (!currentToken.equals(")"))
                {
                    params.add(parseExpression());
                    if (currentToken.equals(","))
                    {
                        eat(",");
                    }
                }
                eat(")");
                return new ProcedureCall(var, params);
            }
            return new Variable(var);
        }
    }

    /**
     * Parses a whole term that includes division or
     * multiplication (calls parseFactor inside)
     * @return  the parsed term
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Expression parseTerm()throws ScanErrorException
    {
        Expression val = parseFactor();
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                //val = val * parseFactor();
                val = new BinOp("*", val, parseFactor());
            }
            if (currentToken.equals("/"))
            {
                eat("/");
                //val = val / parseFactor();
                val = new BinOp("/", val, parseFactor());
            }
        }
        return val;
    }

    /**
     * Parses the whole statement
     * precondition: currentToken is the start
     * postcondition: the expression is eaten and currentToken is the next token
     * @return  the statement
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if (currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            List<Statement> statements = new ArrayList<Statement>();
            while (!currentToken.equals("END") && !currentToken.equals("."))
            {
                statements.add(parseStatement());
            }
            eat("END");
            eat(";");
            return new ast.Block(statements);
        }
        else if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if (currentToken.equals("IF"))
        {
            eat(currentToken); 
            Expression exp1 = parseExpression(); 
            String operator = currentToken; 
            eat(operator);
            Expression exp2 = parseExpression(); 
            Condition cond = new Condition(exp1, exp2, operator);
            eat("THEN");
            Statement statement = parseStatement();
            return new If(cond, statement);
        }
        else if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Expression exp1 = parseExpression();
            String operator = currentToken;
            eat(operator);
            Expression exp2 = parseExpression();
            Condition cond = new Condition(exp1, exp2, operator);
            eat("DO");
            Statement stm = parseStatement();
            return new While(cond, stm);
        }
        String name = currentToken;
        eat(currentToken);
        eat(":=");
        Expression e = parseExpression();
        eat(";");
        return new Assignment(name, e);
    }

    /**
     * Parses the procedure declarations and returns a Program 
     * @return  a Program that contains the procedures and statements
     * @throws  ScanErrorException if there's a character it doesn't recognize
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<String> globVariables = new LinkedList<String>();
        while (currentToken.equals("VAR"))
        {
            eat(currentToken);
            while (!currentToken.equals(";"))
            {
                globVariables.add(currentToken);             
                eat(currentToken);
                if (currentToken.equals(","))
                {
                    eat(currentToken);
                }
            }
            eat(";");
        }      
        List<ProcedureDeclaration> procedures = 
            new ArrayList<ProcedureDeclaration>();
        while (currentToken.equals("PROCEDURE"))
        {
            parseProcedure(procedures);
        }
        Statement stmt = parseStatement();
        return new Program(procedures, stmt, globVariables);
    }

    /**
     * Parses the local variables of the procedure
     * @param procedures    the list of procedures
     */
    public void parseProcedure(List<ProcedureDeclaration> procedures) throws ScanErrorException
    {
        List<String> params = new ArrayList<String>();
        eat("PROCEDURE");
        String name = currentToken;
        eat(name);
        eat("(");
        while (!currentToken.equals(")"))
        {
            params.add(currentToken);
            eat(currentToken);
            if (currentToken.equals(","))
            {
                eat(",");
            }
        }
        eat(")");
        eat(";");
        List<String> localVariables = new ArrayList<String>();
        if (currentToken.equals("VAR"))
        {
            eat(currentToken);
            while (!currentToken.equals(";"))
            {
                localVariables.add(currentToken);             
                eat(currentToken);
                if (currentToken.equals(","))
                {
                    eat(currentToken);
                }
            }
            eat(";");
        }
        Statement stmt = parseStatement();
        procedures.add(new ProcedureDeclaration(name, stmt, 
                params, localVariables));
    }
}
