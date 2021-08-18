package com.madsen;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Main
{
    static void mergeSort(int[] a)
    {
        int[] stack = new int[4096 * 8];
        int stackPointer = 0;
        // 0 is begining, 1 is first recursive call, 2 is second recursive call, 3 is the comparison loop,
        // 4 is left filling loop, 5 is right filling loop, 6 is the transfer loop, 7 is end
        int returnPointer = 0;
        long retReg = 0;
        // a-c are parameter registers
        int aReg = 0;
        int bReg = 0;
        int cReg = 0;
        int dReg = 0;
        int eReg = 0;
        int fReg = 0;
        int gReg = 0;
        int hReg = 0;


        //putting array on stack
        aReg = stackPointer;
        for (int i : a)
        {
            stack[stackPointer++] = i;
        }
        cReg = stackPointer - aReg - 1;
        //System.out.println(cReg);

        //The main function "pushing" its return pointer
        stack[stackPointer++] = 7;
        while (true)
        {
            if (returnPointer == 0)
            {
                stack[stackPointer++] = dReg;
                stack[stackPointer++] = eReg;
                stack[stackPointer++] = fReg;

                dReg = (bReg + cReg) / 2;
                eReg = bReg;
                fReg = cReg;
                if (bReg < cReg)
                {
                    bReg = eReg;
                    cReg = dReg;
                    //"call #0 then return to #1"
                    stack[stackPointer++] = 1;
                    returnPointer = 0;
                }
                else
                {
                    returnPointer = 2;
                }
            }
            else if (returnPointer == 1)
            {
                bReg = dReg + 1;
                cReg = fReg;
                //"call #0 then return to #2"
                stack[stackPointer++] = 2;
                returnPointer = 0;
            }
            else if (returnPointer == 2)
            {
                //Create tmp array at bReg with size fReg - eReg + 1
                bReg = stackPointer;
                stackPointer += fReg - eReg + 1;
                cReg = 0;     //offset for how much of the temp is filled ASSUMING C REG IS NEEDED
                gReg = eReg;  //lp
                hReg = dReg + 1;    //rp
                returnPointer = 3;  //start comparison loop
            }
            else if (returnPointer == 3)
            {
                if (gReg <= dReg && hReg <= fReg)
                {
                    if (stack[aReg + gReg] < stack[aReg + hReg])
                    {
                        stack[bReg + cReg] = stack[aReg + gReg];
                        cReg++;
                        gReg++;
                    }
                    else
                    {
                        stack[bReg + cReg] = stack[aReg + hReg];
                        cReg++;
                        hReg++;
                    }
                    returnPointer = 3;
                }
                else
                {
                    returnPointer = 4;
                }
            }
            else if (returnPointer == 4)
            {
                if (gReg <= dReg)
                {
                    stack[bReg + cReg] = stack[aReg + gReg];
                    cReg++;
                    gReg++;
                    returnPointer = 4;
                }
                else
                {
                    returnPointer = 5;
                }
            }
            else if (returnPointer == 5)
            {
                if (hReg <= fReg)
                {
                    stack[bReg + cReg] = stack[aReg + hReg];
                    cReg++;
                    hReg++;
                    returnPointer = 5;
                }
                else
                {
                    //initialize the next for loop
                    cReg = eReg;
                    returnPointer = 6;
                }
            }
            else if (returnPointer == 6)
            {
                if (cReg <= fReg)
                {
                    stack[aReg + cReg] = stack[bReg + cReg - eReg];
                    cReg++;
                    returnPointer = 6;
                }
                else
                {
                    stackPointer -= fReg - eReg + 1;


                    fReg = stack[--stackPointer];
                    eReg = stack[--stackPointer];
                    dReg = stack[--stackPointer];
                    returnPointer = stack[--stackPointer];
                }
            }
            else if (returnPointer == 7)
            {
                //I may "improve" this later
                for (int i = stackPointer; i > 0; i--)
                {
                    a[i - 1] = stack[i - 1];
                }
                break;
            }
        }
    }


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

                //"call #0 then return to #1"
                stack[stackPointer++] = 1;
                returnPointer = 0;
            }
            else if (returnPointer == 1)
            {
                stack[stackPointer++] = (int) retReg;
                stack[stackPointer++] = (int) (retReg >> 32);
                aReg = bReg - 2;
                //"call #0 then return to #2"
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

        ///*
        for (int i = 1; i < 10000; i++)
        {
            int[] a = new Random().ints(i, 0, 1000).toArray();
            int[] b = Arrays.copyOf(a, a.length);
            Arrays.sort(b);
            mergeSort(a);
            if (Arrays.equals(a, b))
            {
                System.out.println(true);
            }
        }//*/
    }
}
