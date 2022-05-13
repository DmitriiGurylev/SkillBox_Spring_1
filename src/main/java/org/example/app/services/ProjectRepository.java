package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean removeItemById(int bookIdToRemove);

    boolean removeItemsByAuthor(String bookAuthorToRemove);

    boolean removeItemsByTitle(String bookTitleToRemove);

    boolean removeItemsBySize(Integer bookSizeToRemove);

    List<T> filterItemsByAuthor(String bookAuthorToFilter);

    List<T> filterItemsByTitle(String bookTitleToFilter);

    List<T> filterItemsBySize(Integer bookSizeToFilter);
}
