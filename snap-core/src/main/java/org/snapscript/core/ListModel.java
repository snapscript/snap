package org.snapscript.core;

import java.util.List;

import org.snapscript.core.Model;

public class ListModel implements Model {
   
   private final List<Model> models;
   
   public ListModel(List<Model> models) {
      this.models = models;
   }

   @Override
   public Object getAttribute(String name) {
      for(Model model : models) {
         Object value = model.getAttribute(name);
         
         if(value != null) {
            return value;
         }
      }
      return null;
   }

}
