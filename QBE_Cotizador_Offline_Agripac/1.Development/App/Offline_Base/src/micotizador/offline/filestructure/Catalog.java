package micotizador.offline.filestructure;

/**
 * Created by Admin on 06/07/2015.
 */
public class Catalog {
    public int getCatalogID() {
        return CatalogID;
    }

    public void setCatalogID(int catalogID) {
        CatalogID = catalogID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private int CatalogID;
    private String Name;
}
