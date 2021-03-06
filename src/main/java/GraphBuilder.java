import java.io.IOException;
import java.util.*;
import java.io.File;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class GraphBuilder {
    private static final String path = "data/artists.csv";
    private CSVUtils util = new CSVUtils();

    public DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> build() {
        DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);
        try {
            Scanner reader = new Scanner(new File(path));

            while (reader.hasNext()) {
                List<String> line = util.parseLine(reader.nextLine());

                graph.addVertex(line.get(0));
                graph.addVertex(line.get(1));
                DefaultWeightedEdge edge = graph.addEdge(line.get(0), line.get(1));
                graph.setEdgeWeight(edge, Integer.parseInt(line.get(2)));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }


}