package data.serializer;

import ofar.generated.classes.solveRequest.SolveRequest;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

public class DataUnmarshaller {

    public static SolveRequest unmarshallData(String fileName, String schemaPath, String contextPath) throws SAXException, JAXBException {
        SolveRequest solveRequest;
        try {
            // initialize JAXBContext and create unmarshaller
            //contextPath = "ofar.generated.classes.solveRequest"
            JAXBContext jc = JAXBContext.newInstance(contextPath);
            Unmarshaller u = jc.createUnmarshaller();

            // set validation wrt schema using default validation handler (rises exception with non-valid files)
            SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));
            u.setSchema(schema);

            // unmarshal file named fname
            solveRequest = (SolveRequest) u.unmarshal(new File(fileName));
            System.out.println(solveRequest);


        } catch (SAXException se) {
            System.out.println("Unable to validate schema");
            throw se;
        }
        return solveRequest;
    }

    public static void main(String[] args) throws JAXBException, SAXException {
        DataUnmarshaller.unmarshallData("xsd/solve_request.xml","xsd/solve_request.xsd","ofar.generated.classes.solveRequest");
    }

}