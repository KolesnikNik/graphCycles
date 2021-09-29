import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphV2 {

    /**
     * Список ребер
     */
    static int[][] graph;

    /**
     * Список уникальных циклов
     */
    static Set<String> uni = new HashSet<>();

    /**
     * Список всех найденных циклов
     */
    static List<int[]> cycles = new ArrayList<int[]>();


    public static void main(String[] args) {

        //Считываем ребра
        fileReader("Test.txt");

        //Пробегаем граф в поисках циклов
        for (int i = 0; i < graph.length; i++)
            for (int j = 0; j < graph[i].length; j++) {
                findCycle(new int[]{graph[i][j]});
            }

        //Выводим циклы
        print(cycles);

    }

    /**
     * Метод обхода графа в поиска цикла
     * обходим вершины, пока не поподем в начальную
     * после записываем цикл в список
     *
     * @param path - пройденый путь
     */
    static void findCycle(int[] path) {

        int n = path[0];
        int x;
        int[] sub = new int[path.length + 1];

        for (int i = 0; i < graph.length; i++)
            for (int y = 0; y <= 1; y++)
                if (graph[i][y] == n) {
                    x = graph[i][(y + 1) % 2];
                    if (!visited(x, path)) {
                        sub[0] = x;
                        System.arraycopy(path, 0, sub, 1, path.length);

                        findCycle(sub);
                    } else if ((path.length > 2) && (x == path[path.length - 1]))
                        cycles.add(inverse(path));
                }
    }

    /**
     * Метод чтения графа из файла
     * Считанное из файла заносится в массив graph
     *
     * @param fileName - имя читаемого файла
     */
    public static void fileReader(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<>();

        //Считываем файл
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                list.add(reader.next());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        graph = new int[list.size()][2];

        //Записываем ребра в массив
        for (int i = 0; i < list.size(); i++) {
            String[] parts = list.get(i).split(",");
            graph[i][0] = Integer.parseInt(parts[0]);
            graph[i][1] = Integer.parseInt(parts[1]);
        }

    }

    /**
     * Метод отображения циклов
     * метод находит повторяющиеся циклы и исключает их
     *
     * @param cycles - все найденные циклы,
     *               найденные в результате обхода каждой вершины
     */
    static void print(List<int[]> cycles) {

        for (int[] cycle : cycles) {
            int[] a = new int[cycle.length + 1];
            for (int i = 0; i < a.length; i++) {
                if (i < a.length - 1) a[i] = cycle[i];
                else a[i] = cycle[0];
            }
            uni.add(Arrays.toString(a));
        }
        System.out.println("Count of cycles: " + uni.size());
        int i = 1;
        for (String item : uni) {

            System.out.println("Cycle №" + i + " " + item);
            i++;
        }
    }

    /**
     * Цикл сортирует
     *
     * @param cycle - сортируемый цикл.
     * @return - возвращает отсортированную копию цикла
     */
    static int[] inverse(int[] cycle) {
        int[] p = cycle.clone();
        Arrays.sort(p);
        return p;
    }

    /**
     * Метод определяет посещаемость вершины
     *
     * @param n    - вершина
     * @param path - пройденный путь
     * @return - возвращает true, если вершина была посещена
     * и false если нет
     */
    static Boolean visited(int n, int[] path) {
        boolean ret = false;

        for (int p : path) {
            if (p == n) {
                ret = true;
                break;
            }
        }

        return ret;
    }

}

