<%-- 
    Document   : cuestionario
    Created on : 08/07/2016, 12:26:39
    Author     : USUARIO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cuestionario View</title>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Cuestionario Viewer</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <form class="navbar-form navbar-right" role="form">
                        <div class="form-group">
                            <input type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Sign in</button>
                    </form>
                </div><!--/.navbar-collapse -->
            </div>
        </nav>

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container">
                <h1>Cuestionario</h1>
                <!--<p><a id="get_data" class="btn btn-primary btn-lg" href="#" role="button">Mostrar Datos &raquo;</a></p>-->
                <div id="json_out">

                </div>
            </div>
        </div>

        <div class="container">
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <div class="row containerGroups">
                <c:forEach items="${cuestionario.grupos}" var="grupo">
                    <h2 class="group">${grupo.label}</h2>
                    <c:forEach items="${grupo.preguntas}" var="pregunta">
                        <fieldset class="preguntaFieldset">
                            <legend class="question">${pregunta.label}</legend>
                            <c:forEach items="${pregunta.opciones}" var="opcion">
                                <input type="radio" name="${pregunta.id}" value="Cat" />${opcion.label}<br />
                            </c:forEach>
                        </fieldset>  
                        <br>
                    </c:forEach>
                </c:forEach>
            </div>

            <hr>

            <footer>
                <p>&copy; Roberth Loaiza, Jose Quichimbo 2016</p>
            </footer>
        </div> <!-- /container --> 
    </body>
    <script>
        var cuestionarioJson = {"http://example.org/Grupo2":{"id":"http://example.org/Grupo2", "label":"Capitulo 2", "preguntas":[{"id":"http://example.org/Pregunta8", "label":"Como se compone una tripleta?", "idCorrect":"http://example.org/Alternativa8b", "opciones":[{"id":"http://example.org/Alternativa8d", "label":"Modelo, grafo, objeto"}, {"id":"http://example.org/Alternativa8c", "label":"Secuencia, objeto, grafo"}, {"id":"http://example.org/Alternativa8b", "label":"Sujeto, predicado, objeto"}, {"id":"http://example.org/Alternativa8a", "label":"Sujeto, patron, objeto"}]}, {"id":"http://example.org/Pregunta7", "label":"Que es RDF", "idCorrect":"http://example.org/Alternativa7d", "opciones":[{"id":"http://example.org/Alternativa7d", "label":"Un modelo basado en grafos que permite generar vocabularios"}, {"id":"http://example.org/Alternativa7c", "label":"Una secuencia de instrucciones"}, {"id":"http://example.org/Alternativa7b", "label":"Una alternativa a big data"}, {"id":"http://example.org/Alternativa7a", "label":"Un lenguaje de programacion"}]}, {"id":"http://example.org/Pregunta6", "label":"Que es una ontologia?", "idCorrect":"http://example.org/Alternativa6d", "opciones":[{"id":"http://example.org/Alternativa6d", "label":"Es una representacion de datos"}, {"id":"http://example.org/Alternativa6c", "label":"Es un formato big data"}, {"id":"http://example.org/Alternativa6b", "label":"Es un mecanismo de linked data"}, {"id":"http://example.org/Alternativa6a", "label":"Es un conjunto de funciones python"}]}, {"id":"http://example.org/Pregunta5", "label":"Cual de las siguientes definiciones corresponde a SBC?", "idCorrect":"http://example.org/Alternativa5b", "opciones":[{"id":"http://example.org/Alternativa5d", "label":"Es una metodologia basada en conocimiento"}, {"id":"http://example.org/Alternativa5c", "label":"Es una arquitectura de desarrollo"}, {"id":"http://example.org/Alternativa5b", "label":"Mantiene una gran cantidad de conocimiento y aportan mecanismos para manejarlo"}, {"id":"http://example.org/Alternativa5a", "label":"Es un conjunto de funciones java"}]}]}, "http://example.org/Grupo1":{"id":"http://example.org/Grupo1", "label":"Capitulo 1", "preguntas":[{"id":"http://example.org/Pregunta4", "label":"Que propiedad de vcard identifica el nombre completo?", "idCorrect":"http://example.org/Alternativa4c", "opciones":[{"id":"http://example.org/Alternativa4d", "label":"vcard.full"}, {"id":"http://example.org/Alternativa4c", "label":"vcard.FN"}, {"id":"http://example.org/Alternativa4b", "label":"vcard.given"}, {"id":"http://example.org/Alternativa4a", "label":"vcard.n"}]}, {"id":"http://example.org/Pregunta3", "label":"Cual metodo de Jena lista las tripletas?", "idCorrect":"http://example.org/Alternativa3a", "opciones":[{"id":"http://example.org/Alternativa3d", "label":"Model.listSubjectsWithProperty()"}, {"id":"http://example.org/Alternativa3c", "label":"Model.listProperties()"}, {"id":"http://example.org/Alternativa3b", "label":"Model.listSubjects()"}, {"id":"http://example.org/Alternativa3a", "label":"Model.listStatements()"}]}, {"id":"http://example.org/Pregunta2", "label":"Cual es el formato de una tripleta?", "idCorrect":"http://example.org/Alternativa2a", "opciones":[{"id":"http://example.org/Alternativa2d", "label":"RDF"}, {"id":"http://example.org/Alternativa2c", "label":"XML"}, {"id":"http://example.org/Alternativa2b", "label":"JSON"}, {"id":"http://example.org/Alternativa2a", "label":"N-TRIPLES"}]}, {"id":"http://example.org/Pregunta1", "label":"Cuantos elementos tiene un triplete?", "idCorrect":"http://example.org/Alternativa1c", "opciones":[{"id":"http://example.org/Alternativa1d", "label":"4 Elementos"}, {"id":"http://example.org/Alternativa1c", "label":"3 Elementos"}, {"id":"http://example.org/Alternativa1b", "label":"2 Elementos"}, {"id":"http://example.org/Alternativa1a", "label":"1 Elementos"}]}]}}
    </script>
</html>
