package com.madsen;

import java.util.Stack;

public class Main
{
    static long fibonacci(int n)
    {
        int[] stack = new int[256];
        int stackPointer = 0;
        //0 is begining 1 is first call 2 is second call 3 is end
        int returnPointer = 0;
        long retReg = 0;
        int aReg = n;
        int bReg = 0;

        //The main function "pushing" its return pointer
        stack[stackPointer++] = 3;
        while (true)
        {
            if (returnPointer == 0)
            {
                if (aReg < 1)
                {
                    retReg = 1;
                    //"return"
                    returnPointer = stack[--stackPointer];
                    continue;
                }
                stack[stackPointer++] = bReg;
                bReg = aReg;
                aReg--;

                //"call #1"
                stack[stackPointer++] = 1;
                returnPointer = 0;
            }
            else if (returnPointer == 1)
            {
                stack[stackPointer++] = (int) retReg;
                stack[stackPointer++] = (int) (retReg >> 32);
                aReg = bReg - 2;
                //"call #2"
                stack[stackPointer++] = 2;
                returnPointer = 0;
            }
            else if (returnPointer == 2)
            {

                retReg += (long) stack[--stackPointer] << 32;
                retReg += stack[--stackPointer];
                bReg = stack[--stackPointer];

                //"return"
                returnPointer = stack[--stackPointer];
            }
            else if (returnPointer == 3)
            {
                break;
            }
            else
            {
                throw new RuntimeException("AAAa");
            }
        }
        return retReg;
    }

    static long factorial(int n)
    {

        int[] stack = new int[100];
        int stackPointer = 0;
        //0 is begining 1 is the call 2 is end
        int returnPointer = 0;
        long retReg = 0;
        int aReg = n;
        int bReg = 0;

        //The main function "pushing" its return pointer
        stack[stackPointer++] = 2;
        while (true)
        {
            if (returnPointer == 0)
            {
                if (aReg < 1)
                {
                    retReg = 1;
                    //"return"
                    returnPointer = stack[--stackPointer];
                    continue;
                }
                stack[stackPointer++] = bReg;
                bReg = aReg;
                aReg--;

                //"call"
                stack[stackPointer++] = 1;
                returnPointer = 0;
            }
            else if (returnPointer == 1)
            {
                retReg *= bReg;
                bReg = stack[--stackPointer];

                //"return"
                returnPointer = stack[--stackPointer];
            }
            else if (returnPointer == 2)
            {
                break;
            }
        }
        return retReg;
    }

    public static void main(String[] args)
    {
        for (int i = 0; i < 40; i++)
        {
            //System.out.println("fib(" + i + ")=" + fibonacci(i));
        }
        for (int i = 0; i < 20; i++)
        {
            //System.out.println(i + "!=" + factorial(i));
        }
    }
}
