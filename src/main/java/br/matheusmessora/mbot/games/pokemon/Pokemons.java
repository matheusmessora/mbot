package br.matheusmessora.mbot.games.pokemon;

/**
 * Created by cin_mmessora on 6/5/17.
 */
public enum Pokemons {

    PIKACHU("Pikachu", 0.75, 2),
    CHARMANDER("Charmander", 0.5, 1),
    SQUIRTLE("Squirtle", 0.5, 1),
    BULBASSAUR("Bulbassaur", 0.5, 1),
    SNORLAX("Snorlax", 0.88, 4);

    private final String name;
    private final double chance;
    private final int exp;

    Pokemons(String name, double chance, int exp) {
        this.name = name;
        this.chance = chance;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public double getChance() {
        return chance;
    }

    public int getExp() {
        return exp;
    }
}
