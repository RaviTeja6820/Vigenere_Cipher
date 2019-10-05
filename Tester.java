
/**
 * Write a description of Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
public class Tester {
public void testCeaserCipher(){
    FileResource fr = new FileResource();
    CaesarCipher cc = new CaesarCipher(1);
    System.out.println(cc.encrypt(fr.asString()));
    System.out.println(cc.decrypt(cc.encrypt(fr.asString())));
}

public void testCaesarCracker(){
    FileResource fr = new FileResource();
    CaesarCracker cc = new CaesarCracker();
    System.out.println(cc.decrypt(fr.asString()));
}

public void testVigenereCipher(){
    FileResource fr = new FileResource();
    int[] arr = {17,14,12,4};
    VigenereCipher vc = new VigenereCipher(arr);
    System.out.println(vc.encrypt(fr.asString()));
    System.out.println(vc.decrypt(vc.encrypt(fr.asString())));
}

public void testmostcommon(){
    FileResource fr = new FileResource();
    HashSet<String> dic = new HashSet<String>();
    for(String s:fr.asString().split("\\W")){
        dic.add(s);
    }
    VigenereBreaker vb = new VigenereBreaker();
    System.out.println(vb.mostCommonCharIn(dic));
}

public void testVigenereBreaker(){
    VigenereBreaker vb  = new VigenereBreaker();
    vb.breakVigenere();
}

}
