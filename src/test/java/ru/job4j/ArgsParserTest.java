package ru.job4j;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Именованные аргументы
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 10/12/2021
 */
public class ArgsParserTest {
    @Test
    public void whenOf() {
        ArgsParser ap = ArgsParser.of(new String[]{"-d=/homeDir/"});
        String expected = ap.get("d");
        assertEquals(expected, "/homeDir/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEmptyParam() {
        ArgsParser ap = ArgsParser.of(new String[]{});
        ap.get("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenArgumentException() {
        ArgsParser.of(new String[]{"-d /homeDir/"});
    }

    @Test
    public void whenLowerCase() {
        ArgsParser ap = ArgsParser.of(new String[]{"-T=mask"});
        String excepted = ap.get("t");
        assertEquals(excepted, "mask");
    }
}