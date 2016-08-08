package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;

public class InitializerCollector extends Initializer {
   
   private final List<Initializer> list;
   
   public InitializerCollector(){
      this.list = new ArrayList<Initializer>();
   }

   public void update(Initializer initializer) throws Exception {
      if(initializer != null) {         
         list.add(initializer);
      }
   }
   
   @Override
   public Result compile(Scope scope, Type type) throws Exception {
      Result last = null;
      
      for(Initializer initializer : list) {
         Result result = initializer.compile(scope, type);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      if(last == null) {
         return ResultType.getNormal();
      }
      return last;
   } 

   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      Result last = null;

      for(Initializer initializer : list) {
         Result result = initializer.execute(scope, type);
         
         if(!result.isNormal()){
            return result;
         }
         last = result;
      }
      if(last == null) {
         return ResultType.getNormal();
      }
      return last;
   }              
}
