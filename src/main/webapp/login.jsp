<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- Coding By CodingNepal - www.codingnepalweb.com -->
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Facebook Login </title>
    <link rel="stylesheet" href="css/login.css">
  </head>
  <body>
    <div class="container flex">
      <div class="facebook-page flex">
        <div class="text">
          <h1>facebook</h1>
          <p>Connecter avec vos amis et tout le monde </p>
          <p> facebook bien veiller sur vous</p>
        </div>
        <form  action="home.jsp" method="get">
          <input type="email" placeholder="Adresse email ou numero telephone" required>
          <input type="password" placeholder="Mot de passe" required>
          <div class="link">
            <button type="submit" class="login">Se connecter</button>
            <a href="#" class="forgot">Oublié mot de passe?</a>
          </div>
          <hr>
          <div class="button">
            <a href="home.jsp">Créer nouveau compte</a>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>