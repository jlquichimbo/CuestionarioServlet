package Rdf;

import static Rdf.Write.generateJSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

@WebServlet(name = "read", urlPatterns = {"/upload"})     // specify urlPattern for servlet
@MultipartConfig                                               // specifies servlet takes multipart/form-data
public class read extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Model model = ModelFactory.createDefaultModel();

        // get access to file that is uploaded from client
        Part p1 = request.getPart("file");
        InputStream is = p1.getInputStream();
        if (is == null) {
            throw new IllegalArgumentException("File: " + p1 + " not found");
        }
        model.read(is, "");
        out.println("<h3>File uploaded successfully!</h3>");
        //property option
        // String optionPropertyURI = "ex:option";
        //String OpcionC = "ex:correct";
        String optionCorrectPropertyURI = request.getParameter("correcta");
        String groupResourceURI = request.getParameter("grupo");
        String questionResourceURI = request.getParameter("pregunta");
        String optionPropertyURI = request.getParameter("respuesta");


        /*WRITE JSON*/
        Map cuestionarioJson = generarCuestionario(model, groupResourceURI, questionResourceURI, optionPropertyURI, optionCorrectPropertyURI);
        request.setAttribute("cuestionario", cuestionarioJson);
        request.getRequestDispatcher("/cuestionario.jsp").forward(request, response);

    } // end of doPost()

    public static Map generarCuestionario(Model model, String groupResourceURI,
            String questionResourceURI, String optionPropertyURI, String optionCorrectPropertyURI) throws JsonProcessingException {
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
//        String questionResourceURI = "http://example.org/Question";
        String questionPropertyURI = "http://example.org/question";
        ArrayList<Pregunta> preguntas;
        //Opcion
        StmtIterator iterOption;
        Resource actualOptionResource;
        String optionQuestionString;
        Statement correctOption;
//        String optionPropertyURI = "http://example.org/option";
//        String optionCorrectPropertyURI = "http://example.org/correct";
        ArrayList<Opcion> opciones;

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
                        model.getProperty(questionPropertyURI), (RDFNode) null));
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

} // end of UploadServlet
