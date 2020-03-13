package utils;

import сollection.*;

import java.lang.reflect.Array;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Parser {
    StringTokenizer stringTokenizer;
    // { name ; x , y ; health ; category; weaponType; meleeWeapon; name, world }

    public SpaceMarine parseSpaceMarine(String[] tokens) throws ParseException {
        SpaceMarine marine = null;
        try {
            int id = Integer.parseInt(tokens[0].strip());
            String name = tokens[1].strip();
            Coordinates coordinates = parseCoordinates(tokens[2].strip());
            Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = (Date) formatter.parseObject(tokens[3].strip());
            float health = Float.parseFloat(tokens[4].strip());
            AstartesCategory category = AstartesCategory.valueOf(tokens[5].strip());
            Weapon weapon = Weapon.valueOf(tokens[6].strip());
            MeleeWeapon meleeWeapon = MeleeWeapon.valueOf(tokens[7].strip());
            Chapter chapter = parseChapter(tokens[8].strip());
            marine = new SpaceMarine(name, coordinates, health, category, weapon, meleeWeapon, chapter);
            marine.setCreationDate(date);
            marine.setId(id);
        } finally {
            return marine;
        }


    }

    public void setStringTokenizer(StringTokenizer stringTokenizer) {
        this.stringTokenizer = stringTokenizer;
    }


    public Coordinates parseCoordinates(String str) {
        str.strip();
        String[] tokens = str.split(":");
        long x = Long.parseLong(tokens[0].strip());
        float y = Float.parseFloat(tokens[1].strip());
        return new Coordinates(x, y);

    }

    public Chapter parseChapter(String str) {
        str.strip();
        String[] tokens = str.split(":");
        if (tokens[1].equals("null")) return new Chapter(tokens[0].strip(), tokens[2].strip(), Long.parseLong(tokens[3].strip()));
        return new Chapter(tokens[0].strip(), tokens[1].strip(), tokens[2].strip(), Long.parseLong(tokens[3].strip()));
    }

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
