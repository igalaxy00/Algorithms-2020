package lesson7;

import kotlin.NotImplementedError;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Трудоемкость: o(second.length()*first.length()
     * Ресурсоемкость: o(second.length()*first.length()
     */
    public static String longestCommonSubSequence(String first, String second) {
        int secLen = second.length();
        int firsLen = first.length();
        StringBuilder result = new StringBuilder();
        int[][] nums = new int[firsLen + 1][secLen + 1];
        String answer;
        for (int i = 1; i <= secLen; i++) {
            for (int j = 1; j <= firsLen; j++) {
                if (first.charAt(j - 1) == (second.charAt(i - 1))) {
                    nums[j][i] = 1 + nums[j - 1][i - 1];
                } else
                    nums[j][i] = max(nums[j - 1][i], nums[j][i - 1]);
            }
        }

        while (secLen != 0 && firsLen != 0) {
            if (first.charAt(firsLen - 1) == (second.charAt(secLen - 1))) {
                result.append(first.charAt(firsLen - 1));
                secLen--;
                firsLen--;
            } else if (nums[firsLen][secLen] > nums[firsLen][secLen - 1]) {
                firsLen--;
            } else
                secLen--;
        }
        answer = result.reverse().toString();
        return answer;
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     * Трудоемкость: O(list.size^2)
     * Ресурсоемкость: R = O(list.size)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if (list.isEmpty()) return new ArrayList<>();
        int[] maxL = new int[list.size()];
        int[] previous = new int[list.size()];
        int position = 0;
        int len = 0;
        List<Integer> res = new ArrayList<>();
        for (int j = 0; j < list.size(); j++) {
            previous[j] = -1;
            maxL[j] = 1;
            for (int i = 0; i < j; i++) {
                if (list.get(i) < list.get(j) && maxL[i] + 1 > maxL[j]) {
                    maxL[j] = maxL[i] + 1;
                    previous[j] = i;
                }
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (maxL[j] > len) {
                position = j;
                len = maxL[j];
            }
        }

        while (position != -1) {
            res.add(0, list.get(position));
            position = previous[position];
        }
        return res;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
