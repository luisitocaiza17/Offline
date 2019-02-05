package micotizador.offline.filestructure;

/**
 * Created by Admin on 06/07/2015.
 */
public class Rule {

    public String getRuleName() {
        return RuleName;
    }

    public void setRuleName(String ruleName) {
        RuleName = ruleName;
    }

    public int getRuleID() {
        return RuleID;
    }

    public void setRuleID(int ruleID) {
        RuleID = ruleID;
    }

    private int RuleID;
    private String RuleName;
}
