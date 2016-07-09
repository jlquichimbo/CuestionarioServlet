<%-- 
    Document   : index
    Created on : 07-jul-2016, 18:48:42
    Author     : rploaiza10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

        <div class="col-md-offset-4 "><br><br><br><br><br><br><br><br><br><br><br><br>
            <form class="col-md-12 col-sm-12" action="upload" method="post" enctype="multipart/form-data">
                

                <div class="form-group row">
                    <label for="exampleInputFile">Select File :</label>
                    <input type="file" name="file">
                </div>

                <div class="form-group row">
                    <label for="grupo" class="col-sm-2 control-label">Tipo Grupo :</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="grupo" placeholder="ex:Group">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="pregunta" class="col-sm-2 control-label">Tipo Pregunta :</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="pregunta" placeholder="ex:question">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="respuesta" class="col-sm-2 control-label">Tipo Respuesta :</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="respuesta" placeholder="ex:option">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="correcta" class="col-sm-2 control-label">Opcion Correcta :</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="correcta" placeholder="ex:correct">
                    </div>
                </div>

                <div class="form-group row">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" value="Upload File">Cargar</button>
                    </div>
                </div>
            </form>
        </div>

    </body>


</html>
