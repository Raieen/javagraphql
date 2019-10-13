package xyz.raieen.javagraphql;

import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * This is basically where the magic happens... where the data is fetched. Isn't that obvious?
 */
@Component
public class GraphQLDataFetchers {

    @Autowired
    BookRepository bookRepository;
//    final Logger logger = LoggerFactory.getLogger(GraphQLDataFetchers.class);

    public DataFetcher bookByIdDataFetcher() {
        return env -> {
            String bookId = env.getArgument("id");
            return bookRepository.findById(bookId).orElse(null); // Finds book with id and returns it, or null.
        };
    }

    public DataFetcher bookByTitleDataFetcher() {
        return env -> {
            String bookTitle = env.getArgument("title");
            return bookRepository.findByTitle(bookTitle);
        };
    }

    public DataFetcher greetDataFetcher() {
        return env -> String.format("Hello %s!", (String) env.getArgument("name"));
    }

    public DataFetcher booksDataFetcher() {
        return env -> bookRepository.books();
    }

    public DataFetcher createBookDataFetcher() {
        System.out.println("AJSDAJLNSDLJAASDADDDD");
        return env -> {
            String title = env.getArgument("title");
            int pageCount = env.getArgument("pageCount");
            String author = env.getArgument("author");
            Book book = new Book(title, author, pageCount);
            System.out.println("bookRepository.insert(book) = " + bookRepository.insert(book));
//            bookRepository.insert(book);
            return book;
        };
    }

    public DataFetcher updateBookDataFetcher() {
        return env -> {
            Book book = bookRepository.findById(env.getArgument("id")).orElse(null);
            if (env.containsArgument("title")) {
                book.setTitle(env.getArgument("title"));
            }
            if (env.containsArgument("pageCount")) {
                book.setPageCount(env.getArgument("pageCount"));
            }
            if (env.containsArgument("author")) {
                book.setAuthor(env.getArgument("author"));
            }
            return bookRepository.save(book);
        };
    }

    public DataFetcher deleteBookDataFetcher() {
        return env -> "This has been left as an exercise for the reader c:";
    }
}
