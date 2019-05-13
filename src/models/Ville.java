package models;
import java.sql.*;
import java.util.ArrayList;

public class Ville {

	private int idVille;
	private String nomV;
	private String agenceExpedition;
	private float montantExpedition;


    private static Statement statement = BD.getStatement();


    public Ville (String nomV, String agenceExpedition, float montantExpedition)
    {
        this.idVille = -1;
        this.nomV = nomV;
        this.agenceExpedition = agenceExpedition;
        this.montantExpedition = montantExpedition;
    }

    public Ville (int idVille,String nomV, String agenceExpedition, float montantExpedition)
    {
        this.idVille = idVille;
        this.nomV = nomV;
        this.agenceExpedition = agenceExpedition;
        this.montantExpedition = montantExpedition;
    }

    public static void saveAll (ArrayList<Ville> villes)
    {
        for (int i= 0;i<villes.size();i++)
        {
            Ville.save(villes.get(i));
        }
    }

    public static Ville load (int idVille)
    {
        Ville ville = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville WHERE idVille ="+idVille+";");
            resultSet.next();
            ville = new Ville(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4));
            resultSet.close();
        } catch (SQLException e) {
        }
        return ville;
    }

    public static Ville load (String nomV)
    {
        Ville ville = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville WHERE nomVille LIKE '"+nomV+"';");
            resultSet.next();
            ville = new Ville(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4));
            resultSet.close();
        } catch (SQLException e) {
        }
        return ville;
    }

    public static ArrayList<Float> loadAllParametre ()
    {
        ArrayList<Float> parametre = new ArrayList<Float>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT count(*),avg(fraisExpedition),max(fraisExpedition),min(fraisExpedition) FROM ville;");
            resultSet.next();
            parametre.add(resultSet.getFloat(1));
            parametre.add(resultSet.getFloat(2));
            parametre.add(resultSet.getFloat(3));
            parametre.add(resultSet.getFloat(4));
            resultSet.close();
        } catch (SQLException e) {
        }
        return parametre;
    }

    public static boolean search(String nomV)
    {
        int nombre = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT count(*) FROM ville WHERE nomVille LIKE '"+nomV+"';");
            resultSet.next();
            nombre = resultSet.getInt(1);
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre>0;
    }

    public static void save (Ville ville)
    {
        if (ville.idVille == -1)
        {
            try {
                statement.executeUpdate("INSERT INTO ville(nomVille,agenceExpedition,fraisExpedition) VALUES " +
                        "('"+ville.getNomV()+"', '"+ville.getAgenceExpedition()+"', '"+ville.getMontantExpedition()+"');");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ville = load(ville.nomV);
        }
    }



    public static ArrayList<Ville> loadAll()
    {
        ArrayList<Ville> villes = new ArrayList<Ville>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ville;");
            while (resultSet.next())
            {
                villes.add(new Ville(resultSet.getInt(1),resultSet.getString(2),resultSet.getString(3),resultSet.getFloat(4)));
            }
            resultSet.close();
        } catch (SQLException e) {
        }
        return villes;
    }

    public static void delete(Ville ville)
    {
        ville.delete();
    }

    public void delete()
    {
        if (idVille != -1)
        {
            try {
                statement.executeUpdate("DELETE FROM ville WHERE idVille="+getIdVille()+";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        setIdVille(-1);
    }

    public void update()
    {
        if(idVille != -1)
        {
            try {
                statement.executeUpdate("UPDATE ville SET nomVille='"+getNomV()+"', agenceExpedition='"+getAgenceExpedition()+"', fraisExpedition="+getMontantExpedition()+" WHERE idVille="+getIdVille()+";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Client> loadClients()
    {
        return Client.loadAllByIdVille(idVille);
    }

    public int getNomberOfClients()
    {
        return Client.loadAllByIdVille(idVille).size();
    }


    public ArrayList<Client> loadClientsByName(String nom)
    {
    	return Client.loadAllByNameAndIdVille(idVille, nom);
    }

    public static Ville loadMostPopularVille()
    {
    	Ville ville = null;
    	ArrayList<Ville> villes = Ville.loadAll();
    	if( !villes.isEmpty())
    	{
    		int m = 0;
    		int size = villes.get(0).getNomberOfClients();
    		int tmp = 0;
    		for (int i=0; i<villes.size();i++)
    			if ((tmp = villes.get(i).getNomberOfClients()) > size)
    			{
    				m = i;
    				size = tmp;
    			}
    		ville = Ville.load(m);
    	}
    	return ville;
    }
    public static Ville loadLeastPopularVille()
    {
    	Ville ville = null;
    	ArrayList<Ville> villes = Ville.loadAll();
    	if( !villes.isEmpty())
    	{
    		int m = 0;
    		int size = villes.get(0).getNomberOfClients();
    		int tmp = 0;
    		for (int i=0; i<villes.size();i++)
    			if ((tmp = villes.get(i).getNomberOfClients()) < size)
    			{
    				m = i;
    				size = tmp;
    			}
    		ville = Ville.load(m);
    	}
    	return ville;
    }


    public int getIdVille() {
        return idVille;
    }

    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    public String getNomV() {
        return nomV;
    }

    public void setNomV(String nomV) {
        this.nomV = nomV;
    }

    public String getAgenceExpedition() {
        return agenceExpedition;
    }

    public void setAgenceExpedition(String agenceExpedition) {
        this.agenceExpedition = agenceExpedition;
    }

    public float getMontantExpedition() {
        return montantExpedition;
    }

    public void setMontantExpedition(float montantExpedition) {
        this.montantExpedition = montantExpedition;
    }
}
