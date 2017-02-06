package org.snapscript.compile;

import junit.framework.TestCase;
import org.snapscript.core.Context;
import org.snapscript.core.ExpressionEvaluator;
import org.snapscript.core.Model;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

import java.util.HashMap;
import java.util.Map;

public class SprintContextTest extends TestCase {

    private static interface ApplicationContext {
        <T> T getBean(String name);
    }

    private static class MapApplicationContext implements ApplicationContext {

        private final Map<String, Object> beans;

        public MapApplicationContext(Map<String, Object> beans) {
            this.beans = beans;
        }

        @Override
        public <T> T getBean(String name) {
            return (T)beans.get(name);
        }
    }

    private static class ApplicationContextModel implements Model {

        private final ApplicationContext context;

        public ApplicationContextModel(ApplicationContext context) {
            this.context = context;
        }

        public Object getAttribute(String name) {
            return context.getBean(name);
        }
    }

    private static class Foo {

        public String callFoo(Bar bar, int value) {
            return "bar=" + bar + " value="+value;
        }
    }

    private static class BarFactory {

        public Bar createBar(String name, int value) {
            return new Bar(name, value);
        }
    }

    private static class Bar {

        private final String name;
        private final int value;

        public Bar(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName(){
            return name;
        }

        public int getValue(){
            return value;
        }

        @Override
        public String toString(){
            return String.format("(%s, %s)", name, value);
        }
    }

    private static ApplicationContext createMockApplicationContext(){
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("foo", new Foo());
        beans.put("barFactory", new BarFactory());
        beans.put("x", 22); // put in a simple variable

        return new MapApplicationContext(beans);
    }

    public static Model createModelAdapter(ApplicationContext context) {
        return new ApplicationContextModel(context);
    }

    public static ExpressionEvaluator createEvaluator() {
        Store store = new ClassPathStore();
        Context context = new StoreContext(store);
        return context.getEvaluator();
    }

    public void testContextModel() throws Exception {
        ApplicationContext context = createMockApplicationContext();
        ExpressionEvaluator evaluator = createEvaluator();
        Model model = createModelAdapter(context); // provide an adapter to the Spring context

        assertEquals("bar=(blah, 33) value=44", evaluator.evaluate(model, "foo.callFoo(barFactory.createBar('blah', 11 + x), 44f)"));
        assertEquals("bar=(xx, 3456) value=-94", evaluator.evaluate(model, "foo.callFoo(barFactory.createBar('xx', 3456), -94)"));
    }
}
