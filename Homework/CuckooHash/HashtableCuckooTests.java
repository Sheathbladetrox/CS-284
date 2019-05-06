package CuckooHash;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashtableCuckooTests
{
    @Test
    @DisplayName("Test 1")
    void test1()
    {
        HashtableCuckoo<String,Character> ht = new HashtableCuckoo<String, Character>();
        String[] arr1 = {"a" , "p", "w", "t", "s"};
        String[] arr2 = {"d", "n", "z", "k", "r"};
        String[] arr3 = {"b", "c", "e", "g", "h"};

        for(int i=0; i<5; i++)
        {
            String str = arr1[i] + arr2[i] + arr3[i];
            ht.put(str,'a');

            String str1 = arr2[i] + arr3[i] + arr1[i];
            ht.put(str1,'b');

            String str2 = arr3[i] + arr2[i] + arr1[i];
            ht.put(str2,'c');
        }

        ht.remove("kgt");
        ht.remove("hrs");
        StringBuilder result = new StringBuilder();
        result.append("[12,zew,b,table1]\n");
        result.append("[21,cnp,c,table1]\n");
        result.append("[33,srh,a,table1]\n");
        result.append("[42,ezw,c,table1]\n");
        result.append("[51,ncp,b,table1]\n");
        result.append("[55,adb,a,table1]\n");
        result.append("[56,gkt,c,table1]\n");
        result.append("[5,pnc,a,table2]\n");
        result.append("[9,dba,b,table2]\n");
        result.append("[17,rhs,b,table2]\n");
        result.append("[25,bda,c,table2]\n");
        result.append("[41,wze,a,table2]\n");
        result.append("[0,tkg,a,overflow]\n");

        assertEquals(result.toString(),ht.toString());
        assertEquals(13,ht.size());
    }

    @Test
    @DisplayName("Test 2")
    void test2()
    {
        HashtableCuckoo<String,Character> ht = new HashtableCuckoo<String, Character>();
        String[] arr1 = {"a" , "p", "w", "t", "s"};
        String[] arr2 = {"d", "n", "z", "k", "r"};
        String[] arr3 = {"b", "c", "e", "g", "h"};

        for(int i=0; i<5; i++)
        {
            String str = arr1[i] + arr2[i] + arr3[i];
            ht.put(str,'a');

            String str1 = arr2[i] + arr3[i] + arr1[i];
            ht.put(str1,'b');

            String str2 = arr3[i] + arr2[i] + arr1[i];
            ht.put(str2,'c');

        }

        StringBuilder result = new StringBuilder();
        result.append("[12,zew,b,table1]\n");
        result.append("[21,cnp,c,table1]\n");
        result.append("[33,hrs,c,table1]\n");
        result.append("[42,ezw,c,table1]\n");
        result.append("[51,ncp,b,table1]\n");
        result.append("[55,adb,a,table1]\n");
        result.append("[56,gkt,c,table1]\n");
        result.append("[5,pnc,a,table2]\n");
        result.append("[9,dba,b,table2]\n");
        result.append("[17,rhs,b,table2]\n");
        result.append("[25,kgt,b,table2]\n");
        result.append("[41,wze,a,table2]\n");
        result.append("[0,bda,c,overflow]\n");
        result.append("[1,tkg,a,overflow]\n");
        result.append("[2,srh,a,overflow]\n");
        assertEquals(result.toString(),ht.toString());
        assertEquals(15,ht.size());


    }

}