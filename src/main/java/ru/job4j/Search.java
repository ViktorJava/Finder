package ru.job4j;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * TODO Add Javadoc
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 25.04.2021
 */
public class Search extends SimpleFileVisitor<Path> {
    List<Path> paths;
    Predicate<Path> condition;

    //TODO Add Javadoc
    public Search(Predicate<Path> condition) {
        this.condition = condition;
        this.paths = new ArrayList<>();
    }

    /**
     * Здесь мы и описываем что нужно делать с каждым файлом в каждой директории.
     *
     * @param file  Ссылка на файл.
     * @param attrs Основные атрибуты просматриваемого файла.
     * @return Продолжаем обход дерева CONTINUE.
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        if (condition.test(file)) {
            paths.add(file);
        }
        return CONTINUE;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
