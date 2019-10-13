# Graphql-Java Example

Use [GraphQL playground](https://github.com/prisma-labs/graphql-playground/releases) for graphql fun time.

# Examples

These are probably enough examples. If not, git gud.

## Mutation
```graphql
mutation {
  createBook(title: "Livre", pageCount: 4, author: "Ryan SUE") {
    pageCount    
    id
    title
    author
  }
} 
```

### Output
```json
{
  "data": {
    "createBook": {
      "pageCount": 4,
      "id": "5da37e6fce21c925507f6c5e",
      "title": "Livre",
      "author": "Ryan SUE"
    }
  }
}
```
## Query
```graphql
query {
  books {
    id
    title
    pageCount
    author
  }
}
```

### Output
```json
{
  "data": {
    "books": [
      {
        "id": "5da371dace21c92b4c00dea9",
        "title": "Test Book",
        "pageCount": 3,
        "author": "Ryan SUE"
      },
      {
        "id": "5da371f8ce21c9252ce1735b",
        "title": "Test Book",
        "pageCount": 3,
        "author": "Ryan SUE"
      },
      {
        "id": "5da37e6fce21c925507f6c5e",
        "title": "Livre",
        "pageCount": 4,
        "author": "Ryan SUE"
      }
    ]
  }
}
```