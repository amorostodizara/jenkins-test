<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<!-- <meta charset="ISO-8859-1"> -->
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css" rel="stylesheet" />
    <title>Salle de Classe </title>
    <link rel="stylesheet" href="css/home.css" />
    <link href="css/output.css" rel="stylesheet">
</head>
<body>
    <!-- navbar -->
    <nav class="navbar">
      <div class="logo_item">
        <i class="bx bx-menu" id="sidebarOpen"></i>
        <img src="images/logonita.png" alt="">GestionSalleDeClasse</i>
      </div>
<!--       <div class="search_bar"> -->
<!--         <input type="text" placeholder="Recherche..." /> -->
<!--       </div> -->

<div class="search_bar">
    <input type="text" id="searchInput" placeholder="Recherche..." oninput="searchProfs(this.value)">
</div>
      <div class="navbar_content">
        <i class="bi bi-grid"></i>
        <i class='bx bx-sun' id="darkLight"></i>
        <i class='bx bx-bell' ></i>
       <a href="index.jsp"><img src="images/profile.png" alt="" class="profile" /></a>
      </div>
    </nav>
    
    <!-- sidebar -->
    <nav class="sidebar">
      <div class="menu_content">
        <ul class="menu_items">
          <div class="menu_title menu_dahsboard"></div>
       
          <li class="item">
            <div href="#" class="nav_link submenu_item">
              <span class="navlink_icon">
                <i class="bx bx-home-alt"></i>
              </span>
              <span class="navlink">Accueil</span>
              <i class="bx bx-chevron-right arrow-left"></i>
            </div>
            <ul class="menu_items submenu">
              <a href="#" class="nav_link sublink" onclick="showTable('professeurs')">Professeurs</a>
              <a href="#" class="nav_link sublink" onclick="showTable('salles')">Salles</a>
              <a href="#" class="nav_link sublink" onclick="showTable('occupations')">Occuper</a>
              <a href="#" class="nav_link sublink">Rien à faire</a>
            </ul>
          </li>
         
        <div class="bottom_content">
          <div class="bottom expand_sidebar">
            <span> Expand</span>
            <i class='bx bx-log-in' ></i>
          </div>
          <div class="bottom collapse_sidebar">
            <span> Collapse</span>
            <i class='bx bx-log-out'></i>
          </div>
        </div>
      </div>
    </nav>
  
<div id="professeurs" class="table-container">
    <h2>Professeurs 
        <i class="bx bx-plus" onclick="openModal('addProfModal')"
           style="cursor: pointer; float: right; height:32px; width:32px; color:blue;"></i>
    </h2>
    <table>
        <thead>
            <tr>
                <th>Code Prof</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Grade</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Les lignes seront ajoutées dynamiquement par JavaScript -->
        </tbody>
    </table>
</div>

<div id="salles" class="table-container hidden">
    <h2>Salles
        <i class="bx bx-plus" onclick="openModal('addSalleModal')"
           style="cursor: pointer; float: right; height:32px; width:32px; color:blue;"></i>
    </h2>
    <table>
        <thead>
            <tr>
                <th>Code Salle</th>
                <th>Désignation</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Les lignes seront ajoutées dynamiquement par JavaScript -->
        </tbody>
    </table>
</div>

<div id="occupations" class="table-container hidden">
    <h2>Occupations
        <i class="bx bx-plus" onclick="openModal('addOccuperModal')"
           style="cursor: pointer; float: right; height:32px; width:32px; color:blue;"></i>
    </h2>
    <table>
        <thead>
            <tr>
                <th>Code Prof</th>
                <th>Code Salle</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Les lignes seront ajoutées dynamiquement par JavaScript -->
        </tbody>
    </table>
</div>

<!-- Les modals -->

<!-- Modal pour ajouter un professeur -->
<div id="addProfModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('addProfModal')">&times;</span>
        <h2>Ajouter un Professeur</h2>

<form id="addProfForm" onsubmit="event.preventDefault(); addProf(codeprof.value, nom.value, prenom.value, grade.value);">
    <label for="codeprof">Code Prof:</label>
    <input type="text" id="codeprof" name="codeprof" required>
    <label for="nom">Nom:</label>
    <input type="text" id="nom" name="nom" required>
    <label for="prenom">Prénom:</label>
    <input type="text" id="prenom" name="prenom" required>
    <label for="grade">Grade:</label>
    <input type="text" id="grade" name="grade" required>
    <button type="submit">Ajouter</button>
</form>
    </div>
</div>

<!-- Modal pour ajouter une salle -->
<div id="addSalleModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('addSalleModal')">&times;</span>
        <h2>Ajouter une Salle</h2>
        <form id="addSalleForm" onsubmit="event.preventDefault(); addSalle(codesal.value, designation.value);">
            <label for="codesal">Code Salle:</label>
            <input type="text" id="codesal" name="codesal" required>
            
            <label for="designation">Désignation:</label>
            <input type="text" id="designation" name="designation" required>
            
            <button type="submit">Ajouter</button>
        </form>
    </div>
</div>


<!-- Modal pour modifier un professeur -->
<div id="updateProfModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('updateProfModal')">&times;</span>
        <h2>Modifier un Professeur</h2>
<form id="updateProfForm" onsubmit="event.preventDefault(); updateProf(updateCodeprof.value, updateNom.value, updatePrenom.value, updateGrade.value);">
    <label for="updateCodeprof">Code Prof:</label>
    <input type="text" id="updateCodeprof" name="updateCodeprof" readonly>
    <label for="updateNom">Nom:</label>
    <input type="text" id="updateNom" name="updateNom" required>
    <label for="updatePrenom">Prénom:</label>
    <input type="text" id="updatePrenom" name="updatePrenom" required>
    <label for="updateGrade">Grade:</label>
    <input type="text" id="updateGrade" name="updateGrade" required>
    <button type="submit">Mettre à jour</button>
</form>
    </div>
</div>


<!-- Modal pour modifier une salle -->
<div id="updateSalleModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('updateSalleModal')">&times;</span>
        <h2>Modifier une Salle</h2>
        <form id="updateSalleForm" onsubmit="event.preventDefault(); updateSalle(updateCodesal.value, updateDesignation.value);">
            <label for="updateCodesal">Code Salle:</label>
            <input type="text" id="updateCodesal" name="updateCodesal" readonly>
            
            <label for="updateDesignation">Désignation:</label>
            <input type="text" id="updateDesignation" name="updateDesignation" required>
            
            <button type="submit">Mettre à jour</button>
        </form>
    </div>
</div>


<!-- Modal pour ajouter une occupation -->
<div id="addOccuperModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('addOccuperModal')">&times;</span>
        <h2>Ajouter une Occupation</h2>
        <form id="addOccuperForm" onsubmit="event.preventDefault(); addOccuper();">
            <label for="optcodeprof">Code Prof:</label>
            <select id="optcodeprof" name="optcodeprof" required></select>
            
            <label for="optcodesal">Code Salle:</label>
            <select id="optcodesal" name="optcodesal" required></select>
            
            <label for="date">Date:</label>
            <input type="date" id="date" name="date" required>
            
            <button type="submit">Ajouter</button>
        </form>
    </div>
</div>

<!-- Modal pour modifier une occupation -->
<div id="updateOccuperModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal('updateOccuperModal')">&times;</span>
        <h2>Modifier une Occupation</h2>
        <form id="updateOccuperForm" onsubmit="event.preventDefault(); updateOccuper();">
            <input type="hidden" id="updateId" name="updateId">
            
            <label for="optupdateCodeprof">Code Prof:</label>
            <select id="optupdateCodeprof" name="optupdateCodeprof" required></select>
            
            <label for="optupdateCodesal">Code Salle:</label>
            <select id="optupdateCodesal" name="optupdateCodesal" required></select>
            
            <label for="updateDate">Date:</label>
            <input type="date" id="updateDate" name="updateDate" required>
            
            <button type="submit">Mettre à jour</button>
        </form>
    </div>
</div>

    <!-- JavaScript -->
    <script src="js/script.js"></script>

  </body>
</html>