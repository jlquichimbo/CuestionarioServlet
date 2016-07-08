/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Rdf;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class Grupo {
    private String id;
    private String label;
    private ArrayList<Pregunta> preguntas;

    public Grupo(String id, String label, ArrayList<Pregunta> preguntas) {
        this.id = id;
        this.label = label;
        this.preguntas = preguntas;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the preguntas
     */
    public ArrayList<Pregunta> getPreguntas() {
        return preguntas;
    }

    /**
     * @param preguntas the preguntas to set
     */
    public void setPreguntas(ArrayList<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
    
    
}
