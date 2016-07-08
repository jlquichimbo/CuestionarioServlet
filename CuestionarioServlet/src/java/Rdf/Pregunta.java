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
public class Pregunta {
    private String id;
    private String label;
    private String idCorrect;
    private ArrayList<Opcion> opciones;

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
     * @return the idCorrect
     */
    public String getIdCorrect() {
        return idCorrect;
    }

    /**
     * @param idCorrect the idCorrect to set
     */
    public void setIdCorrect(String idCorrect) {
        this.idCorrect = idCorrect;
    }

    /**
     * @return the opciones
     */
    public ArrayList<Opcion> getOpciones() {
        return opciones;
    }

    /**
     * @param opciones the opciones to set
     */
    public void setOpciones(ArrayList<Opcion> opciones) {
        this.opciones = opciones;
    }
    
}
