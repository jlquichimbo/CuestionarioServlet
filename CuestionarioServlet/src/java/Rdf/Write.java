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
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author USUARIO
 */
public class Write {

    public static Map generarCuestionario(Model model, String groupResourceURI,
            String questionResourceURI, String optionPropertyURI, String optionCorrectPropertyURI, InputStream is, String formato) throws JsonProcessingException {
        /*Declaracion de variables*/
//        ArrayList<String> optionsList;
        Map<String, ArrayList> groupsDictionary;
        groupsDictionary = new HashMap<>();
        //Grupo
        StmtIterator iterGroup;
        Resource actualGroupResource;
        String actualGroupString;
//        String groupResourceURI = "http://example.org/Group";
        ArrayList<Grupo> grupos;
        //Pregunta
        StmtIterator iterQuestion;
        Resource actualQuestionResource;
        String actualQuestionString;
 //       String questionPropertyURI = "http://example.org/question";
        ArrayList<Pregunta> preguntas;
        //Opcion
        StmtIterator iterOption;
        Resource actualOptionResource;
        String optionQuestionString;
        Statement correctOption;
//        String optionCorrectPropertyURI = "http://example.org/correct";
        ArrayList<Opcion> opciones;
        model.read(is, null, formato);
        System.out.println(formato);
        /*ITERATE GROUPS*/
        iterGroup = model.listStatements(new SimpleSelector(null, RDF.type, model.getResource(groupResourceURI)));
        grupos = new ArrayList<>();
        if (iterGroup.hasNext()) {
            while (iterGroup.hasNext()) {
                Grupo grupoObject;
                preguntas = new ArrayList<>();
                actualGroupResource = iterGroup.nextStatement().getSubject();
                actualGroupString = actualGroupResource.getProperty(RDFS.comment).getObject().toString();
                //iterQuestion con actual Grupo
                iterQuestion = model.listStatements(new SimpleSelector(actualGroupResource,
                        model.getProperty(questionResourceURI), (RDFNode) null));
                int i = 1;
                /*ITERATE QUESTIONS*/
                if (iterQuestion.hasNext()) {
                    while (iterQuestion.hasNext()) {
                        Pregunta objectPregunta;
                        opciones = new ArrayList<>();
                        actualQuestionResource = model.getResource(iterQuestion.nextStatement().getObject().toString());
                        actualQuestionString = actualQuestionResource.getProperty(RDFS.comment).getObject().toString();
                        correctOption = model.getRequiredProperty(actualQuestionResource, model.getProperty(optionCorrectPropertyURI));
                        //iterOption con actual Pregunta
                        iterOption = model.listStatements(
                                new SimpleSelector(actualQuestionResource,
                                        model.getProperty(optionPropertyURI), (RDFNode) null));
                        /*ITERATE OPTIONS*/
                        while (iterOption.hasNext()) {
                            Opcion opcionObject;
                            actualOptionResource = model.getResource(iterOption.nextStatement().getObject().toString());
                            optionQuestionString = actualOptionResource.getProperty(RDFS.label).getObject().toString();
                            //Objeto Opcion
                            opcionObject = new Opcion(actualOptionResource.getURI(), optionQuestionString);
                            opciones.add(opcionObject);
                        }
                        /*Generamos el objeto Pregunta*/
                        objectPregunta = new Pregunta(actualQuestionResource.getURI(),
                                actualQuestionString, correctOption.getObject().toString(), opciones);
                        preguntas.add(objectPregunta);

                        /*Generamos el diccionario de opciones para la actual pregunta*/
                        i++;
                    }
                }
                grupoObject = new Grupo(actualGroupResource.getURI(), actualGroupString, preguntas);
                grupos.add(grupoObject);
            }

        } else {
            System.out.println("No were found in the database");
        }
        groupsDictionary.put("grupos", grupos);
//        System.out.println(generateJSON(groupsDictionary));
//        return generateJSON(groupsDictionary);
        return groupsDictionary;
    }

    public static String generateJSON(HashMap groupsDictionary) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonFromMap = mapper.writeValueAsString((Map) groupsDictionary);
        return jsonFromMap;
    }

    public static void printGrupo(Grupo grupo) {
        ArrayList<Pregunta> preguntas = grupo.getPreguntas();
        System.out.println("******** GRUPO **********");
        System.out.println(grupo.getId() + " " + grupo.getLabel());
        for (Pregunta pregunta : preguntas) {
            printPregunta(pregunta);
        }
    }

    public static void printPregunta(Pregunta pregunta) {
        ArrayList<Opcion> optionsList = pregunta.getOpciones();
        System.out.println(pregunta.getId() + ": " + pregunta.getLabel());
        for (Opcion opcion : optionsList) {
            System.out.println(opcion.getId() + ": " + opcion.getLabel());
        }
        System.out.println("Correcta: " + pregunta.getIdCorrect());
    }
}