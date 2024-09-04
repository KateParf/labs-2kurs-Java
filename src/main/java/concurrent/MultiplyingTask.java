package concurrent;

import functions.TabulatedFunction;

public class MultiplyingTask implements Runnable
{
    private final TabulatedFunction tabulatedFunction;
    private volatile boolean isCompleted = false;

    public MultiplyingTask(TabulatedFunction function)
    {
        this.tabulatedFunction = function;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < tabulatedFunction.getCount(); i++)
        {
            synchronized (tabulatedFunction) // для обеспечения доступа к объекту в определенное время только для одного потока
            {
                tabulatedFunction.setY(i, tabulatedFunction.getY(i) * 2);
            }
        }
        System.out.println("Текущий поток " + Thread.currentThread().getName() + " закончил выполнение задачи");
        isCompleted = true;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }
}