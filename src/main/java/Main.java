import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import rental.DataFetchers;
import rental.Mutations;
import utils.FileUtils;
import utils.JsonUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final String SCHEMA_PATH = "src/main/resources/graphql/schema/rental.graphqls";
    private static final String QUERY_PATH  = "/graphql/query/rental/sample_query.graphql";

    public static void main(String[] args) {
        GraphQL graphQL = setupGraphQL();
        System.out.println("GraphQL server is running!");
        executeSampleQuery(graphQL);
    }

    private static GraphQL setupGraphQL() {
        TypeDefinitionRegistry typeRegistry = parseSchema(SCHEMA_PATH);
        RuntimeWiring wiring = buildRuntimeWiring();
        return GraphQL.newGraphQL(new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring)).build();
    }

    private static TypeDefinitionRegistry parseSchema(String schemaPath) {
        try {
            String schema = new String(Files.readAllBytes(Paths.get(schemaPath)));
            return new SchemaParser().parse(schema);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read schema file", e);
        }
    }

    private static RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("items", DataFetchers.getAllItemsFetcher())
                        .dataFetcher("user", DataFetchers.getUserByIdFetcher())
                )
                .type("Mutation", builder -> builder
                        .dataFetcher("rentItem", Mutations.rentItemFetcher())
                        .dataFetcher("returnItem", Mutations.returnItemFetcher())
                )
                .build();
    }

    private static void executeSampleQuery(GraphQL graphQL) {
        String query = FileUtils.readFileContent(QUERY_PATH);
        ExecutionResult executionResult = graphQL.execute(query);
        System.out.println(JsonUtils.serializeToJson(executionResult.toSpecification()));
    }
}
