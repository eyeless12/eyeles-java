package edu.java.service;

import edu.java.model.Link;
import java.util.List;

public interface ILinkService {
    List<String> getSupportedDomains();

    boolean isSupported(String domain);

    Link addLink(long id, Link link);

    Link deleteLink(long chatId, Link link);

    List<Link> findAll(long chatId);
}
