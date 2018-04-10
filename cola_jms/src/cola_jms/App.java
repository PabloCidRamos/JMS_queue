package cola_jms;

public class App {

	public static void main(String[] args) {
		thread(new Producer(), false);
        thread(new Consumer(), false);
	}
	
	public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

}
