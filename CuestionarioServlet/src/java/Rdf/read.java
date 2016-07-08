package Rdf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
import org.apache.jena.vocabulary.RDFS;

@WebServlet(name="read", urlPatterns={"/upload"})     // specify urlPattern for servlet
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
        String optionPropertyURI = request.getParameter("pregunta");
        String OpcionC = request.getParameter("correcta");
        Property optionProperty = model.createProperty(optionPropertyURI);
        // select all the resources with a VCARD.FN property

        ResIterator iter = model.listResourcesWithProperty(optionProperty);
        ResIterator prop1 = model.listSubjectsWithProperty(RDFS.comment);

        StmtIterator iterOption;
        Resource actualQuestion;
        Resource actualOption;
        String option;

        Statement correct;
        if (iter.hasNext()) {
            while (iter.hasNext()) {
                actualQuestion = iter.nextResource();
                out.println(actualQuestion.getLocalName() + " : " + prop1.nextResource().
                        getRequiredProperty(RDFS.comment).getString()+"<br>");

                iterOption = model.listStatements(
                        new SimpleSelector(actualQuestion,
                                model.getProperty(optionPropertyURI), (RDFNode) null));
                correct = model.getRequiredProperty(actualQuestion, model.getProperty(OpcionC));
                while (iterOption.hasNext()) {
                    actualOption = model.getResource(iterOption.nextStatement().getObject().toString());
                    option = actualOption.getProperty(RDFS.label).getString();
                    if (correct.getObject().toString().equals(actualOption.toString())) {
                        out.println(option + "*<br>");

                    } else {
                        out.println(option+"<br>");
                    }
                }
            }
        } else {
            System.out.println("No were found in the database");
        }

    } // end of doPost()
} // end of UploadServlet