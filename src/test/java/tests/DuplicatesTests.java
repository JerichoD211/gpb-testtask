package tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import tests.dublicatestask.FindingDoubles;

import static org.junit.jupiter.api.Assertions.*;

public class DuplicatesTests {
    @Test @Step("Тест для дубликатов")
    public void duplicatesTest() {
        assertEquals(0, FindingDoubles.counter("abcde"));
        assertEquals(2, FindingDoubles.counter("aabbcde"));
        assertEquals(2, FindingDoubles.counter("aabBcde"));
        assertEquals(1, FindingDoubles.counter("indivisibility"));
        assertEquals(2, FindingDoubles.counter("Indivisibilities"));
        assertEquals(2, FindingDoubles.counter("aA11"));
        assertEquals(2, FindingDoubles.counter("ABBA"));
        assertEquals(3, FindingDoubles.counter("aafdfad"));
        assertEquals(1, FindingDoubles.counter("66666"));
        assertEquals(1, FindingDoubles.counter("AAAaaa"));
        assertEquals(0, FindingDoubles.counter("12345"));
        assertEquals(11, FindingDoubles.counter("aabbccddeeffgghhiijjkk"));
        assertEquals(0, FindingDoubles.counter(""));
    }
}
