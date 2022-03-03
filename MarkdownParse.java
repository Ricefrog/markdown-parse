// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Stack;

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        
        int currentIndex = 0;
        Stack<Character> bracketTracker = new Stack<>(); 
        boolean findLink = false;
        int start = 0;
        int end = 0;
        while(currentIndex < markdown.length()) {
            char curr = markdown.charAt(currentIndex);
            //if an escape char is found, skip it and the 
            //character it is escaping
            if (curr == '\\') {
                currentIndex += 2;
                continue;
            }
            //if we are potentially looking at a link with []
            if (findLink) {
                // if there arent any other brackets on the bracket tracker
                if (bracketTracker.isEmpty()) {
                    if (curr == '(') {
                        bracketTracker.push(curr);
                        start = currentIndex;
                    } else { //something else came after the ] that wasn't (
                        findLink = false;
                    }
                } else {
                    if (curr == ')') {
                        end = currentIndex;
						String link = markdown.substring(start+1, end).trim();
						if (!(
							link.contains(" ") ||
							link.contains("\t") ||
							link.contains("\n"))
						) {
                        	toReturn.add(markdown.substring(start + 1, end));
						}
                        bracketTracker.pop();
                        findLink = false;
                    }
                }
            } else {
                if (curr == '[') {
                    bracketTracker.push(curr);
                } else if (curr == ']') {
                    if (!bracketTracker.isEmpty()) {
                        bracketTracker.clear();
                        findLink = true;
                    }
                } else if (curr == '!') {
                    if (currentIndex < markdown.length() - 1 && markdown.charAt(currentIndex + 1) == '[') {
                        currentIndex += 2;
                    }
                }
            }
            // move to next char
            currentIndex++;
        }

        return toReturn;

    }
    public static Map<String, List<String>> getLinks(File dirOrFile) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        if(dirOrFile.isDirectory()) {
            for(File f: dirOrFile.listFiles()) {
                result.putAll(getLinks(f));
            }
            return result;
        }
        else {
            Path p = dirOrFile.toPath();
            int lastDot = p.toString().lastIndexOf(".");
            if(lastDot == -1 || !p.toString().substring(lastDot).equals(".md")) {
                return result;
            }
            ArrayList<String> links = getLinks(Files.readString(p));
            result.put(dirOrFile.getPath(), links);
            return result;
        }
    }
    public static void main(String[] args) throws IOException {
		//Path fileName = Path.of(args[0]);
	    //String contents = Files.readString(fileName);
        Map<String, List<String>> links = getLinks(new File(args[0]));
		Iterator hmIterator = links.entrySet().iterator();
		while(hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry)hmIterator.next();
			List<String> linksForFile = (List<String>)mapElement.getValue();
			System.out.println(linksForFile);
		}
    }
}
