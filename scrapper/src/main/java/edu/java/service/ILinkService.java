package edu.java.service;

import edu.java.model.Link;
import java.util.Collection;

public interface ILinkService {
    Link add(String url, long chatId);

    Link remove(String url, long chatId);

    Collection<Link> listAll(long chatId);
}
