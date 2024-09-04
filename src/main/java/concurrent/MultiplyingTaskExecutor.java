package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.UnitFunction;

//import java.util.LinkedList;
//import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiplyingTaskExecutor
{
    public static void main(String[] args)
    {
        // создание табулированной функции
        UnitFunction unitFunction = new UnitFunction();
        TabulatedFunction function = new LinkedListTabulatedFunction(unitFunction, 1, 1000, 1000);

        // создание и инициализация ConcurrentHashMap для хранения задач и их состояний
        Map<MultiplyingTask, Boolean> tasksMap = new ConcurrentHashMap<>();

        // цикл для создания задач и добавления их в tasksMap
        for (int i = 0; i < 10; i++)
        {
            MultiplyingTask task = new MultiplyingTask(function); // создается 10 экземпляров класса MultiplyingTask
            tasksMap.put(task, false); // каждый из которых добавляется в tasksMap с начальным значением false
            Thread thread = new Thread(task); // для каждой задачи создается новый поток
            thread.start();  // запускается выполнение этой задачи в отдельном потоке
        }

        // цикл для проверки выполнения задач
        while (tasksMap.containsValue(false)) // продолжает выполняться, пока хотя бы одна задача не завершится
        {
            for (Map.Entry<MultiplyingTask, Boolean> entry : tasksMap.entrySet()) // итерация по элементам tasksMap
            {
                if (!entry.getValue() && entry.getKey().isCompleted()) // если задача не была помечена как выполненная,
                    // и при этом метод isCompleted для данной задачи возвращает true
                {
                    entry.setValue(true); // для данной задачи устанавливается значение true
                }
            }
        }

//        // создание списка потоков
//        List<Thread> threads = new LinkedList<>();
//
//        // цикл для создания задач и потоков
//        for (int i = 0; i < 10; i++)
//        {
//            MultiplyingTask task = new MultiplyingTask(function);
//            Thread thread = new Thread(task);
//            threads.add(thread);
//        }
//
//        // запуск всех потоков
//        for (Thread thread : threads)
//        {
//            thread.start();
//        }
//
//        try
//        {
//            Thread.sleep(2000); // пауза на пару секунд
//        }
//        catch (InterruptedException e)
//        {
//            Thread.currentThread().interrupt();
//        }

        // вывод табулированной функции
        for (int i = 1; i < function.getCount(); i++)
        {
            System.out.println("x = " + function.getX(i) + ", y = " + function.getY(i));
        }
    }
}