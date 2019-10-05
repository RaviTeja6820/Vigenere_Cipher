import java.util.*;
import edu.duke.*;
import java.io.*;
public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder();
        int index=whichSlice;
        while(true){
            if(index>=message.length())
            break;
            char c = message.charAt(index);
            sb.append(c);
            index = index + totalSlices;
            
        }
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        int i=0;
        while(i<klength){
            key[i] = cc.getKey(sliceString(encrypted,i,klength));
            i++;
        }
        return key;
    }
    
    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> words = new HashSet<String>();
        for(String s:fr.lines()){
            words.add(s.toLowerCase());
        }
        return words;
    }
    
    public int countWords(String message,HashSet<String> dictionary){
        int count=0;
        for(String str:message.split("\\W")){
            if(dictionary.contains(str.toLowerCase())){
                count++;
            }
        }
        return count;
    }
    
    public String breakForLaunguage(String encrypted,HashSet<String> dictionary){
        int[] count = new int[100];
        int max=0,maxi=-1;
        String[] decrypte = new String[100];
        char mostCommon = mostCommonCharIn(dictionary);
        for(int keyLength=1;keyLength<=100;keyLength++){
            int[] key = new int[keyLength];
            key = tryKeyLength(encrypted,keyLength,mostCommon);
            VigenereCipher vc  = new VigenereCipher(key);
            String decrypted = vc.decrypt(encrypted);
            decrypte[keyLength-1]=decrypted;
            count[keyLength-1] = countWords(decrypted,dictionary);
            if(max<count[keyLength-1]){
                max=count[keyLength-1];
                maxi=keyLength-1;
            }
        }
        System.out.println("Key;length"+(maxi+1));
        return decrypte[maxi];
    }
    
    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int k=0; k < vals.length; k++){
            if (vals[k] > vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }
    
    public char mostCommonCharIn(HashSet<String> dictionary){
            String alph = "abcdefghijklmnopqrstuvwxyz";
            int[] counts = new int[26];
        for(String str:dictionary){
        for(int k=0; k < str.length(); k++){
            int dex = alph.indexOf(Character.toLowerCase(str.charAt(k)));
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        }
        return alph.charAt(maxIndex(counts));
    }
    
    public String breakForAllLangs(String encrypted,HashMap<String,HashSet<String>> launguages){
        HashMap<String,Integer> count = new HashMap<String,Integer>();
        int max=0;String maxstr=" ",laun=" ";
        for(String launguage:launguages.keySet()){
            String str = breakForLaunguage(encrypted,launguages.get(launguage));
            int counts = countWords(str,launguages.get(launguage));
            count.put(launguage,counts);
            if(max<counts){
                max=counts;
                maxstr = str;
                laun=launguage;
            }
        }
        System.out.println(laun);
        return maxstr;
    }
        
            
    public void breakVigenere () {
        HashMap<String,HashSet<String>> dictionary = new HashMap<String,HashSet<String>>(); 
        DirectoryResource dr = new DirectoryResource();
        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            HashSet<String> dic = readDictionary(fr);
            dictionary.put(f.getName(),dic);
        }
        FileResource fr2 = new FileResource();
        String str = breakForAllLangs(fr2.asString(),dictionary);int count=0;
        for(String s:str.split("\\W")){
            count++;
            if(count<=10){
                System.out.println(s);
            }
        }
        System.out.println(count);
        System.out.println(str);
    }
    
}
