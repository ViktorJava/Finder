package ru.job4j;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс условий.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.2
 * @since 10/16/2021
 */
public class Condition {
    public Predicate<Path> getPredicate(String name, String type) {
        switch (type) {
            case "name":
                return getName(name);
            case "mask":
                return getMask(name);
            case "regex":
                return getRegex(name);
            default:
                throw new IllegalArgumentException("ошибка аргументов");
        }
    }

    /**
     * Метод возвращает предикат, при типе поиска, по регулярному выражению.
     * Например: -n=\d.java
     * В поиск войдут все файлы в имени которых присутствуют цифры
     * и с расширением java.
     *
     * @param name Имя файла поиска.
     * @return Предикат поиска файла по регулярному выражению.
     */
    private Predicate<Path> getRegex(String name) {
        return p -> {
            Pattern pat = Pattern.compile(name);
            Matcher mat = pat.matcher(p.toFile().getName());
            return mat.find();
        };
    }

    /**
     * Метод возвращает предикат, при типе поиска по маске.
     *
     * @param name Имя файла поиска.
     * @return Предикат поиска файла по маске.
     */
    private Predicate<Path> getMask(String name) {
        int i = name.indexOf("*");
        if ((i == -1) || i != name.lastIndexOf("*")) {
            throw new IllegalArgumentException("ошибка маски");
        }
        String left = name.substring(0, i);
        String right = name.substring(i + 1);
        if (i == 0) {
            return path -> path.toFile().getName().endsWith(right);
        }
        if (i == name.length() - 1) {
            return path -> path.toFile().getName().startsWith(left);
        }
        return path -> path.toFile().getName().startsWith(left)
                && path.toFile().getName().endsWith(right);
    }

    /**
     * Метод возвращает предикат, при типе поиска, по имени файла.
     *
     * @param name Имя файла поиска.
     * @return Предикат поиска файла по имени.
     */
    private Predicate<Path> getName(String name) {
        return n -> n.toFile().getName().equals(name);
    }
}
