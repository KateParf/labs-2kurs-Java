package concurrent;

import functions.ConstantFunction;
import functions.LinkedListTabulatedFunction;

public class ReadWriteTaskExecutor {
    public static void main(String[] args){
        ConstantFunction constF = new ConstantFunction(-1);
        LinkedListTabulatedFunction linkedF = new LinkedListTabulatedFunction(constF, 1, 1000, 1000);

        Thread thread1 = new Thread(new ReadTask(linkedF));
        Thread thread2 = new Thread(new WriteTask(linkedF, 0.5));

        thread1.start();
        thread2.start();
    }
}
