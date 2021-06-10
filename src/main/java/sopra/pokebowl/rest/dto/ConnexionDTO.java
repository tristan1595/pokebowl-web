package sopra.pokebowl.rest.dto;

public class ConnexionDTO {
	private String pseudo;
	private String motDePasse;

	public ConnexionDTO() {
		super();
	}

	public ConnexionDTO(String pseudo, String motDePasse) {
		super();
		this.pseudo = pseudo;
		this.motDePasse = motDePasse;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

}
