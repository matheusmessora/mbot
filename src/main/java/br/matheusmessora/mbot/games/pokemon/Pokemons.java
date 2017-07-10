package br.matheusmessora.mbot.games.pokemon;

/**
 * Created by cin_mmessora on 6/5/17.
 */
public enum Pokemons {

    PIKACHU("Pikachu", 0.75, 5, "<:pikachu:331940546966716417>"),
    CHARMANDER("Charmander", 0.57, 3, "<:charmander:331936878183972878>"),
    SQUIRTLE("Squirtle", 0.54, 3, "<:squirtle:331939418002948096>"),
    BULBASSAUR("Bulbassaur", 0.56, 3, "<:bulba:331939251052740609>"),
    Ratata("Ratata", 0.10, 2, "<:ratata:331939092885667841>"),
    Megazord("Snorlax da Bahia", 0.90, 13, "<:megazord:331832648110702593>"),
    SNORLAX("Snorlax", 0.88, 10, "<:snorlax:331938139209728030>"),
    ;

    private final String name;
    private final double chance;
    private final int exp;
    private final String code;


    Pokemons(String name, double chance, int exp, String code) {
        this.name = name;
        this.chance = chance;
        this.exp = exp;
        this.code = code;
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

    public String getCode() {
        return code;
    }
}
