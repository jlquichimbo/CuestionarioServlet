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
                <p class="text-left bold">Desarrolle el presente examen</p>
                <div class="form-group col-md-3">
                    <label for="sobre">El examen se califica sobre:</label>
                    <input type="text" class="form-control" id="sobreCalificacion" value="10" form="formCalificar">
                </div>
            </div>
        </div>

        <div class="container">
            <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
            <%! int countPreguntas = 0;%>
            <%! int countCorrectas = 0;%>

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
                                        <% countPreguntas++;%>
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
            <form id="formCalificar">
                <input type="hidden" id="numPreguntas" name="numPreguntas" value="<%=countPreguntas%>">
                <input type="hidden" id="numCorrectas" name="numCorrectas">
                <input type="hidden" id="calificacion" name="calificacion">
                <input type="hidden" id="calificacion" name="sobre">
                <div id="divCalificacion" style="display: none"></div>
                <button id="terminarBtn" type="submit" class="pull-right btn btn-success">Calificar</button>
                <!--<input id="terminarBtn" class="pull-right btn btn-success" type="button" value="Calificar"/>-->
            </form>
            <input onclick="location.href = 'upload.jsp';" class="pull-right btn btn-error" type="button" value="Atras"/>
        </div> <!-- /container --> 

        <div id="result"></div>

        <hr>
    </body>
    <footer>
        <p class="text-center">&copy; Roberth Loaiza, Jose Quichimbo 2016</p>
    </footer>
    <script>
        //        CALIFICACION VIA SCRIPT
        $("#terminarBtn").click(function (event) {
            event.preventDefault();

            var numPreguntas = 0;
            var countCorrectas = 0;
            var optionSelected;
            var optionCorrect;
            var sobreCalificacion = $("#sobreCalificacion").val();
            $(".respuestas:checked").each(function () {
                numPreguntas++;
                optionSelected = $(this).val();
                optionCorrect = $(this).attr("correct");
                if (optionSelected == optionCorrect) {//Respuesta correcta
                    countCorrectas++;
                    $("div[name='" + optionSelected + "']").addClass("alert alert-success");

                } else {//Respuesta incorrecta
                    $("div[name='" + optionSelected + "']").addClass("alert alert-danger");
                    $("div[name='" + optionCorrect + "']").addClass("alert alert-success");

                }
            });
            $("#numCorrectas").val(countCorrectas);
            var calificacion = reglaDeTres(numPreguntas, countCorrectas, sobreCalificacion);
            calificacion = calificacion.toFixed(2);
            $("#calificacion").val(calificacion);
            $("#sobre").val(sobreCalificacion);
            $("#divCalificacion")
                    .show(1000)
                    .html("<h2>CALIFICACION: " + calificacion + "/" + sobreCalificacion + "</h2>")
                    .focus();
            //Funcion para enviar por ajax y no se pierda la calificacion jQuery
            sendAjax();
        });

        function reglaDeTres(numPreguntas, numCorrectas, sobreCalificacion) {
            var calificacion;
            calificacion = (numCorrectas * sobreCalificacion) / numPreguntas;
            return calificacion;
        }

        //Envia el formulario por ajax, para generar el resultado en rdf
        function sendAjax() {
            $.ajax({
                url: 'Calificacion',
                data: $("#formCalificar").serialize(),
                success: function (data) {
                    $('#result').html(data);
                }
            });
        }
    </script>
</html>
