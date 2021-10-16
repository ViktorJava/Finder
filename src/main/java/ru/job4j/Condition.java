package ru.job4j;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * TODO Add Javadoc
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
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

    //TODO Add implementation
    private Predicate<Path> getRegex(String name) {
        return Objects::isNull;
    }

    //TODO Add Javadoc
    private Predicate<Path> getMask(String name) {
        int i = name.indexOf("*");
        if (i == -1) {
            throw new IllegalArgumentException("ошибка маски");
        }
        String left = name.substring(0, i);
        String right = name.substring(i + 1);
        if (i == 0) {
            return path -> path.toFile().getName().endsWith(right);
        } else if (i == name.length() - 1) {
            return path -> path.toFile().getName().startsWith(left);
        }

        return path -> path.toFile().getName().startsWith(left)
                && path.toFile().getName().endsWith(right);
    }

    private Predicate<Path> getName(String name) {
        return n -> n.toFile().getName().equals(name);
    }
}
