const body = document.querySelector("body");
const darkLight = document.querySelector("#darkLight");
const sidebar = document.querySelector(".sidebar");
const submenuItems = document.querySelectorAll(".submenu_item");
const sidebarOpen = document.querySelector("#sidebarOpen");
const sidebarClose = document.querySelector(".collapse_sidebar");
const sidebarExpand = document.querySelector(".expand_sidebar");
sidebarOpen.addEventListener("click", () => sidebar.classList.toggle("close"));
sidebarClose.addEventListener("click", () => {
  sidebar.classList.add("close", "hoverable");
});
sidebarExpand.addEventListener("click", () => {
  sidebar.classList.remove("close", "hoverable");
});
sidebar.addEventListener("mouseenter", () => {
  if (sidebar.classList.contains("hoverable")) {
    sidebar.classList.remove("close");
  }
});
sidebar.addEventListener("mouseleave", () => {
  if (sidebar.classList.contains("hoverable")) {
    sidebar.classList.add("close");
  }
});
darkLight.addEventListener("click", () => {
  body.classList.toggle("dark");
  if (body.classList.contains("dark")) {
    document.setI
    darkLight.classList.replace("bx-sun", "bx-moon");
  } else {
    darkLight.classList.replace("bx-moon", "bx-sun");
  }
});
submenuItems.forEach((item, index) => {
  item.addEventListener("click", () => {
    item.classList.toggle("show_submenu");
    submenuItems.forEach((item2, index2) => {
      if (index !== index2) {
        item2.classList.remove("show_submenu");
      }
    });
  });
});
if (window.innerWidth < 768) {
  sidebar.classList.add("close");
} else {
  sidebar.classList.remove("close");
}

function showTable(tableId) {
    document.querySelectorAll('.table-container').forEach(table => {
        table.classList.add('hidden');
    });
    document.getElementById(tableId).classList.remove('hidden');
}

// Afficher la table des professeurs par défaut au chargement de la page
document.addEventListener('DOMContentLoaded', function() {
    showTable('professeurs');
});


function loadProfAndSalleOptions() {
    // Charger les professeurs
    fetch('/WebApp/profs/options')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erreur HTTP ! Statut : ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Données des professeurs reçues :", data);
            const codeprofSelect = document.getElementById("optcodeprof"); // Nouvel ID
            const updateCodeprofSelect = document.getElementById("optupdateCodeprof"); // Nouvel ID
            codeprofSelect.innerHTML = ""; // Vider les options existantes
            updateCodeprofSelect.innerHTML = ""; // Vider les options existantes

            data.forEach(prof => {
                const option = `<option value="${prof.codeprof}">${prof.codeprof} - ${prof.nom} ${prof.prenom}</option>`;
                codeprofSelect.innerHTML += option;
                updateCodeprofSelect.innerHTML += option;
            });
        })
        .catch(error => console.error('Erreur lors du chargement des professeurs:', error));

    // Charger les salles
    fetch('/WebApp/salles/options')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erreur HTTP ! Statut : ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Données des salles reçues :", data);
            const codesalSelect = document.getElementById("optcodesal"); // Nouvel ID
            const updateCodesalSelect = document.getElementById("optupdateCodesal"); // Nouvel ID
            codesalSelect.innerHTML = ""; // Vider les options existantes
            updateCodesalSelect.innerHTML = ""; // Vider les options existantes

            data.forEach(salle => {
                const option = `<option value="${salle.codesal}">${salle.codesal} - ${salle.designation}</option>`;
                codesalSelect.innerHTML += option;
                updateCodesalSelect.innerHTML += option;
            });
        })
        .catch(error => console.error('Erreur lors du chargement des salles:', error));
}

// Charger les options au démarrage de la page
document.addEventListener('DOMContentLoaded', function () {
    loadProfAndSalleOptions();
});
// Charger les options au démarrage de la page
document.addEventListener('DOMContentLoaded', function () {
    loadProfAndSalleOptions();
});

// Fonction pour ouvrir un modal
function openModal(modalId) {
    document.getElementById(modalId).style.display = "block";
}

// Fonction pour fermer un modal
function closeModal(modalId) {
    document.getElementById(modalId).style.display = "none";
}

// Fonction pour charger les professeurs depuis le backend
function loadProfs() {
    fetch('/WebApp/api/profs')
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("#professeurs tbody");
            tbody.innerHTML = ""; // Vider le tableau avant de le remplir
            data.forEach(prof => {
                const row = `<tr>
                    <td>${prof.codeprof}</td>
                    <td>${prof.nom}</td>
                    <td>${prof.prenom}</td>
                    <td>${prof.grade}</td>
                    <td>
                        <i class="bx bx-edit" onclick="openUpdateProfModal('${prof.codeprof}', '${prof.nom}', '${prof.prenom}', '${prof.grade}')" style="cursor: pointer; margin-right: 10px;"></i>
                        <i class="bx bx-trash" onclick="deleteProf('${prof.codeprof}')" style="cursor: pointer;"></i>
                    </td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error('Erreur lors du chargement des professeurs:', error));
}

// Fonction pour ajouter un professeur
function addProf(codeprof, nom, prenom, grade) {
    fetch('/WebApp/api/profs', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ codeprof, nom, prenom, grade }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadProfs(); // Recharger la liste des professeurs
        closeModal('addProfModal');
    })
    .catch(error => console.error('Erreur lors de l\'ajout du professeur:', error));
}

// Fonction pour mettre à jour un professeur
function updateProf(codeprof, nom, prenom, grade) {
    fetch(`/WebApp/api/profs/${codeprof}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ nom, prenom, grade }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadProfs(); // Recharger la liste des professeurs
        closeModal('updateProfModal');
    })
    .catch(error => console.error('Erreur lors de la mise à jour du professeur:', error));
}

// Fonction pour supprimer un professeur
function deleteProf(codeprof) {
    if (confirm("Êtes-vous sûr de vouloir supprimer ce professeur ?")) {
        fetch(`/WebApp/api/profs/${codeprof}`, {
            method: 'DELETE',
        })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadProfs(); // Recharger la liste des professeurs
        })
        .catch(error => console.error('Erreur lors de la suppression du professeur:', error));
    }
}

// Fonction pour rechercher des professeurs
function searchProfs(keyword) {
    fetch(`/WebApp/api/profs?search=${keyword}`)
        .then(response => response.json())
        .then(data => {
            const tbody = document.querySelector("#professeurs tbody");
            tbody.innerHTML = ""; // Vider le tableau avant de le remplir
            data.forEach(prof => {
                const row = `<tr>
                    <td>${prof.codeprof}</td>
                    <td>${prof.nom}</td>
                    <td>${prof.prenom}</td>
                    <td>${prof.grade}</td>
                    <td>
                        <i class="bx bx-edit" onclick="openUpdateProfModal('${prof.codeprof}', '${prof.nom}', '${prof.prenom}', '${prof.grade}')" style="cursor: pointer; margin-right: 10px;"></i>
                        <i class="bx bx-trash" onclick="deleteProf('${prof.codeprof}')" style="cursor: pointer;"></i>
                    </td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => console.error('Erreur lors de la recherche des professeurs:', error));
}

// Charger les professeurs au démarrage de la page
document.addEventListener('DOMContentLoaded', function () {
    loadProfs();
});


function loadSalles() {
    fetch('/WebApp/api/salles')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erreur HTTP ! Statut : ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.querySelector("#salles tbody");
            tbody.innerHTML = ""; // Vider le tableau avant de le remplir
            data.forEach(salle => {
                const row = `<tr>
                    <td>${salle.codesal}</td>
                    <td>${salle.designation}</td>
                    <td>
                        <i class="bx bx-edit" onclick="openUpdateSalleModal('${salle.codesal}', '${salle.designation}')" style="cursor: pointer; margin-right: 10px;"></i>
                        <i class="bx bx-trash" onclick="deleteSalle('${salle.codesal}')" style="cursor: pointer;"></i>
                    </td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error('Erreur lors du chargement des salles:', error);
            alert('Erreur lors du chargement des salles. Voir la console pour plus de détails.');
        });
}
document.addEventListener('DOMContentLoaded', function () {
        loadSalles();
    });


function addSalle(codesal, designation) {
    fetch('/WebApp/api/salles', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ codesal, designation }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadSalles(); // Recharger la liste des salles
        closeModal('addSalleModal');
    })
    .catch(error => console.error('Erreur lors de l\'ajout de la salle:', error));
}

function updateSalle(codesal, designation) {
    fetch(`/WebApp/api/salles/${codesal}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ designation }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadSalles(); // Recharger la liste des salles
        closeModal('updateSalleModal');
    })
    .catch(error => console.error('Erreur lors de la mise à jour de la salle:', error));
}

function deleteSalle(codesal) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette salle ?")) {
        fetch(`/WebApp/api/salles/${codesal}`, {
            method: 'DELETE',
        })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadSalles(); // Recharger la liste des salles
        })
        .catch(error => console.error('Erreur lors de la suppression de la salle:', error));
    }
}




function loadOccupers() {
    fetch('/WebApp/api/occupers')
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erreur HTTP ! Statut : ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            const tbody = document.querySelector("#occupations tbody");
            tbody.innerHTML = ""; // Vider le tableau avant de le remplir
            data.forEach(occuper => {
                const row = `<tr>
                    <td>${occuper.codeprof}</td>
                    <td>${occuper.codesal}</td>
                    <td>${occuper.date}</td>
                    <td>
                        <i class="bx bx-edit" onclick="openUpdateOccuperModal(${occuper.id}, '${occuper.codeprof}', '${occuper.codesal}', '${occuper.date}')" style="cursor: pointer; margin-right: 10px;"></i>
                        <i class="bx bx-trash" onclick="deleteOccuper(${occuper.id})" style="cursor: pointer;"></i>
                    </td>
                </tr>`;
                tbody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error('Erreur lors du chargement des occupations:', error);
            alert('Erreur lors du chargement des occupations. Voir la console pour plus de détails.');
        });
}
 document.addEventListener('DOMContentLoaded', function () {
        loadOccupers();
    });

//function addOccuper(codeprof, codesal, date) {
//    fetch('/WebApp/api/occupers', {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json',
//        },
//        body: JSON.stringify({ codeprof, codesal, date }),
//    })
//    .then(response => response.json())
//    .then(data => {
//        alert(data.message);
//        loadOccupers(); // Recharger la liste des occupations
//        closeModal('addOccuperModal');
//    })
//    .catch(error => console.error('Erreur lors de l\'ajout de l\'occupation:', error));
//}
//
//function updateOccuper(id, codeprof, codesal, date) {
//    fetch(`/WebApp/api/occupers/${id}`, {
//        method: 'PUT',
//        headers: {
//            'Content-Type': 'application/json',
//        },
//        body: JSON.stringify({ codeprof, codesal, date }),
//    })
//    .then(response => response.json())
//    .then(data => {
//        alert(data.message);
//        loadOccupers(); // Recharger la liste des occupations
//        closeModal('updateOccuperModal');
//    })
//    .catch(error => console.error('Erreur lors de la mise à jour de l\'occupation:', error));
//}


function addOccuper() {
    const codeprof = document.getElementById("optcodeprof").value;
    const codesal = document.getElementById("optcodesal").value;
    const date = document.getElementById("date").value;

    fetch('/WebApp/api/occupers', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ codeprof, codesal, date }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadOccupers(); // Recharger la liste des occupations
        closeModal('addOccuperModal');
    })
    .catch(error => console.error('Erreur lors de l\'ajout de l\'occupation:', error));
}

function updateOccuper() {
    const id = document.getElementById("updateId").value;
    const codeprof = document.getElementById("optupdateCodeprof").value;
    const codesal = document.getElementById("optupdateCodesal").value;
    const date = document.getElementById("updateDate").value;

    fetch(`/WebApp/api/occupers/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({id, codeprof, codesal, date }),
    })
    .then(response => response.json())
    .then(data => {
        alert(data.message);
        loadOccupers(); // Recharger la liste des occupations
        closeModal('updateOccuperModal');
    })
    .catch(error => console.error('Erreur lors de la mise à jour de l\'occupation:', error));
}
function deleteOccuper(id) {
    if (confirm("Êtes-vous sûr de vouloir supprimer cette occupation ?")) {
        fetch(`/WebApp/api/occupers/${id}`, {
            method: 'DELETE',
        })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadOccupers(); // Recharger la liste des occupations
        })
        .catch(error => console.error('Erreur lors de la suppression de l\'occupation:', error));
    }
}


function openUpdateProfModal(codeprof, nom, prenom, grade) {
    document.getElementById("updateCodeprof").value = codeprof;
    document.getElementById("updateNom").value = nom;
    document.getElementById("updatePrenom").value = prenom;
    document.getElementById("updateGrade").value = grade;
    openModal('updateProfModal');
}


function openUpdateSalleModal(codesal, designation) {
    document.getElementById("updateCodesal").value = codesal;
    document.getElementById("updateDesignation").value = designation;
    openModal('updateSalleModal');
}

function openUpdateOccuperModal(id, codeprof, codesal, date) {
    document.getElementById("updateId").value = id;
    document.getElementById("optupdateCodeprof").value = codeprof;
    document.getElementById("optupdateCodesal").value = codesal;
    document.getElementById("updateDate").value = date;
    openModal('updateOccuperModal');
}



