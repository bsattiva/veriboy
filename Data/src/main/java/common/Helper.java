package common;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 *
 * @author bsati
 */
public class Helper {
    private static final String HOME = System.getProperty("user.dir");
    private static final String SEP = File.separator;

    public static boolean isThing(String s)
    {
        boolean result = false;
        if(s!=null)
        {
            if(!s.isEmpty())
            {
                result = true;
            }
        }
        return result;
    }

    public static String getStringFromProperties(String file, String key){
        Properties properties = new Properties();
        String result = "";
        try {
            properties.load(new FileInputStream(file));
            result = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static String getInsrtyQuery(String view,String section,String element,String content){

        return "insert into content.content values('" + view + "','" + section + "','" + element + "','" + content + "')";
    }
    public static String getUpdateQuery(String view,String section,String element,String content){

        return "update content.content set content='" + content + "' where view='" + view +
                "' and section='" + section +
                "' and element='" + element +"'";
    }

    public static String getStringFromProperties(String file, String key, String def){
        Properties properties = new Properties();
        String result = def;
        try {
            properties.load(new FileInputStream(file));
            result = properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String escapeData(String s){
        String result = "";
        char[] ch = s.toCharArray();

        for(char cha: ch){
            if(cha == "'".charAt(0)){
                result = result + "\\";
            }
            result = result + cha;
        }

        return result;
    }

    public static String concat(String[] t, String del)
    {
        String result = "";

        for(String s:t)
        {
            result = result + s + del;
        }

        result = result.substring(0,result.length()-del.length());

        return result;
    }

    public static String concatEqual(String[] t1, String[] t2, String del)
    {
        String result = "";
        String q = "'";
        try {
            String eq = "=";

            for (int i = 0; i < t1.length; i++) {
                result = result + t1[i] + eq + q +t2[i] +q+ del;

            }
            result = result.substring(0, result.length() - del.length());
        } catch (Exception e) {
            result = null;
        }
        return result;

    }

    public static String getFromPattern(String reg, String body)
    {
        String result = "";

        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(body);

        try {
            while (matcher.find()) {
                int n = matcher.groupCount();
                result = matcher.group(1);
            }
        } catch (Exception e) {
        }

        return result;
    }

    public static String getFromList(List<String[]> l, String key){
        Optional<String[]> s = l.stream().filter(temp -> (temp[0].equals(key))).findFirst();
        return s.get()[1];
    }

    public static String getHomeDir(String[] tree){
        StringBuilder builder = new StringBuilder(HOME);
        Arrays.asList(tree).forEach(folder -> builder.append(SEP + folder));
        return builder.toString();
    }

    public static String encrypt(String text, String sec) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            Key key = new SecretKeySpec(messageDigest.digest(sec.getBytes(StandardCharsets.UTF_8)), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] encoded = Base64.getEncoder().encode(encrypted);
            return new String(encoded, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Unable to encrypt", e);
        }
    }

    public static String decrypt(String text, String sec) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            Key key = new SecretKeySpec(messageDigest.digest(sec.getBytes(StandardCharsets.UTF_8)), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(text.getBytes(StandardCharsets.UTF_8));
            byte[] decrypted = cipher.doFinal(decoded);
            return new String(decrypted, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Unable to decrypt", e);
        }
    }

}
