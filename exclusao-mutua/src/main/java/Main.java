import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class Main {
	
	private static ScheduledExecutorService executor;
	private static Cliente clientes = new Cliente();
	
    public static void main(String[] args) {
    	Routines routine = new Routines();    	
    	routine.startNewRoutine(40, clientes, true);
    }    

}