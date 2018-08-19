package org.snapscript.core.type.index;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.snapscript.core.MockModule;
import org.snapscript.core.MockType;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.EmptyModel;
import org.snapscript.core.scope.Model;
import org.snapscript.core.scope.ModelScope;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class SignatureGeneratorTest extends TestCase {   
   
   public static class GenericFunc<T> {
   
      public <A, B> List<B> fun2(A a, T t) {
         return Collections.EMPTY_LIST;
      }
      
      public <A> List<A> fun1(A a, T t) {
         return Collections.EMPTY_LIST;
      }
   }
   
   public void testSignature() throws Exception {
      Module module = new MockModule(); 
      Model model = new EmptyModel();
      Scope scope = new ModelScope(model, module);
      Type type = new MockType(module, "GenericFunc", null, null);
      SignatureGenerator generator = new SignatureGenerator();
      Method method1 = GenericFunc.class.getDeclaredMethod("fun1", Object.class, Object.class);
      Signature signature1 = generator.generate(type, method1);
      
      assertEquals(signature1.getGenerics().size(), 1);
      assertEquals(signature1.getGenerics().get(0).getName(scope), "A");
      
      Method method2 = GenericFunc.class.getDeclaredMethod("fun2", Object.class, Object.class);
      Signature signature2 = generator.generate(type, method2);
      
      assertEquals(signature2.getGenerics().size(), 2);
      assertEquals(signature2.getGenerics().get(0).getName(scope), "A");
      assertEquals(signature2.getGenerics().get(1).getName(scope), "B");
      
   }

}
