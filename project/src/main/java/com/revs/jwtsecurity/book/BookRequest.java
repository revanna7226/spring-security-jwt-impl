package com.revs.jwtsecurity.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookRequest {
    private int id;
    private String author;
    private String isbn;
}
