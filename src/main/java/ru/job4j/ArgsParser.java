package ru.job4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>Именованные аргументы</h2>
 * Класс реализует приём массива параметров и разбивание их на пары.
 *
 * @author ViktorJava (gipsyscrew@gmail.com)
 * @version 0.1
 * @since 25.04.2021
 */
public class ArgsParser {

    private final Map<String, String> values = new HashMap<>();

    /**
     * Метод возвращает значение ключа.
     *
     * @param key Ключ.
     * @return Значение
     */
    public String get(String key) {
        String lKey = key.toLowerCase();
        if (!values.containsKey(lKey)) {
            throw new IllegalArgumentException("параметра не существует: " + lKey);
        }
        return values.get(lKey);
    }

    /**
     * Приватный метод, парсит массив параметров полученный в виде аргумента.
     *
     * @param args Массив параметров в формате -ключ=значение.
     */
    private void parse(String[] args) {
        for (String arg: args) {
            String[] split = arg.replaceFirst("-", "").split("=");
            if (split.length != 2) {
                throw new IllegalArgumentException("неверный формат аргумента: -param=value");
            }
            values.put(split[0].toLowerCase(), split[1]);
        }
    }

    /**
     * Парсер аргументов.
     *
     * @param args Массив параметров в формате -ключ=значение.
     * @return Объект типа ArgsParser с распарсенными параметрами.
     */
    public static ArgsParser of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("список аргументов пуст");
        }
        ArgsParser names = new ArgsParser();
        names.parse(args);
        return names;
    }
}
