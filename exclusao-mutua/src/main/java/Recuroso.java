
public class Recuroso {
	
	private int mouse;
	private int teclado;
	
	public Recuroso() {
		this.mouse = -1;
		this.teclado = -1;
	}

	public int getMouse() {
		return mouse;
	}

	public void setMouse(int mouse) {
		this.mouse = mouse;
	}

	public int getTeclado() {
		return teclado;
	}

	public void setTeclado(int teclado) {
		this.teclado = teclado;
	}
	
	public void reiniciarRecursoMouse() {
		this.mouse = -1;
	}
	
	public void reiniciarRecursoTeclado() {
		this.teclado = -1;
	}

}
