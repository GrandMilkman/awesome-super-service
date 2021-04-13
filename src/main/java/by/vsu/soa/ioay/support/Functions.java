package by.vsu.soa.ioay.support;

import java.util.Iterator;

import by.vsu.soa.ioay.entity.Entity;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Functions {

    private static ExpressionParser parser = new SpelExpressionParser(new SpelParserConfiguration(true, true));

    public static String join(final Iterable<?> it, final String SpEL, final String delimeter) {
        Expression expression = parser.parseExpression(SpEL);

        StringBuilder sb = new StringBuilder();

        Iterator<?> iterator = it.iterator();

        if (iterator.hasNext()) {
            sb.append(expression.getValue(iterator.next()));
            while (iterator.hasNext()) {
                sb.append(delimeter).append(expression.getValue(iterator.next()));
            }
        }

        return sb.toString();
    }

    public static boolean contains(final Entity entity, final Iterable<Entity> list) {
        if (entity != null && list != null) {
            for (Entity e : list) {
                if (e.getId().equals(entity.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

} // class


