/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.jena.atlas.json.JsonObject;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.log4j.varia.NullAppender;
/**
 *
 * @author USUARIO
 */
public class Write {
    
     public static void main(String[] args) throws JsonProcessingException {
        // TODO code application logic here      
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());

        /*Declaracion de variables*/
        ArrayList<String> optionsList;
        HashMap <String, ArrayList> questionsDicctionary;
        questionsDicctionary = new HashMap<>();
        StmtIterator iterQuestion;
        StmtIterator iterOption;
        Resource actualQuestion;
        String actualQuestionString;
        Resource actualOption;
        Statement correctOption;
        String optionQuestionString;
        String urlRead = "cuestionario.rdf";
//        String urlRead = "cuestionario.ttl";
        Model model = ModelFactory.createDefaultModel();
        readRDF(model, urlRead);
        //property option
        String optionPropertyURI = "http://example.org/option";
        String optionCorrectPropertyURI = "http://example.org/correct";
        String questionResourceURI = "http://example.org/Question";

        // list questions
        // set up the output
        System.out.println("The questions are:");
        iterQuestion = model.listStatements(new SimpleSelector(null, RDF.type, model.getResource(questionResourceURI)));
        int i = 1;
        if (iterQuestion.hasNext()) {
            while (iterQuestion.hasNext()) {
                optionsList = new ArrayList<>();
                actualQuestion = iterQuestion.nextStatement().getSubject();
                actualQuestionString = actualQuestion.getProperty(RDFS.label).getObject().toString();
//                System.out.println(i + "  " + actualQuestionString);
                iterOption = model.listStatements(
                        new SimpleSelector(actualQuestion,
                                model.getProperty(optionPropertyURI), (RDFNode) null));
                correctOption = model.getRequiredProperty(actualQuestion, model.getProperty(optionCorrectPropertyURI));
                //**Recorre las opciones del objeto iterOption
                while (iterOption.hasNext()) {
                    actualOption = model.getResource(iterOption.nextStatement().getObject().toString());
                    optionQuestionString = actualOption.getProperty(RDFS.label).getObject().toString();
                    optionsList.add(optionQuestionString);
//                    if (correctOption.getObject().toString().equals(actualOption.toString())) {
//                        optionQuestionString +=  "** Correcta";
//                        optionsList.add(optionQuestionString);
//                    } 
//                    System.out.println("  " + optionQuestionString);
                }
                /*Genneramos el diccionario de opciones para la actual pregunta*/
                questionsDicctionary.put(actualQuestionString, optionsList);
//                System.out.println(optionsList);
                i++;
            }
        } else {
            System.out.println("No were found in the database");
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonFromMap = mapper.writeValueAsString((Map)questionsDicctionary);
        System.out.println(jsonFromMap);
//        printModel(model);
//        model.write(System.out, "JSON-LD");    
//        model.write(System.out, "RDF/JSON");
    }

    public static void readRDF(Model model, String urlRead) {
        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(urlRead);
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + urlRead + " not found");
        }

        // read the RDF/XML file
//        model.read(in, "TTL");
//        model.read(urlRead, "TURTLE");
        model.read(urlRead, "RDF/XML");
    }
    
}
