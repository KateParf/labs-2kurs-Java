package functions;

import concurrent.SynchronizedTabulatedFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelIntegrator
{
//    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors(); // количество доступных процессоров
    private int threadsCnt;

    // конструктор по умолчанию
    public ParallelIntegrator() {
        this.threadsCnt = 1;
    }

    // конструктор с заданием кл-ва потоков
    public ParallelIntegrator(int threadsCnt) {
        this.threadsCnt = threadsCnt;
    }

    // считает интеграл функции на всей области ее задания от Xmin до Xmax
    // передаем точность вычисления интеграла
    public double integrate(TabulatedFunction function, double precision) throws InterruptedException, ExecutionException
    {
        // получение границ интегрирования
        double lowerBound = function.leftBound();
        double upperBound = function.rightBound();

        // создание пула потоков для параллельных вычислений
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCnt); // создаем ExecutorService с фиксированным числом потоков
        List<Future<Double>> futures = new ArrayList<>(); // инициализируем список futures для хранения результатов выполнения каждого потока

        // оборачиваем ф-ю в синхронную обертку для многопоточного доступа
        SynchronizedTabulatedFunction syncFunc = new SynchronizedTabulatedFunction(function);

        // разделение задач на подзадачи и отправка их на выполнение
        double intervalSize = (upperBound - lowerBound) / threadsCnt; // разбиваем общий интервал интегрирования на подынтервалы
        for (int i = 0; i < threadsCnt; i++) // для каждого из них запускаем вычисление частичного интеграла в отдельном потоке
        {
            double start = lowerBound + i * intervalSize;
            double end = start + intervalSize;
            futures.add(
                    executorService.submit(
                            () -> computePartialIntegral(function, start, end, precision)
                    )
            ); // результаты добавляем в список futures
        }

        // остановка пула потоков и ожидание завершения всех задач
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // суммирование результатов
        double totalIntegral = 0.0;
        for (Future<Double> future : futures) {
            totalIntegral += future.get();
        }

        return totalIntegral;
    }

    private double computePartialIntegral(TabulatedFunction function, double start, double end, double precision)
    {
        double sum = 0.0;

        double x, y1, y2;
        double averageY;
        double area;
        for (x = start; x <= end - precision; x += precision)
        {
            y1 = function.apply(x);
            y2 = function.apply(x + precision);
            averageY = (y1 + y2) / 2.0;
            area = averageY * precision;
            sum += area;
        }

        //
        y1 = function.apply(x);
        y2 = function.apply(end);
        averageY = (y1 + y2) / 2.0;
        area = averageY * (end - x);
        sum += area;

        return sum;
    }
}