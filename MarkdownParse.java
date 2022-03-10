// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Stack;

class LinkVisitor extends AbstractVisitor {
	ArrayList<String> links = new ArrayList<>();

	@Override
	public void visit(Link link) {
		links.add(link.getDestination());
	}
}


public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
		Parser parser = Parser.builder().build();
		Node node = parser.parse(markdown);
		LinkVisitor visitor = new LinkVisitor();
		node.accept(visitor);
		return visitor.links;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        System.out.println(getLinks(contents));
		/*
		Iterator hmIterator = links.entrySet().iterator();
		while(hmIterator.hasNext()) {
			Map.Entry mapElement = (Map.Entry)hmIterator.next();
			List<String> linksForFile = (List<String>)mapElement.getValue();
			System.out.println(linksForFile);
		}
		*/
    }
}
