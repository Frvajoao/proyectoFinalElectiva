package services;

public class Apriori {

    private final long id;
    private final String content;
    private final String name;

    public Apriori(long id, String name, String content) {
        this.id = id;
        this.content = content;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
    
    public String getName() {
        return name;
    }
    
    
}
