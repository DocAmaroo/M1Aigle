package base;

import java.io.Serializable;

public class Species implements Serializable {
    private String species;
    public Species(String species) { this.species = species; }
    public String getSpecies() { return this.species; }
    public void setSpecies(String species){
        this.species = species;
    }
}
