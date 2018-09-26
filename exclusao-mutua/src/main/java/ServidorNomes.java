import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ServidorNomes {		
	
	public static int buscaNovoIdProcesso() {
		Random gerador = new Random();
		return gerador.nextInt(10000);
	}

	public static boolean isIdRepetido(HashMap<Integer, PrintStream> processos, int novoId) {
		return processos.containsKey(novoId);
	}

	public static int eleicaoNovoCoordenador(HashMap<Integer, PrintStream> processos) {
		List<Integer> listaIds = processos.entrySet().stream().map(proc -> proc.getKey()).collect(Collectors.toList());
		Collections.sort(listaIds);
		return listaIds.get(0).intValue();
	}
	
	public static void startServer() {
		HashMap<Integer, PrintStream> processos = new HashMap<>();
		Coordenador coordenador = new Coordenador();
		coordenador.removeCoordenador();		
		try {

			ServerSocket servidor = new ServerSocket(9999);
			System.out.println("Servidor nomes lendo a porta 9999");

			while (true) {
				Socket socket = servidor.accept();

				new Thread() {
					public void run() {
						System.out.println("Cliente conectou: " + socket.getInetAddress().getHostName());

						try {
							PrintStream novoProcesso = new PrintStream(socket.getOutputStream());
							int novoId = buscaNovoIdProcesso();
							while (isIdRepetido(processos, novoId)) {
								novoId = buscaNovoIdProcesso();
							}
							processos.put(novoId, novoProcesso);
							novoProcesso.println(novoId);
							Scanner leitura = new Scanner(socket.getInputStream());
							while (leitura.hasNext()) {
								if (coordenador.getId() == -1) {
									coordenador.setId(eleicaoNovoCoordenador(processos));
									processos.get(coordenador.getId()).println("Você novo coordenador");
								}
								String texto = leitura.nextLine();
								System.err.println(texto);
								// for (PrintStream cliente : clientes) {
								// cliente.println(texto);
								// // cliente.flush();
								// }
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		startServer();		
	}
}
