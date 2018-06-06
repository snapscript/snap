package org.snapscript.tree;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Execution;
import org.snapscript.core.NameFormatter;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.type.Type;

public class ImportStatic implements Compilation {   
   
   private final Qualifier qualifier;    
   
   public ImportStatic(Qualifier qualifier) {
      this.qualifier = qualifier;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      return new CompileResult(location, target, name);
   }
   
   private static class CompileResult extends Statement {
      
      private final StaticImportMatcher matcher;
      private final NameFormatter formatter;  
      private final Execution execution;    
      private final String location;
      private final String target;
      private final String prefix;
      
      public CompileResult(String location, String target, String prefix) {
         this.execution = new NoExecution(NORMAL);
         this.matcher = new StaticImportMatcher();
         this.formatter = new NameFormatter();        
         this.location = location;
         this.target = target;
         this.prefix = prefix;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();         
         String parent = formatter.formatFullName(location, target);
         Type type = module.getType(parent); // this is a type name
         
         if(type == null) {
            throw new InternalStateException("Could not import '" + parent + "'");
         }
         List<Function> list = module.getFunctions();
         List<Function> functions = matcher.matchFunctions(type, prefix);
         List<Property> properties = matcher.matchProperties(type, prefix);
         Scope outer = module.getScope(); // make sure to use module scope
         State state = outer.getState(); 
         
         for(Property property : properties) {
            String name = property.getName();
            Object value = property.getValue(null); // its static
            Constraint constraint = property.getConstraint();
            Local local = Local.getConstant(value, name, constraint);

            try {
               state.add(name, local);
            }catch(Exception e) {
               throw new InternalStateException("Import of static property '" + name +"' failed", e);
            }  
         }
         list.addAll(functions);     
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
      
   }
}