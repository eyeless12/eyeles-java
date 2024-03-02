package edu.java.model;

import java.util.List;

public record Chat(long id, List<Link> links) {
    public void addLink(Link link) {
        links.add(link);
    }
}
