package micotizador.offline.filestructure;

import java.util.List;

/**
 * Created by Admin on 06/07/2015.
 */
public class Structure {

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public List<Rule> getRules() {
        return Rules;
    }

    public void setRules(List<Rule> rules) {
        Rules = rules;
    }

    public List<Catalog> getCatalogs() {
        return Catalogs;
    }

    public void setCatalogs(List<Catalog> catalogs) {
        Catalogs = catalogs;
    }

    private int Version;
    private List<Rule> Rules;
    private List<Catalog> Catalogs;

}
