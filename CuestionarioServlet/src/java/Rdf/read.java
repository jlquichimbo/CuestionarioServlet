package Rdf;

import static Rdf.Write.generarCuestionario;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

@WebServlet(name = "read", urlPatterns = {"/upload"})     // specify urlPattern for servlet
@MultipartConfig                                               // specifies servlet takes multipart/form-data
public class read extends HttpServlet {
    
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        final String formato = request.getParameter("archivo") ;
        OntModel model = ModelFactory.createOntologyModel();
        final Part p1 = request.getPart("file");
        final InputStream is = p1.getInputStream();

//        Model model = ModelFactory.createDefaultModel();
//        // get access to file that is uploaded from client
//        final Part p1 = request.getPart("file");
//        final InputStream is = p1.getInputStream();
//        if (is == null) {
//            throw new IllegalArgumentException("File: " + p1 + " not found");
//        }
        out.println("<h3>File uploaded successfully!</h3>");
        final String optionCorrectPropertyURI = request.getParameter("correcta");
        final String groupResourceURI = request.getParameter("grupo");
        final String questionResourceURI = request.getParameter("pregunta");
        final String optionPropertyURI = request.getParameter("respuesta");
        /*WRITE JSON*/
        Map cuestionarioJson = generarCuestionario(model, groupResourceURI, questionResourceURI, optionPropertyURI, optionCorrectPropertyURI, is , formato);
        request.setAttribute("cuestionario", cuestionarioJson);
        request.getRequestDispatcher("/cuestionario.jsp").forward(request, response);
    } // end of doPost()
} // end of UploadServlet
