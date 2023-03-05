import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
//concat
//contains***
//compareto
//endsWith
//equalsIgnoreCase
//indexOf
//


public class Main  {
    public static boolean two = false;
    public static void main(String[] args) {
        String start = "https://en.wikipedia.org/wiki/Chad";
        String search = "https://en.wikipedia.org/wiki/Trist%C3%A3o_Vaz_Teixeira";
        HtmlRead(start,0, search);
        if(!path.containsKey(search)) {
            two = true;
            HtmlRead(search,0, start);
            for(String s: path.keySet()) {
                if(path2.values().contains(path.get(s))) {
                    String cur = path.get(s);
                    ArrayList<String> result = new ArrayList<>();
                    while (!cur.equals(start)) {
                        result.add(cur);
                        cur = path.get(cur);
                    }
                    result.add(cur);
                    Collections.reverse(result);
                    cur = path2.get(path.get(s));

                    while (!cur.equals(start)) {
                        result.add(cur);
                        cur = path2.get(cur);
                    }
                    for(String s2: result) System.out.println(s2);
                    break;
                }
            }
        }
        else {
            ArrayList<String> result = new ArrayList<String>();
            String cur = search;
            while (!cur.equals(start)) {
                result.add(cur);
                cur = path.get(cur);
            }
            result.add(cur);
            Collections.reverse(result);
            for(String s: result) System.out.println(s);
        }
    }
    public static HashMap<String, String> path = new HashMap<>(); // curlink to past link
    public static HashMap<String, String> path2 = new HashMap<>();
    public static int MAXDEPTH = 3;

    public static void HtmlRead(String link, int depth, String search) {
        if(depth>=MAXDEPTH) return;
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line, link, depth, search);
            }
        }
        catch(Exception e) {

        }

    }
    public static boolean run = true;
    public static void parseLine(String line, String pastlink, int depth, String search) {
        if(!run) return;
        String[] starters = new String[]{"href='","href=\""};
        for(String s: starters) {
            if (line.contains(s)) {
//                System.out.println(line);
                int start = line.indexOf(s);
                char[] x = line.toCharArray();
                int end = x.length;
                int plus = s.length();
//                if(x[start+plus]=='/' || x[start+plus]=='#') continue;
                for(int i = start+plus; i<x.length; i++) {
                    if(x[i]=='\"' || x[i]=='\'' || x[i]==' ') {
                        end = i;
                        break;
                    }
                }
                String link = line.substring(start + plus, end);
                if(link.indexOf("/wiki/")!=0) return;
                link = "https://en.wikipedia.org" + link;
                if(link.equals(search)) {
//                    System.out.println("FOUND");
                    if(two) path2.put(link, pastlink);
                    else path.put(link, pastlink);
                    run = false;
                    return;
                }
//                Depth First:
                if(!path.containsKey(link)) {
//                    System.out.println(link);
                    if(two) path2.put(link, pastlink);
                    else path.put(link, pastlink);
                    HtmlRead(link, depth+1, search);
                }
//                if(bottom.getText().contains(link)) {
//                    bottom.append(link + "\n\n");
//                    list.get(index).add(link);
//                }
                if(end==x.length) return;
                line = line.substring(end+1);
                parseLine(line, pastlink, depth, search);
                break;
            }

        }

    }


}