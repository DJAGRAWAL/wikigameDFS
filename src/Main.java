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
//concat
//contains***
//compareto
//endsWith
//equalsIgnoreCase
//indexOf
//


public class Main  {
    public static void main(String[] args) {
        String start = "https://en.wikipedia.org/wiki/National_Football_League";
        search = "https://en.wikipedia.org/wiki/Green_Bay_Packers";
        HtmlRead(start,0);
        if(path.containsKey(search)) {
            String s = search;
            ArrayList<String> result = new ArrayList<>();
            while(s!=start) {
                result.add(s);
                s = path.get(s);
            }
            result.add(start);
            Collections.reverse(result);
            for(String s2: result) System.out.println(s2);
        }
        else {
            System.out.println("NO PATH FOUND");
        }
    }
    public static HashMap<String, String> path = new HashMap<>(); // curlink to past link
    public static int MAXDEPTH = 3;
    public static String search;

    public static void HtmlRead(String link, int depth) {
        if(depth>=MAXDEPTH) return;
        try {
            URL url = new URL(link);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line, link, depth);
            }
        }
        catch(Exception e) {

        }

    }
    public static boolean run = true;
    public static void parseLine(String line, String pastlink, int depth) {
        if(!run) return;
        String[] starters = new String[]{"href='","href=\"", "src='", "src=\""};
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
                    path.put(link, pastlink);
                    run = false;
                    return;
                }
//                Depth First:
                if(!path.containsKey(link)) {
//                    System.out.println(link);
                    path.put(link, pastlink);
                    HtmlRead(link, depth+1);
                }
//                if(bottom.getText().contains(link)) {
//                    bottom.append(link + "\n\n");
//                    list.get(index).add(link);
//                }
                if(end==x.length) return;
                line = line.substring(end+1);
                parseLine(line, pastlink, depth);
                break;
            }

        }

    }


}