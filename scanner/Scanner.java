package scanner;

import java.io.*;
/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) 
 * lab exercise 1
 * @author Anna Wang
 * @version January 30th 2018
 * Usage: use this to scan files
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance 
     * field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * This helper method sets currentChar to the next value by 
     * setting currentchar to the value of the stream's read. If it's 
     * -1, then end of file and eof is set true.
     */
    private void getNextChar()
    {
        int x = 0;
        try
        {
            x = in.read();
            if (x == -1)
            {
                eof = true;
            }
            else
            {
                currentChar = (char)x;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Compares value of expected input to currentChar. If they're 
     * the same then it advances the input stream with getNextChar. 
     * Else, ScanErrorException thrown.
     * @param expected  the expected character
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException
            ("Illegal character- expected <currentChar> and found <char>");
        }
    }

    /**
     * Sees if x is a digit
     * @param x the character
     * @return true if it's a digit, else false
     */
    public static boolean isDigit (char x)
    {
        return (x >= '0' && x <= '9');
    }

    /**
     * Sees if x is a letter
     * @param x the character
     * @return true if it's a letter, else false
     */
    public static boolean isLetter(char x)
    {
        return ((x >= 'a' && x <= 'z' ) || (x >= 'A' && x <= 'Z' ));
    }

    /**
     * Sees if x is a white space/special character
     * @param x the character
     * @return true if it's a white space or special character, else false
     */
    public static boolean isWhiteSpace(char x)
    {
        return (x == ' ' || x == '\t' || x == '\r' || x == '\n' );
    }

    /**
     * Sees if x is an operand
     * @param x the character
     * @return true if it's an operand, else false
     */
    public static boolean isOperand(char x)
    {
        return (x == '=' || x == '+' || x == '-' || 
            x == '*' || x == '/' || x == '%' || 
            x == '(' || x == ')' || x == ';' || 
            x == '<' || x == '>' || x == ',' || x == ':' || x == '.');
    }

    /**
     * Sees if it's end of file or not yet
     * @return  true if not end of file, else false.
     */
    public boolean hasNext()
    {
        return !eof && currentChar != '.';
    }

    /**
     * Scans number
     * @return  the number
     */
    private String scanNumber() throws ScanErrorException
    {
        String number = "";
        while (hasNext() && isDigit(currentChar))
        {
            number += currentChar;
            eat(currentChar);
        }
        return number;
    }

    /**
     * Scans identifier
     * @return  the identifier
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String identifier = "";
        while (hasNext() && (isDigit(currentChar) || isLetter(currentChar)))
        {
            identifier += currentChar;
            eat(currentChar);
        }
        return identifier;
    }

    /**
     * Scans operand
     * @return  the operand
     */
    private String scanOperand() throws ScanErrorException
    {
        String s = "";
        if (isOperand(currentChar))
        {
            if (currentChar == '<')
            {
                s = s + currentChar;
                eat(currentChar);
                if (currentChar == '>' || currentChar == '=')
                {
                    s = s + currentChar;
                    return s;
                }
                return s;
            }
            if (currentChar == '>')
            {
                s = s + currentChar;
                eat(currentChar);
                if (currentChar == '=')
                {
                    s = s + currentChar;
                    return s;
                }
                return s;
            }
            s = s + currentChar;
        }
        return s;
    }

    /**
     * Creates a token from the currentChar and specifies its type
     * @return a new token with the value of currentChar and the correct type
     */
    public String nextToken() throws ScanErrorException
    {
        String s = "";
        if (!hasNext())
        {
            return "END";
        }
        while (hasNext() && isWhiteSpace(currentChar))
        {
            eat(currentChar);
        }
        if (isDigit(currentChar))
        {
            s = scanNumber();
        }
        else if (isLetter(currentChar))
        {
            s = scanIdentifier();
        }
        else if (currentChar == ':')
        {
            s += currentChar;
            eat(currentChar);
            s += currentChar;
            eat(currentChar);
        }
        else
        {
            s = scanOperand();
            eat(currentChar);
        }
        return s;
    }
}

