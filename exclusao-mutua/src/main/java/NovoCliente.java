
public class NovoCliente implements Runnable{

	@Override
	public void run() {
		System.out.println("Iniciou");
		Cliente cliente = new Cliente();
		cliente.iniciaNovoCliente();
	}

}
