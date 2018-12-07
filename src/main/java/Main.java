import org.jgrapht.Graph;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        GraphBuilder graphBuilder = new GraphBuilder();
        Graph g = graphBuilder.build();

        // 1ª pergunta
        System.out.println("1 - Quais os artistas mais tocados no mundo?\n");
        System.out.println(getArtists(g, true));

        //2ª pergunta
        System.out.println("\n2 - Quais os artistas mais isolados no mundo? (Os que estão em menos listas das mais tocadas)\n");
        System.out.println(getArtists(g, false));


        ArtistGenderBasedGraphBuilder graphArtists = new ArtistGenderBasedGraphBuilder();
        Graph g2 = graphArtists.build();

        //3ª pergunta
        System.out.println("\n3 - Dado um gênero, qual o artista mais tocado do mundo com esse gênero?\n");
        System.out.println(getMostPlayedByGender(g2,"classic rock", graphArtists.getArtists()));

        //4ª pergunta
        System.out.println("\n4 - Com o objetivo de fazer um grande festival musical com 10 artistas, quais os artistas com maior" +
                " afinidade (maior semelhança em países que aparecem em top playlists)?\n");
        System.out.println(getArtistsByAfinity(g));
    }


    private static String getArtists(Graph g, Boolean mostPlayed) {
        ArrayList<String> artists = new ArrayList<String>();
        ArrayList<Integer> weights = new ArrayList<Integer>();

        for (Object vertex : g.vertexSet()) {
            int totalEdgesWeight = 0;
            for (Object edge : g.edgesOf(vertex)) {
                totalEdgesWeight += g.getEdgeWeight(edge);
            }
            artists.add(vertex.toString());
            weights.add(totalEdgesWeight);
        }

        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < 3; i++) {
            int value = mostPlayed ? Collections.max(weights) : Collections.min(weights);

            int index = weights.indexOf(value);
            result.add(artists.get(weights.indexOf(value)));
            weights.remove(index);
            artists.remove(index);

        }

        return String.format("1: %s\n2: %s\n3: %s", result.get(0), result.get(1), result.get(2));
    }


    private static String getMostPlayedByGender(Graph g, String gender, HashSet<Artist> entities) {
        ArrayList<String> artists = new ArrayList<String>();
        ArrayList<Integer> weights = new ArrayList<Integer>();

        for (Object vertex : g.vertexSet()) {
            int totalEdgesWeight = 0;
            for (Object edge : g.edgesOf(vertex)) {
                totalEdgesWeight += g.getEdgeWeight(edge);
            }
            if (getEntity(vertex.toString(), entities).getGender().equalsIgnoreCase(gender)) {
                artists.add(vertex.toString());
                weights.add(totalEdgesWeight);
            }
        }
        if(artists.isEmpty()){
            return "Nenhum artista foi encontrado com o gênero especificado.";
        }

        int value = Collections.max(weights);
        return artists.get((weights.indexOf(value)));

    }

    private static Artist getEntity(String name, HashSet<Artist> artists) {
        for (Artist a : artists) {
            if (name.equals(a.getName())) return a;
        }
        return  null;
    }


    private static String getArtistsByAfinity(Graph g){
        ArrayList<Object> edges = new ArrayList<Object>(Arrays.asList(g.edgeSet().toArray()));
        ArrayList<Integer> weights = new ArrayList<Integer>();
        for (Object edge: edges) {
            weights.add((int)g.getEdgeWeight(edge));
        }
        ArrayList<String> artistsWithHighAfinity = new ArrayList<String>();
        while(artistsWithHighAfinity.size() != 10 ){
            int value = Collections.max(weights);
            int index = weights.indexOf(value);
            if(!artistsWithHighAfinity.contains(g.getEdgeSource(edges.get(weights.indexOf(value))).toString()))
            artistsWithHighAfinity.add(g.getEdgeSource(edges.get(weights.indexOf(value))).toString());
            if(!artistsWithHighAfinity.contains(g.getEdgeTarget(edges.get(weights.indexOf(value))).toString()))
            artistsWithHighAfinity.add(g.getEdgeTarget(edges.get(weights.indexOf(value))).toString());
            weights.remove(index);
            edges.remove(index);

        }

        String result = Arrays.toString(artistsWithHighAfinity.toArray());
       return result.substring(1, result.length() - 1 );
    }
}