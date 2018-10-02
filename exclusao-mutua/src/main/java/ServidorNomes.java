import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ServidorNomes {

	public static int buscaNovoIdProcesso() {
		Random gerador = new Random();
		return gerador.nextInt(10000);
	}

	public static boolean isIdRepetido(List<Processo> listaProcesso, int novoId) {
		return listaProcesso.stream().filter(processo -> processo.getId() == novoId).collect(Collectors.toList())
				.isEmpty();
	}

	public static Processo eleicaoNovoCoordenador(List<Processo> listaProcesso) {
		Collections.sort(listaProcesso, (a, b) -> {
			return a.getId() < b.getId() ? -1 : a.getId() > b.getId() ? 1 : 0;
		});
		return listaProcesso.get(0);
	}

	public static void startServer() {
		List<Processo> listaProcesso = new ArrayList();
		List<Processo> coordenador = new ArrayList<>();
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
							while (!isIdRepetido(listaProcesso, novoId)) {
								novoId = buscaNovoIdProcesso();
							}
							listaProcesso.add(new Processo(novoId, novoProcesso, socket));
							System.out.println(novoId);
							novoProcesso.println(novoId);
							if (!coordenador.isEmpty()) {
								novoProcesso
										.println("Coordenador: " + coordenador.get(0).getIp().getInetAddress().getHostName());
							}
							Scanner leitura = new Scanner(socket.getInputStream());
							while (leitura.hasNext()) {
								String texto = leitura.nextLine();
								if (texto.equals("Eleição")) {
									System.out.println("Inicio uma nova eleição");
									coordenador.clear();									
									coordenador.add(eleicaoNovoCoordenador(listaProcesso));
									coordenador.get(0).getCliente().println("Novo Coordenador");
									for (Processo processo : listaProcesso) {
										if (processo.getId() != coordenador.get(0).getId()) {
											processo.getCliente().println("novo Coordenador: "
													+ processo.getIp().getInetAddress().getHostName());
										}
									}
								}
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
		Coordenador coorde = new Coordenador();
		coorde.coordenador();
	}
	
}
