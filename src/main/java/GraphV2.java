import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphV2 {
    //Задаем ребра, составляем граф
    static int[][] graph;
    static Set<String> uni = new HashSet<>();

    static List<int[]> cycles = new ArrayList<int[]>();

    public static void main(String[] args) {
        fileReader("Test.txt");
        //Пробегаем граф в поисках циклов
        for (int i = 0; i < graph.length; i++)
            for (int j = 0; j < graph[i].length; j++) {

                findNewCycles(new int[]{graph[i][j]});
            }

        //Выведем циклы
        print(cycles);

    }

    //Поиск цикла
    static void findNewCycles(int[] edge) {
        int n = edge[0];
        int x;
        int[] sub = new int[edge.length + 1];

        for (int i = 0; i < graph.length; i++)
            for (int y = 0; y <= 1; y++)
                if (graph[i][y] == n)
                //  ребро текущего узла
                {
                    x = graph[i][(y + 1) % 2];
                    if (!visited(x, edge))
                    //  еще не посещенный узел
                    {
                        sub[0] = x;
                        System.arraycopy(edge, 0, sub, 1, edge.length);

                        findNewCycles(sub);
                    } else if ((edge.length > 2) && (x == edge[edge.length - 1]))
                        //  цикл найден
                        cycles.add(inverse(edge));
                }
    }

    //Чтение из файла
    public static void fileReader(String fileName) {
        File file = new File(fileName);
        List<String> list = new ArrayList<>();
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNext()) {
                list.add(reader.next());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        graph = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            graph[i][0] = Character.getNumericValue(list.get(i).charAt(0));
            graph[i][1] = Character.getNumericValue(list.get(i).charAt(2));

        }

    }

    //Добавляем циклы в Set, чтобы выделить уникальные
    //далее выводим коллекцию
    static void print(List<int[]> cycles) {
        for (int[] cycle : cycles) {

            uni.add(Arrays.toString(cycle));
        }

        for (String item : uni) {
            System.out.println(item);
        }
    }

    //  Сортируем цикл
    static int[] inverse(int[] edge) {
        int[] p = edge.clone();
        Arrays.sort(p);
        return p;
    }
    //  Посещалась ли вершина
    static Boolean visited(int n, int[] edge) {
        Boolean ret = false;

        for (int p : edge) {
            if (p == n) {
                ret = true;
                break;
            }
        }

        return ret;
    }

}

