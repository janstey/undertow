package io.undertow.attribute;

import io.undertow.server.HttpServerExchange;

/**
 * @author Stuart Douglas
 */
public class SubstituteEmptyWrapper implements ExchangeAttributeWrapper {

    private final String substitute;

    public SubstituteEmptyWrapper(String substitute) {
        this.substitute = substitute;
    }

    @Override
    public ExchangeAttribute wrap(final ExchangeAttribute attribute) {
        return new SubstituteEmptyAttribute(attribute, substitute);
    }

    public static class SubstituteEmptyAttribute implements ExchangeAttribute {
        private final ExchangeAttribute attribute;
        private final String substitute;

        public SubstituteEmptyAttribute(ExchangeAttribute attribute, String substitute) {
            this.attribute = attribute;
            this.substitute = substitute;
        }

        @Override
        public String readAttribute(HttpServerExchange exchange) {
            String val = attribute.readAttribute(exchange);
            if(val == null || val.isEmpty()) {
                return substitute;
            }
            return val;
        }

        @Override
        public void writeAttribute(HttpServerExchange exchange, String newValue) throws ReadOnlyAttributeException {
            attribute.writeAttribute(exchange, newValue);
        }
    }
}
