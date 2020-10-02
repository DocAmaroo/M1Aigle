package TDTP1_2;

import static org.junit.jupiter.api.Assertions.*;

class DicoTest {

    protected OrderedDico od;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        od = new OrderedDico(3);

    }

    @org.junit.jupiter.api.Test
    void test() {
        od.put("brandade", 9000);
        assertEquals(1, od.size());
        assertTrue(od.containsKey("brandade"));
        assertEquals(9000, od.get("brandade"));
    }
}