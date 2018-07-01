package scanner;
import java.io.*;
import java.util.*;

/**
 * ScannerTester tests Scanner.
 *
 * @author Anna Wang
 * @version 2/8/18
 */
public class ScannerTester
{
    /**
     * @param args  argument
     */
    public static void main (String[]args) throws 
    FileNotFoundException, ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(
                new File("ScannerTest.txt"));
        Scanner lex = new Scanner(inStream);
        while (lex.hasNext())
            System.out.println(lex.nextToken());
        System.out.println(lex.nextToken());
    }
}

