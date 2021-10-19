package ru.job4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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

    public static final String NAME_TYPE = "name";

    public static final String MASK_TYPE = "mask";

    public static final String REGEX_TYPE = "regex";

    public static final String CONTENT_TYPE = "content";

    public Predicate<Path> getPredicate(String name, String type) {
        return switch (type) {
            case NAME_TYPE -> getName(name);
            case MASK_TYPE -> getMask(name);
            case REGEX_TYPE -> getRegex(name);
            case CONTENT_TYPE -> getContent(name);
            default -> throw new IllegalArgumentException("Неизвестный тип поиска");
        };
    }

    /**
     * Метод возвращает предикат для поиска файла по содержащейся в нём подстроке.
     * Примечание: кодировка UTF-8.
     * Например: -n=class
     * В поиск войдут файлы, в которых содержится строка "class".
     *
     * @param substring Подстрока для поиска.
     *
     * @return Предикат поиска файла по содержащейся в нём подстроке.
     */
    private Predicate<Path> getContent(final String substring) {
        byte[] bytes = substring.getBytes(StandardCharsets.UTF_8);
        return path -> {
            if (!path.toFile().isFile()) {
                return false;
            }

            boolean foundMatch = false;
            try (InputStream inputStream = new BufferedInputStream(new FileInputStream(path.toFile()))) {
                int read;
                int idx = 0;
                while ((read = inputStream.read()) != -1) {
                    if (bytes[idx] == read) {
                        idx++;
                        if (idx == bytes.length) {
                            foundMatch = true;
                            break;
                        }
                    }
                    else {
                        idx = 0;
                    }
                }
            }
            catch (Exception e) {
                //e.printStackTrace(); для дебага
                return false;
            }
            return foundMatch;
        };
    }

    /**
     * Метод возвращает предикат, при типе поиска, по регулярному выражению.
     * Например: -n=\d.java
     * В поиск войдут все файлы в имени которых присутствуют цифры
     * и с расширением java.
     *
     * @param name Имя файла поиска.
     *
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
     *
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
        return path -> path.toFile().getName().startsWith(left) && path.toFile().getName().endsWith(right);
    }

    /**
     * Метод возвращает предикат, при типе поиска, по имени файла.
     *
     * @param name Имя файла поиска.
     *
     * @return Предикат поиска файла по имени.
     */
    private Predicate<Path> getName(String name) {
        return n -> n.toFile().getName().equals(name);
    }
}
