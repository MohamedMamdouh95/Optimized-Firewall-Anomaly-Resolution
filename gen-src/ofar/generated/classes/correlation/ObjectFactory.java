
package ofar.generated.classes.correlation;

import ofar.generated.classes.rules.RuleType;
import ofar.generated.classes.rules.Rules;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ofar.generated.classes.correlation package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CorrelationSolution_QNAME = new QName("", "CorrelationSolution");
    private final static QName _Rules_QNAME = new QName("", "rules");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ofar.generated.classes.correlation
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CorrelationSolutionType }
     * 
     */
    public CorrelationSolutionType createCorrelationSolutionType() {
        return new CorrelationSolutionType();
    }

    /**
     * Create an instance of {@link Rules }
     * 
     */
    public Rules createRules() {
        return new Rules();
    }

    /**
     * Create an instance of {@link RuleType }
     * 
     */
    public RuleType createRuleType() {
        return new RuleType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CorrelationSolutionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CorrelationSolution")
    public JAXBElement<CorrelationSolutionType> createCorrelationSolution(CorrelationSolutionType value) {
        return new JAXBElement<CorrelationSolutionType>(_CorrelationSolution_QNAME, CorrelationSolutionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Rules }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "rules")
    public JAXBElement<Rules> createRules(Rules value) {
        return new JAXBElement<Rules>(_Rules_QNAME, Rules.class, null, value);
    }

}
