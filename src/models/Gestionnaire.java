package models;

public class Gestionnaire {

	private int idGest = 0;
	private String nomGest;
	private String telephone;
	private String adresse;
	private String cni;
	private int type = 0;
	private String username;
	private String password;
	private int actif = 0;

	public int getIdGest() {
		return this.idGest;
	}

        public Gestionnaire() {
        }
        public Gestionnaire(int idGest,String nomGest,String telephone,String adresse,String username,String password,int type,String cni, String lienCNI)
		{
			this.idGest=idGest;
			this.nomGest=nomGest;
			this.telephone=telephone;
			this.adresse=adresse;
			this.cni=cni;
			this.type=type;
			this.username=username;
			this.password=password;
		}
        
        public Gestionnaire(int idGest) {
            this.idGest = idGest;
        }

	/**
	 * 
	 * @param idGest
	 */
	public void setIdGest(int idGest) {
		this.idGest = idGest;
	}

	public String getNomGest() {
		return this.nomGest;
	}

	/**
	 * 
	 * @param nomGest
	 */
	public void setNomGest(String nomGest) {
		this.nomGest = nomGest;
	}

	public String getTel() {
		// TODO - implement Gestionnaire.getTel
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Tel
	 */
	public void setTel(String Tel) {
		// TODO - implement Gestionnaire.setTel
		throw new UnsupportedOperationException();
	}

	public String getAdresse() {
		return this.adresse;
	}

	/**
	 * 
	 * @param adresse
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getCni() {
		return this.cni;
	}

	/**
	 * 
	 * @param cni
	 */
	public void setCni(String cni) {
		this.cni = cni;
	}

	public int getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(int type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	/**
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public int getActif() {
		return this.actif;
	}

	/**
	 * 
	 * @param actif
	 */
	public void setActif(int actif) {
		this.actif = actif;
	}

}