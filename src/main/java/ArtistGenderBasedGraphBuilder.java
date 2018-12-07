import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class ArtistGenderBasedGraphBuilder {

    private static final String path = "data/artistsWithGender.csv";
    private CSVUtils util = new CSVUtils();
    private HashSet<Artist> artists = new HashSet<Artist>();

    public DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> build() {
        DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(
                DefaultWeightedEdge.class);
        try {
            Scanner reader = new Scanner(new File(path));

            while (reader.hasNext()) {
                List<String> line = util.parseLine(reader.nextLine());
                Artist artist1 = new Artist(line.get(0), line.get(1));
                Artist artist2 = new Artist(line.get(2), line.get(3));
                artists.add(artist1);
                artists.add(artist2);

                graph.addVertex(artist1.getName());
                graph.addVertex(artist2.getName());
                DefaultWeightedEdge edge = graph.addEdge(artist1.getName(), artist2.getName());
                graph.setEdgeWeight(edge, Integer.parseInt(line.get(4)));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public HashSet<Artist> getArtists() {
        return artists;
    }
}


