
public class Coordenador {

	private int id;
	private String recusos;	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getRecusos() {
		return recusos;
	}
	
	public void removeCoordenador() {
		this.id = -1;
	}
}
