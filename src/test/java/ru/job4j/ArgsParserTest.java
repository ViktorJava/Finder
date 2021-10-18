package ru.job4j;

import org.junit.Before;
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
    public void whenNotException() {
        ArgsParser ap = ArgsParser.of(
                new String[]{"-d=/homeDir/", "-n=*.xml", "-t=mask", "-o=log.txt"});
        String expected = ap.get("n");
        assertEquals(expected, "*.xml");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotfoundArgs() {
        ArgsParser.of(new String[]{});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenInvalidArgFormat() {
        ArgsParser.of(new String[]{"-d=", "-n=*.xml", "-t=mask", "-o=log.txt"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenArgumentException() {
        ArgsParser.of(new String[]{"-d=/homeDir/", "-n=*.xml", "-t=mask"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNotfoundParam() {
        ArgsParser ap = ArgsParser.of(new String[]{"-d=/homeDir/", "-n=*.xml",
                "-t=mask", "-o=log.txt"});
        ap.get("c");
    }

    @Test
    public void whenLowerCase() {
        ArgsParser ap = ArgsParser.of(new String[]{"-d=/homeDir/", "-n=*.xml",
                "-T=mask", "-o=log.txt"});
        String excepted = ap.get("t");
        assertEquals(excepted, "mask");
    }
}