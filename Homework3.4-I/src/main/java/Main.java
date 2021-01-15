public class Main {
    private final Object monitor = new Object(); //монитор для тредов
    private volatile char currentLetter = 'A'; //char для переключения монитора между wait и notify

    public static void main(String[] args) {
        Main process = new Main();

        Thread a = new Thread(() -> {
            process.printA();
        });
        Thread b = new Thread(() -> {
            process.printB();
        });
        Thread c = new Thread(() -> {
            process.printC();
        });

        a.start();
        b.start();
        c.start();
    }

    public void printA() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'A') { //пока char не равен "А" - тред ждет
                        monitor.wait();
                    }
                    System.out.print("A"); //печать буквы
                    currentLetter = 'B'; //изменение char для паузы тред "а"
                    monitor.notifyAll(); // освобождение монитора
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    public void printB() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'B') {
                        monitor.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

    public void printC() {
        synchronized (monitor) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != 'C') {
                        monitor.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    monitor.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }

}
