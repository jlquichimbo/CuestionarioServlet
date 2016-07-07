/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

import java.io.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 *
 * @author rploaiza10
 */
public class logica extends HttpServlet {

    public static String optionPropertyURI;
    public static String inputFileName = "practica.rdf";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the FileManager to find the input file
        InputStream in = FileManager.get().open(inputFileName);
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, "");

        //property option
        String optionPropertyURI = "ex:option";
        String OpcionC = "ex:correct";
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
                System.out.println(actualQuestion.getLocalName() + " : " + prop1.nextResource().
                        getRequiredProperty(RDFS.comment).getString());

                iterOption = model.listStatements(
                        new SimpleSelector(actualQuestion,
                                model.getProperty(optionPropertyURI), (RDFNode) null));
                correct = model.getRequiredProperty(actualQuestion, model.getProperty(OpcionC));
                while (iterOption.hasNext()) {
                    actualOption = model.getResource(iterOption.nextStatement().getObject().toString());
                    option = actualOption.getProperty(RDFS.label).getString();
                    if (correct.getObject().toString().equals(actualOption.toString())) {
                        System.out.println(option + "*");

                    } else {
                        System.out.println(option);
                    }
                }
            }
        } else {
            System.out.println("No were found in the database");
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            optionPropertyURI = request.getParameter("capitulo");
            String pre = request.getParameter("pregunta");
            // String res = request.getParameter("respuesta");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet logica</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>El nombre ingresado es: " + optionPropertyURI + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
