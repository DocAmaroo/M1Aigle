package tp3;

import javafx.util.Pair;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RequeteTest {

    @org.junit.jupiter.api.Test
    void test1() {
        IOuvrage ouvrage = mock(IOuvrage.class);
        Requete req = new Requete();
        ArrayList<Pair<String,Integer>> l1 = new ArrayList<Pair<String, Integer>>();
        when (ouvrage.nbExemplaire()).thenReturn(2) ;
        assertEquals(2, ouvrage.nbExemplaire());
        when (ouvrage.listMediaAvecNbOuvrageDispo()).thenReturn(l1) ;
        ArrayList<String> res = Requete.listeMediatheques(ouvrage);
        assertTrue(res.isEmpty());
    }
}