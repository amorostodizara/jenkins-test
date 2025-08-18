package net.example.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name = "salle")
public class SalleModel {

    @Id
    @Column(name = "codesal", nullable = false, length = 50) // codesal est désormais un String
    private String codesal;

    @Column(name = "designation", nullable = false, length = 50)
    private String designation;

    // Constructeurs
    public SalleModel() {} // Constructeur par défaut (requis par JPA)

    public SalleModel(String codesal, String designation) {
        this.codesal = codesal;
        this.designation = designation;
    }

    // Getters et Setters
    public String getCodesal() {
        return codesal;
    }

    public void setCodesal(String codesal) {
        this.codesal = codesal;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    // Méthode toString() pour afficher les informations de l'objet
    @Override
    public String toString() {
        return "SalleModel{" +
                "codesal='" + codesal + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}