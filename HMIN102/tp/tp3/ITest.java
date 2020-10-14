package tp3;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.AdditionalMatchers.leq;
import static org.mockito.Mockito.*;

public class ITest {
    @Test
    void test() throws Exception {
        I i = mock(I.class);

        when(i.methodeInt()).thenReturn(0);
        assertEquals(0, i.methodeInt());
        when(i.methodeInt()).thenReturn(1);
        assertEquals(1, i.methodeInt());
        when(i.methodeInt()).thenReturn(2);
        assertEquals(2, i.methodeInt());
        when(i.methodeInt()).thenReturn(3);
        assertEquals(3, i.methodeInt());

        // check if methodInt have been called 4 times
        verify(i, times(4)).methodeInt();

        // throws exception on methodeInt
        when(i.methodeInt()).thenThrow(Exception.class);
        assertThrows(Exception.class, i::methodeInt);

        // force exception on methodVoid() call
        doThrow(new Exception()).when(i).methodeVoid();
        assertThrows(Exception.class, i::methodeVoid);

        // mock on param
        when(i.methodeParam(anyInt())).thenReturn(0);
        when(i.methodeParam(3)).thenReturn(3);
        when(i.methodeParam(5)).thenReturn(10);

        for (int k=0; k < 10; k++) {
            System.out.print(i.methodeParam(k) + ";");
        }
        System.out.println("");

        // matcher on param
        when(i.methodeParam(gt(10))).thenReturn(42);
        when(i.methodeParam(leq(10))).thenReturn(0);

        ArrayList<String> l1 = new ArrayList<String>();
        l1.add("1"); l1.add("0"); l1.add("42");

        ArrayList<String> l2 = new ArrayList<String>();
        l2.add("1"); l2.add("0"); l2.add("5");

        // matcher on ArrayList
//        when(i.methodeParamArrayList(argThat(array -> {
//            if (array.size() == 1) {
//                return true;
//            }
//            for(String s : array) {
//                if (s.equals("42")){
//                    return true;
//                }
//            }
//
//            return false;
//        }))).thenReturn(42);

        // other method
        when(i.methodeParamArrayList(argThat(array -> array.contains("42") || array.size() == 1 ))).thenReturn(42);

        assertEquals(42, i.methodeParamArrayList(l1));
        assertEquals(0, i.methodeParamArrayList(l2));
    }
}
