
public class Recurso {
	
	private boolean utilizado;
	
	public void utilizar(int cliente) {
		this.utilizado = true;
		System.out.println("O recurso esta sendo utilizado pelo Cliente: " + cliente);
	}
	
	public void acabou(int cliente) {
		this.utilizado = false;
		System.out.println("O recurso não esta mais sendo utilizado pelo Cliente: " + cliente);
	}
	
	public boolean isUtilizado() {
		return this.utilizado;
	}

}
