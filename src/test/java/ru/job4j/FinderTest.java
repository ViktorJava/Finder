package ru.job4j;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

/**
 * Finder test.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 10/16/2021
 */
public class FinderTest {
    Finder finder = new Finder();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * LeftMask: *First.xml
     *
     * @throws IOException Possible Exception.
     */
    @Test
    public void whenLeftMask() throws IOException {
        File tempFile = folder.newFile("dummyFileFirst.xml");
        folder.newFile("dummyFileSecond.xml");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("*First.xml", "mask");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * RightMask: dummyFileF*.xml
     *
     * @throws IOException Possible Exception.
     */
    @Test
    public void whenRightMask() throws IOException {
        File tempFile = folder.newFile("dummyFileFirst.xml");
        folder.newFile("dummyFileSecond.xml");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("dummyFileF*.xml", "mask");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * EndMask: dummyFileFirst.*
     *
     * @throws IOException Possible Exception.
     */
    @Test
    public void whenEndMask() throws IOException {
        File tempFile = folder.newFile("dummyFileFirst.xml");
        folder.newFile("dummyFileSecond.xml");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("dummyFileFirst.*", "mask");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * EndMask: dummy*First.xml
     *
     * @throws IOException Possible Exception.
     */
    @Test
    public void whenCenterMask() throws IOException {
        File tempFile = folder.newFile("dummyFileFirst.xml");
        folder.newFile("dummyFileSecond.xml");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("dummy*First.xml", "mask");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * Search type: name
     *
     * @throws IOException Possible Exception.
     */
    @Test
    public void whenFileName() throws IOException {
        File tempFile = folder.newFile("dummyFileFirst.xml");
        folder.newFile("dummyFileSecond.xml");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("dummyFileFirst.xml", "name");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * Use regex
     */
    @Test
    public void whenUseRegex() throws IOException {
        File tempFile = folder.newFile("GCTypeDemo2.java");
        folder.newFile("Task.java");
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition()
                .getPredicate("\\d.java", "regex");
        List<Path> result = finder.search(path, condition);
        List<Path> expected = List.of(
                Paths.get(tempFile.getAbsolutePath())
        );
        assertEquals(expected, result);
    }

    /**
     * WrongArgument: marsk
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenWrongArgument() {
        Path path = Paths.get(String.valueOf(folder.getRoot()));
        new Condition().getPredicate("*.xml", "marsk");
    }

    /**
     * WrongMask: REA**DME.md
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenWrongMask() {
        new Condition().getPredicate("REA**DME.md", "mask");
    }

    /**
     * WrongMask: README.md
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenNotfoundMask() {
        new Condition().getPredicate("README.md", "mask");
    }

    /**
     * Search type: content
     */
    @Test
    public void searchTypeContent() throws IOException {
        List<Path> expected = new ArrayList<>();
        File tempFile = folder.newFile("dummyFileFirst.xml");
        Files.writeString(tempFile.toPath(), "class");
        expected.add(tempFile.toPath());

        tempFile = folder.newFile("dummyFileSecond.xml");
        Files.writeString(tempFile.toPath(), "clazz");

        tempFile = folder.newFile("dummyFileThird.xml");
        Files.writeString(tempFile.toPath(), "aaa_class__aaa");
        expected.add(tempFile.toPath());

        folder.newFolder("dummyFolder");

        Path path = Paths.get(String.valueOf(folder.getRoot()));
        Predicate<Path> condition = new Condition().getPredicate("class", Condition.CONTENT_TYPE);
        List<Path> result = finder.search(path, condition);

        assertEquals(expected, result);
    }
}
