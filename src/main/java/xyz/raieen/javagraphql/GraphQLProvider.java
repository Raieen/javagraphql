package xyz.raieen.javagraphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

@Component
public class GraphQLProvider {

    @Autowired
    GraphQLDataFetchers graphQLDataFetchers;
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphql");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    // https://www.graphql-java.com/images/graphql_creation.png
    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl); // Parsed version of the schema
        RuntimeWiring runtimeWiring = buildWiring(); // Registers the data fetchers
        // Combines RuntimeWiring and TypeDefinitionRegistry to make the schema.
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring() {
        // Query
        Map<String, DataFetcher> queryDataFetchers = new HashMap<>();
        queryDataFetchers.put("bookById", graphQLDataFetchers.bookByIdDataFetcher());
        queryDataFetchers.put("bookByTitle", graphQLDataFetchers.bookByTitleDataFetcher());
        queryDataFetchers.put("greet", graphQLDataFetchers.greetDataFetcher());
        queryDataFetchers.put("books", graphQLDataFetchers.booksDataFetcher());

        // Mutation
        Map<String, DataFetcher> mutationDataFetchers = new HashMap<>();
        queryDataFetchers.put("createBook", graphQLDataFetchers.createBookDataFetcher());
        queryDataFetchers.put("updateBook", graphQLDataFetchers.updateBookDataFetcher());
        queryDataFetchers.put("deleteBook", graphQLDataFetchers.deleteBookDataFetcher());

        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query").dataFetchers(queryDataFetchers))
                .type(newTypeWiring("Mutation") // When doing .dataFetchers(map) it breaks... why?
                        .dataFetcher("createBook", graphQLDataFetchers.createBookDataFetcher())
                        .dataFetcher("updateBook", graphQLDataFetchers.updateBookDataFetcher())
                        .dataFetcher("deleteBook", graphQLDataFetchers.deleteBookDataFetcher()))

                .build();
    }
}