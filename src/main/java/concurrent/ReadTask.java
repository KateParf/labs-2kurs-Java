package concurrent;

import functions.TabulatedFunction;

public class ReadTask implements Runnable{

    final private TabulatedFunction func;

    public ReadTask(TabulatedFunction func) {
        this.func = func;
    }

    @Override
    public void run() {
        for (int i = 0; i < func.getCount(); i++){
            synchronized (func) {
                System.out.printf("After read: i = %d, x = %f, y = %f ", i, func.getX(i), func.getY(i));
            }
        }
    }
}
