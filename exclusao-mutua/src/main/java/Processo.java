import java.io.PrintStream;
import java.net.Socket;

public class Processo {

	private int id;
	private PrintStream cliente;
	private Socket ip;
	
	
	
	public Processo(int id, PrintStream cliente, Socket ip) {		
		this.id = id;
		this.cliente = cliente;
		this.ip = ip;
	}
	
	public int getId() {
		return id;
	}
	public PrintStream getCliente() {
		return cliente;
	}
	public Socket getIp() {
		return ip;
	}
	
	
}
