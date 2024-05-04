<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Welcome to this amazing web application</title>
</head>
<body>
    <CENTER><H1>Tentez votre chance à cette loterie virtuelle !</H1></CENTER>
    <form action="./hello" method="post">
        Votre nom svp : <input type="text" name="nom"><br>
        <br>
        Opération :
        <input type="radio" name="action" value="submit"> Soumettre
        <input type="radio" name="action" value="delete"> Supprimer
        <input type="radio" name="action" value="update"> Mettre à jour<br>
        <br>
        <center><input type="submit" name="Submit Query" value="Soumettre"></center>
    </form>
</body>
</html>
