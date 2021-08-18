package com.madsen;

public class RealVersions
{
    static void sort(int[] a, int l, int r)
    {
        // 0: the begining
        int m = (r + l) / 2;
        if (l < r)
        {
            sort(a, l, m);
            // 1: after first recursive call
            sort(a, m + 1, r);

        }
        // 2: second recursive call
        int[] T = new int[r - l + 1];

        int tp = 0;
        int lp = l;
        int rp = m + 1;
        while (lp <= m && rp <= r)// 3: comparison loop
        {
            if (a[lp] < a[rp])
            {
                T[tp++] = a[lp];
                lp++;
            }
            else
            {
                T[tp++] = a[rp];
                rp++;
            }
        }
        while (lp <= m)// 4: left filling loop
        {
            T[tp++] = a[lp];
            lp++;
        }
        while (rp <= r)// 5: right filling loop
        {
            T[tp++] = a[rp];
            rp++;
        }
        for (int i = l; i <= r; i++)//6: transfer loop
        {
            a[i] = T[i - l];
        }
    }

    static void merge(int[] a)
    {
        sort(a, 0, a.length - 1);
    }
}
