package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     * Трудоемкость - O(firs.length()*second.length())
     * Ресурсоемкость - O(second.length())
     */
    static public String longestCommonSubstring(String firs, String second) {
        int comStr = 0;
        int[] wordsMas = new int[second.length()];
        StringBuilder finalString = new StringBuilder();
        int startP = 0;
        for(int letterF = 0 ; letterF<firs.length()-1 ; letterF++)// цикл по 1 слову
            for (int letterS = second.length()-1 ; letterS>=0; letterS-- )//цикл по 2 слову
                if (firs.charAt(letterF)==second.charAt(letterS)) {
                    if (letterS == 0){
                        //System.out.println(letterS);
                        wordsMas[letterS] = 1;
                    }else wordsMas[letterS] = wordsMas[letterS - 1] + 1;
                    if (wordsMas[letterS] > comStr){
                        comStr = wordsMas[letterS];
                        startP = letterS;
                    }
                }else
                    wordsMas[letterS] = 0;
        for (int k = startP + 1 - comStr ;  k <=startP ; k++)
            finalString.append(second.charAt(k));
        return finalString.toString();
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     * Трудоемкость O(n*sqrt(n)
     */
    static public int calcPrimesNumber(int limit) {
        if (limit <= 1) return 0;
        boolean k;
        int result = 0;
        for (int i = 2; i <= limit; i++){
            k = true;
            if (i == 2){
                result++;
                continue;
                }
            if (i < 2 || i%2==0)
                continue;
            for (int j = 3; j <= (int) (Math.sqrt(i)); j += 2){
                if (i % j == 0){
                    k = false;
                    break;
                }
            }
            if (!k)
                continue;
            result++;
        }
        return result;
        }
}
