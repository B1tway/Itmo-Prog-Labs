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

//    public SpaceMarine parseSpaceMarine(String str) throws ParseException {
////        String[] tokens = str.split(";");
////        int id = Integer.parseInt(tokens[0].strip());
////        String name = tokens[1].strip();
////        Coordinates coordinates = parseCoordinates(tokens[2]);
////        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        Date date = (Date) formatter.parseObject(tokens[3]);
////
////        float health = Float.parseFloat(tokens[3]);
////        AstartesCategory category = AstartesCategory.valueOf(tokens[4]);
////        Weapon weapon = Weapon.valueOf(tokens[5]);
////
////
////    }

    public void setStringTokenizer(StringTokenizer stringTokenizer) {
        this.stringTokenizer = stringTokenizer;
    }


    public Coordinates parseCoordinates(String str) {
        str.strip();
        String[] tokens = str.split(":");
        long x = Long.parseLong(tokens[0]);
        float y = Float.parseFloat(tokens[1]);
        return new Coordinates(x, y);

    }

    public Chapter parseChapter(String str) {
        str.strip();
        String[] tokens = str.split(":");
        if (tokens[1].equals("null")) return new Chapter(tokens[0], tokens[2], Long.parseLong(tokens[3]));
        return new Chapter(tokens[0], tokens[1], tokens[2], Long.parseLong(tokens[3]));
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
