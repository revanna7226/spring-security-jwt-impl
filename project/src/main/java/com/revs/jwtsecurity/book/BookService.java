package com.revs.jwtsecurity.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;


    public void save(BookRequest bookRequest) {
        var book = Book.builder()
                .author(bookRequest.getAuthor())
                .isbn(bookRequest.getIsbn())
                .build();
        bookRepository.save(book);
    }
}
