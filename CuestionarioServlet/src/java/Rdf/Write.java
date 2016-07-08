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
//        ArrayList<String> optionsList;
        HashMap<String, ArrayList> questionsDicctionary;
        questionsDicctionary = new HashMap<>();
        //Grupo
        StmtIterator iterGroup;
        Resource actualGroupResource;
        String actualGroupString;
        String groupResourceURI = "http://example.org/Group";
        //Pregunta
        StmtIterator iterQuestion;
        Resource actualQuestionResource;
        String actualQuestionString;
        String questionResourceURI = "http://example.org/Question";
        String questionPropertyURI = "http://example.org/question";
        //Opcion
        StmtIterator iterOption;
        Resource actualOptionResource;
        String optionQuestionString;
        Statement correctOption;
        String optionPropertyURI = "http://example.org/option";
        String optionCorrectPropertyURI = "http://example.org/correct";
        ArrayList<Opcion> optionsList;

        /*Lectura del archivo .rdf o .ttl*/
//        String urlRead = "cuestionario.rdf";
        String urlRead = "cuestionario.ttl";
        Model model = ModelFactory.createDefaultModel();
        readRDF(model, urlRead);

        /*ITERATE GROUPS*/
        iterGroup = model.listStatements(new SimpleSelector(null, RDF.type, model.getResource(groupResourceURI)));
        if (iterGroup.hasNext()) {
            while (iterGroup.hasNext()) {
                actualGroupResource = iterGroup.nextStatement().getSubject();
                actualGroupString = actualGroupResource.getProperty(RDFS.label).getObject().toString();
//                System.out.println(" " + actualGroupString);
                // list questions
                iterQuestion = model.listStatements(new SimpleSelector(actualGroupResource,
                        model.getProperty(questionPropertyURI), (RDFNode) null));
                int i = 1;
                /*ITERATE QUESTIONS*/
                if (iterQuestion.hasNext()) {
                    while (iterQuestion.hasNext()) {
                        optionsList = new ArrayList<>();
                        actualQuestionResource = model.getResource(iterQuestion.nextStatement().getObject().toString());
//                        actualQuestionResource = iterQuestion.nextStatement().getSubject();
                        actualQuestionString = actualQuestionResource.getProperty(RDFS.label).getObject().toString();
                        System.out.println(i + "  " + actualQuestionString);
                        iterOption = model.listStatements(
                                new SimpleSelector(actualQuestionResource,
                                        model.getProperty(optionPropertyURI), (RDFNode) null));
                        /*ArrayList de Opciones*/
//                        correctOption = model.getRequiredProperty(actualQuestionResource, model.getProperty(optionCorrectPropertyURI));
                        /*ITERATE OPTIONS*/
                        while (iterOption.hasNext()) {
                            Opcion newOpcionObject;
                            actualOptionResource = model.getResource(iterOption.nextStatement().getObject().toString());
                            optionQuestionString = actualOptionResource.getProperty(RDFS.label).getObject().toString();
                            newOpcionObject = new Opcion(actualOptionResource.getURI(), optionQuestionString);
                            optionsList.add(newOpcionObject);
                            System.out.println(newOpcionObject.getId() +": "+newOpcionObject.getLabel());
//                    if (correctOption.getObject().toString().equals(actualOption.toString())) {
//                        optionQuestionString +=  "** Correcta";
//                        optionsList.add(optionQuestionString);
//                    } 
//                            System.out.println("  " + optionQuestionString);
                        }
                        /*Genneramos el diccionario de opciones para la actual pregunta*/
                        questionsDicctionary.put(actualQuestionString, optionsList);
                        System.out.println(optionsList);
                        i++;
                    }
                }
            }

        } else {
            System.out.println("No were found in the database");
        }
        ObjectMapper mapper = new ObjectMapper();
        String jsonFromMap = mapper.writeValueAsString((Map) questionsDicctionary);
//        System.out.println(jsonFromMap);
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
        model.read(urlRead, "TURTLE");
//        model.read(urlRead, "RDF/XML");
    }

}
