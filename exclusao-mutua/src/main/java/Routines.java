import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Classe para facilitar a criacao das rotinas
 */
public class Routines {

    List<Thread> threadList;

    public Routines() {
        threadList = new ArrayList<>();
    }

    /**
     * Criacao de uma rotina de acordo com os parametros
     *
     * @param sec      Intervalo de tempo para a execucao da rotina, em segundos
     * @param runnable Rotina a ser executada
     */
    public void startNewRoutine(int sec, Runnable runnable) {
        startNewRoutine(sec, runnable, false);
    }

    /**
     * Criacao de uma rotina de acordo com os parametros
     *
     * @param sec              Intervalo de tempo para a execucao da rotina, em segundos
     * @param runnable         Rotina a ser executada
     * @param startImmediately se {@code true}, a rotina inicializará primeiro e depois aguardara o intervalo
     */
    public void startNewRoutine(int sec, Runnable runnable, boolean startImmediately) {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (startImmediately) {
                        runnable.run();
                        Thread.sleep(TimeUnit.SECONDS.toMillis(sec));
                    } else {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(sec));
                        runnable.run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        thread.start();
        threadList.add(thread);
    }

}