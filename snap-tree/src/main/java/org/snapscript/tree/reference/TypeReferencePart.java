/*
 * TypeReferencePart.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.NameExtractor;

public class TypeReferencePart implements Evaluation {

   private final TypeTraverser traverser;
   private final NameExtractor extractor;

   public TypeReferencePart(Evaluation type) {
      this.extractor = new NameExtractor(type);
      this.traverser = new TypeTraverser();
   }   
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Module module = scope.getModule();
      
      if(left != null) {
         String name = extractor.extract(scope);
         
         if(Module.class.isInstance(left)) {
            return create(scope, (Module)left);
         }
         if(Type.class.isInstance(left)) {
            return create(scope, (Type)left);
         }
         throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
      }
      return create(scope, module);
   }
   
   private Value create(Scope scope, Module module) throws Exception {
      String name = extractor.extract(scope);
      Object result = module.getModule(name);
      Type type = scope.getType();
      
      if(result == null) {
         result = module.getType(name); 
      }
      if(result == null && type != null) {
         result = traverser.findEnclosing(type, name);
      }
      if(result == null) {
         throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
      }
      return ValueType.getTransient(result);
   }
   
   
   private Value create(Scope scope, Type type) throws Exception {
      String name = extractor.extract(scope);
      Module module = type.getModule();
      String parent = type.getName();
      Type result = module.getType(parent + "$"+name);
      
      if(result == null) {
         throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
      }
      return ValueType.getTransient(result);
   }
}
