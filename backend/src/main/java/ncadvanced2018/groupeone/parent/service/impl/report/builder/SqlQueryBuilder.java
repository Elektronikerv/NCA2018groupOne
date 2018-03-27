package ncadvanced2018.groupeone.parent.service.impl.report.builder;
import static java.lang.String.format;

import java.util.Arrays;
import java.util.stream.Collectors;

public class SqlQueryBuilder {

    private final StringBuilder builder;

    public SqlQueryBuilder() {
        this.builder = new StringBuilder();
    }

    public SqlQueryBuilder where() {
        builder.append(" WHERE ");
        return this;
    }

    public SqlQueryBuilder and() {
        builder.append(" AND ");
        return this;
    }

    public SqlQueryBuilder or() {
        builder.append(" OR ");
        return this;
    }

    public SqlQueryBuilder in(String field, String... values) {
        String formattedValues = Arrays.stream(values).collect(Collectors.joining(", "));
        builder.append(format(" %s IN (%s) ", field, formattedValues));
        return this;
    }

    public SqlQueryBuilder isNull(String field) {
        builder.append(format(" %s IS NULL ", field));
        return this;
    }

    public SqlQueryBuilder notNull(String field) {
        builder.append(format(" %s IS NOT NULL ", field));
        return this;
    }

    public SqlQueryBuilder equal(String field, String value) {
        builder.append(format("%s = '%s'", field, value));
        return this;
    }

    public SqlQueryBuilder addField(String field) {
        builder.append(field);
        return this;
    }

    public SqlQueryBuilder like(String field, String value) {
        builder.append(format("lower(%s) LIKE lower('%%%s%%')", field, value));
        return this;
    }

    public SqlQueryBuilder orderBy() {
        builder.append(" ORDER BY ");
        return this;
    }

    public SqlQueryBuilder asc() {
        builder.append(" ASC ");
        return this;
    }

    public SqlQueryBuilder desc() {
        builder.append(" DESC ");
        return this;
    }

    public String build() {
        return builder.toString();
    }
}
