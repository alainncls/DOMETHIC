package fr.epf.medfile.daos.ressource;

import java.util.List;

import fr.epf.medfile.models.Ressource;

public interface RessourceService {
    long addRessource(int id, int idPatient, int idAuthor, String title, String type, String category, String text, String img, String date);

    List<Ressource> getRessources();
    List<Ressource> getRessources(int idPatient);
    List<String> getCategories(int idPatient);
    List<Ressource> getRessources(int idPatient, String category);
    Ressource getRessource(int id);
    boolean removeRessource(int id);
    boolean clearRessources();
    boolean isEmpty();
}
