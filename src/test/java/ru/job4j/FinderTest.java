package ru.job4j;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 10/16/2021
 */
public class FinderTest {
    @Test
    public void whenLeftMask() throws IOException {
        Predicate<Path> condition = new Condition().getPredicate("RE*.md", "mask");
        List<Path> lp = Finder.search(Paths.get("c:/projects/job4j_design/"), condition);
        List<Path> expected = List.of(
                Paths.get("c:/projects/job4j_design/chapter_001/src/main/java/ru/job4j/collection/hash/README.md"),
                Paths.get("c:/projects/job4j_design/README.md")
        );
        assertEquals(expected, lp);
    }

    @Ignore
    @Test
    public void whenWrongMask() {
        //TODO Add Tests
    }
}
    