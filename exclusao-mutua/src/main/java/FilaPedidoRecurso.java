import java.util.LinkedList;
import java.util.List;

public class FilaPedidoRecurso {
	
	private List<Processo> filaProcesso = new LinkedList<Processo>();
	
	public void insere(Processo processo) {
		this.filaProcesso.add(processo);
	}

	public Processo remove() {
		return this.filaProcesso.remove(0);
	}

	public boolean vazia() {
		return this.filaProcesso.size() == 0;
	}
}
