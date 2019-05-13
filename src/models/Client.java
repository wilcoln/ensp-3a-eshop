package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Client {

    private static Statement statement = BD.getStatement();
    private int codeClient;
    private String nom;
    private String telephone;
    private String whatsap;
    private int pointFidelite = 0;
    private String dateAbonnement;
    private int remise = 0;
    private int typeCli = 0;
    private String email;
    private Ville saVille;


    public Client() {
        codeClient = -1;
        nom = "";
        telephone = "";
        whatsap = "";
        dateAbonnement = "";
        pointFidelite = 0;
        remise = 0;
        saVille = null;
        typeCli = 0;
        email = "";
    }

    public Client(String nom, String telephone, String whatsap, String dateAbonnement, int pointFidelite, int remise, String nomVille, int typeCli, String email) {
        codeClient = -1;
        this.nom = nom;
        this.telephone = telephone;
        this.whatsap = whatsap;
        this.pointFidelite = pointFidelite;
        this.dateAbonnement = dateAbonnement;
        this.remise = remise;
        this.saVille = Ville.load(nomVille);
        this.typeCli = typeCli;
        this.email = email;
    }

    public Client(String nom, String telephone, String whatsap, String dateAbonnement, int pointFidelite, int remise, Ville saVille, int typeCli, String email) {
        codeClient = -1;
        this.nom = nom;
        this.telephone = telephone;
        this.whatsap = whatsap;
        this.pointFidelite = pointFidelite;
        this.dateAbonnement = dateAbonnement;
        this.remise = remise;
        this.saVille = saVille;
        this.typeCli = typeCli;
        this.email = email;
    }

    public Client(int codeClient, String nom, String telephone, String whatsap, String dateAbonnement, int pointFidelite, int remise, int typeCli, String email, int idVille) {
        this.codeClient = codeClient;
        this.nom = nom;
        this.telephone = telephone;
        this.whatsap = whatsap;
        this.pointFidelite = pointFidelite;
        this.dateAbonnement = dateAbonnement;
        this.remise = remise;
        this.saVille = Ville.load(idVille);
        this.typeCli = typeCli;
        this.email = email;
    }

    public static void save(Client client) {
        if (client.codeClient == -1) {
            try {
                statement.executeUpdate("INSERT INTO client(nomCli,telCli,whatsap,dateAbonnement,pointFidelite,remise,typeCli,email,idVille) VALUES " +
                        "('" + client.getNom() + "', '" + client.getTelephone() + "', '" + client.getWhatsap() + "', '" + client.getDateAbonnement() + "', '" + client.getPointFidelite() + "', '" + client.getRemise() + "', '" + client.getTypeCli() + "', '" + client.getEmail() + "'," + client.getSaVille().getIdVille() + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveAll(ArrayList<Client> clients) {
        for (int i = 0; i < clients.size(); i++) {
            save(clients.get(i));
        }
    }

    public static Client load(int codeClient) {
        Client client = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE codeClient=" + codeClient + ";");
            resultSet.next();
            client = new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10));
            resultSet.close();
        } catch (SQLException e) {
        }
        return client;
    }

    public static Client load(String nomClient) {
        Client client = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE nomCli LIKE '%"+nomClient+ "%';");
            resultSet.next();
            client = new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10));
            resultSet.close();
        } catch (SQLException e) {
        }
        return client;
    }

    public static ArrayList<Client> loadAll() {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client;");
            while (resultSet.next())
                clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10)));
            resultSet.close();
        } catch (SQLException e) {
        }

        return clients;
    }

    public static boolean search(String nom) {
        int nombre = 0;

        try {
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM client WHERE nomCli LIKE '" + nom + "';");
            resultSet.next();
            nombre = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombre > 0;
    }

    public static Client loadByTel(String numTel)
    {
    	Client client = null;
    	try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM client WHERE telCli LIKE '"+numTel+ "';");
            resultSet.next();
            client = new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10));
            resultSet.close();
        } catch (SQLException e) {
        }
    	return client;
    }

    public static ArrayList<Client> loadAllByIdVille(int idVille) {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT *  FROM client WHERE idVille="+idVille+";");
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10)));
            }
            resultSet.close();
        } catch (SQLException e) {
        }

        return clients;
    }

    public static ArrayList<Client> loadAllByNameAndIdVille(int idVille,String nom) {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT *  FROM client WHERE idVille="+idVille+" AND nomCli LIKE '"+nom+"';");
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10)));
            }
            resultSet.close();
        } catch (SQLException e) {
        }

        return clients;
    }

    public static ArrayList<Client> loadAllByDateAbonnement(String dateAbonnement) {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT *  FROM client WHERE dateAbonnement >='"+dateAbonnement+"';");
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10)));
            }
            resultSet.close();
        } catch (SQLException e) {
        }

        return clients;
    }

    public static ArrayList<Client> loadAllByName(String  nom) {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT *  FROM client WHERE nomCli LIKE '%"+nom+"%';");
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getInt(6), resultSet.getInt(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getInt(10)));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }


    public void update() {
        if (codeClient != -1) {
            try {
                statement.executeUpdate("UPDATE client SET nomCli='" + getNom() + "', telCli='" + getTelephone() + "', whatsap='" + getWhatsap() + "', dateAbonnement='" + getDateAbonnement() + "', pointFidelite='" + getPointFidelite() + "', remise=" + getRemise() + ", typeCli=" + getTypeCli() + ", email='" + getEmail() + "', idVille=" + getSaVille().getIdVille() + " WHERE codeClient=" + codeClient + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public int getCodeClient() {
        return codeClient;
    }

    public void setCodeClient(int codeClient) {
        this.codeClient = codeClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWhatsap() {
        return whatsap;
    }

    public void setWhatsap(String whatsap) {
        this.whatsap = whatsap;
    }

    public int getPointFidelite() {
        return pointFidelite;
    }

    public void setPointFidelite(int pointFidelite) {
        this.pointFidelite = pointFidelite;
    }

    public String getDateAbonnement() {
        return dateAbonnement;
    }

    public void setDateAbonnement(String dateAbonnement) {
        this.dateAbonnement = dateAbonnement;
    }

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public int getTypeCli() {
        return typeCli;
    }

    public void setTypeCli(int typeCli) {
        this.typeCli = typeCli;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Ville getSaVille() {
        return saVille;
    }

    public void setSaVille(Ville saVille) {
        this.saVille = saVille;
    }
}
