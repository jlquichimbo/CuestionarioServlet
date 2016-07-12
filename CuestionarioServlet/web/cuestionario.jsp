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
                    <a class="navbar-brand" href="#">Cuestionario Rdf</a>
                </div>
            </div>
        </nav>

        <!-- Main jumbotron for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container">
                <p class="text-right">Desarrolle el presente examen</p>
                <!--<p><a id="get_data" class="btn btn-primary btn-lg" href="#" role="button">Mostrar Datos &raquo;</a></p>-->
                <div id="json_out">

                </div>
            </div>
        </div>

        <div class="container">
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <div class="row containerGroups">
                <c:choose>
                    <c:when test="${fn:length(cuestionario.grupos) == 0}">
                        <!--if grupos.lenght() == 0. Si no se encuentra preguntas-->
                        <div class="alert alert-danger">
                            <p>Error al cargar grupos. Verifique el archivo o la variable para la entidad Grupo</p>
                        </div>
                    </c:when>

                    <c:otherwise>
                        <!--Caso contrario recorrer grupos-->
                        <c:forEach items="${cuestionario.grupos}" var="grupo">
                            <h2 class="group">${grupo.label}</h2>
                            <c:choose>
                                <c:when test="${fn:length(grupo.preguntas) == 0}">
                                    <!--if preguntas.lenght() == 0. Si no se encuentra preguntas-->
                                    <div class="alert alert-danger">
                                        <p>Error al cargar Preguntas. Verifique el archivo o la variable para el predicado pregunta</p>
                                    </div>
                                </c:when>

                                <c:otherwise>   
                                    <!--Caso contrario recorrer preguntas-->
                                    <c:forEach items="${grupo.preguntas}" var="pregunta">
                                        <fieldset class="preguntaFieldset">
                                            <legend class="question">${pregunta.label}</legend>
                                            <c:choose>
                                                <c:when test="${fn:length(pregunta.opciones) == 0}">
                                                    <!--if opciones.lenght() == 0. Si no se encuentran opciones/respuestas-->
                                                    <div class="alert alert-danger">
                                                        <p>Error al cargar Opciones. Verifique el archivo o la variable para el predicado respuesta</p>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <!--Caso contrario iterar respuestas-->
                                                    <c:forEach items="${pregunta.opciones}" var="opcion">
                                                        <div class="opcionesDiv" name="${opcion.id}">
                                                            <input class="respuestas" pregunta="${pregunta.id}" correct="${pregunta.idCorrect}" 
                                                                   type="radio" name="${pregunta.id}" value="${opcion.id}" /> ${opcion.label}<br />
                                                        </div>
                                                    </c:forEach>
                                                </c:otherwise> 
                                            </c:choose>
                                        </fieldset>  
                                        <br>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            <input id="terminarBtn" class="pull-right btn btn-success" type="button" value="Calificar"/>
            <input onclick="location.href = 'upload.jsp';" class="pull-right btn btn-error" type="button" value="Atras"/>
        </div> <!-- /container --> 


        <hr>
    </body>
    <footer>
        <p class="text-center">&copy; Roberth Loaiza, Jose Quichimbo 2016</p>
    </footer>
    <script>
//        CALIFICACION VIA SCRIPT
        $("#terminarBtn").click(function () {
            var optionSelected;
            var optionCorrect;
            console.log("Preguntas seleccionadas");
            $(".respuestas:checked").each(function () {
                optionSelected = $(this).val();
                optionCorrect = $(this).attr("correct");
                if (optionSelected == optionCorrect) {
                    $("div[name='" + optionSelected + "']").addClass("alert alert-success");
                } else {
                    $("div[name='" + optionSelected + "']").addClass("alert alert-danger");
                    $("div[name='" + optionCorrect + "']").addClass("alert alert-success");

                }
//                console.log("selected:"+ optionSelected+"     Correct:"+optionCorrect);
            });
        });
    </script>
</html>
