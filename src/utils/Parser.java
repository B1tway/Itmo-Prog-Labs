package utils;

import сollection.*;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * The type Parser.
 */
public class Parser {
    /**
     * The String tokenizer.
     */
    StringTokenizer stringTokenizer;
    // { name ; x , y ; health ; category; weaponType; meleeWeapon; name, world }

    /**
     * Parse space marine space marine.
     *
     * @param tokens the tokens
     * @return the space marine
     * @throws ParseException the parse exception
     */
    public SpaceMarine parseSpaceMarine(String[] tokens) throws ParseException {
        SpaceMarine marine = null;
        try {
            int id = Integer.parseInt(tokens[0].trim());
            String name = tokens[1].trim();
            Coordinates coordinates = parseCoordinates(tokens[2].trim());
            ZonedDateTime date = ZonedDateTime.parse(tokens[3].trim());
            float health = Float.parseFloat(tokens[4].trim());
            AstartesCategory category = tokens[5].equals("null") ? null : AstartesCategory.valueOf(tokens[5].trim());
            Weapon weapon = Weapon.valueOf(tokens[6].trim());
            MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(tokens[7].trim());
            Chapter chapter = parseChapter(tokens[8].trim());
            marine = new SpaceMarine(name, coordinates, health, category, weapon, meleeWeapon, chapter);
            marine.setCreationDate(date);
            marine.setId(id);
        } finally {
            return marine;
        }


    }

    /**
     * Sets string tokenizer.
     *
     * @param stringTokenizer the string tokenizer
     */
    public void setStringTokenizer(StringTokenizer stringTokenizer) {
        this.stringTokenizer = stringTokenizer;
    }


    /**
     * Parse coordinates coordinates.
     *
     * @param str the str
     * @return the coordinates
     */
    public Coordinates parseCoordinates(String str) {
        str.trim();
        String[] tokens = str.split(":");
        long x = Long.parseLong(tokens[0].trim());
        Long y = Long.parseLong(tokens[1].trim());
        return new Coordinates(x, y);

    }

    /**
     * Parse chapter chapter.
     *
     * @param str the str
     * @return the chapter
     */
    public Chapter parseChapter(String str) {
        str.trim();
        String[] tokens = str.split(":");
        return new Chapter(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(), tokens[3].trim().equals("null") ? null : Integer.parseInt(tokens[3].trim()));
    }

    /**
     * Get tokens string [ ].
     *
     * @param string the string
     * @return the string [ ]
     */
    public String[] getTokens(String string) {
        ArrayList<String> tokens = new ArrayList<>();
        setStringTokenizer(new StringTokenizer(string));
        boolean isToken = false;
        String current = "";
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            if (token.equals("{")) {
                isToken = true;
                continue;
            }
            if (token.equals("}")) break;
            if (isToken && token.equals(";")) {
                tokens.add(current);
                current = "";
                continue;
            }
            if (isToken) current += " " + token;


        }
        return (String[]) tokens.toArray();
    }

}
