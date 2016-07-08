<%-- 
    Document   : index
    Created on : 07-jul-2016, 18:48:42
    Author     : rploaiza10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="upload" method="post" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>Select File : </td>
                    <td><input  name="file" type="file"/> </td>
                </tr>
                <tr>
                    <td>Tipo Grupo : </td>
                    <td><input type="text" name="grupo" size="20"/> </td>
                </tr>
                <tr>
                    <td>Tipo Pregunta : </td>
                    <td><input type="text" name="pregunta" size="20"/> </td>
                </tr>
                <tr>
                    <td>Tipo Respuesta : </td>
                    <td><input type="text" name="respuesta" size="20"/> </td>
                </tr>
                <tr>
                    <td>Opcion Correcta : </td>
                    <td><input type="text" name="correcta" size="20"/> </td>
                </tr>
            </table>
            <p/>
            <input type="submit" value="Upload File"/>
        </form>
    </body>
</html>
