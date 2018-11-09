import java.util.Objects;

public class Artist {
    private String name;
    private String relatedTo;

    public Artist(String name, String related) {
        this.name = name;
        this.relatedTo = related;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(getName(), artist.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
