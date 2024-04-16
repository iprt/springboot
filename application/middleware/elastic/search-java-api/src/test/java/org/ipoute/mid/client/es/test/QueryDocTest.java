package org.ipoute.mid.client.es.test;

import org.iproute.mid.client.es.document.query.QueryDoc;
import org.iproute.mid.client.es.simplify.EsOperator;
import org.junit.jupiter.api.Test;

/**
 * QueryDocTest
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
public class QueryDocTest {

    @Test
    public void all() {
        EsOperator.operate(QueryDoc.QUERY_ALL);
    }

    @Test
    public void page() {
        EsOperator.operate(QueryDoc.QUERY_PAGE);
    }

    @Test
    public void sort() {
        EsOperator.operate(QueryDoc.QUERY_SORT);
    }

    @Test
    public void conditions() {
        EsOperator.operate(QueryDoc.QUERY_CONDITIONS);
    }

    @Test
    public void range() {
        EsOperator.operate(QueryDoc.QUERY_RANGE);
    }

    @Test
    public void fuzzy() {
        EsOperator.operate(QueryDoc.QUERY_FUZZY);
    }

    @Test
    public void highlight() {
        EsOperator.operate(QueryDoc.QUERY_HIGHLIGHT);
    }

    @Test
    public void max() {
        EsOperator.operate(QueryDoc.QUERY_MAX);
    }

    @Test
    public void group() {
        EsOperator.operate(QueryDoc.QUERY_GROUP);
    }
}
