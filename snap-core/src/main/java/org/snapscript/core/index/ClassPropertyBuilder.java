/*
 * ClassPropertyBuilder.java December 2016
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

package org.snapscript.core.index;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Type;
import org.snapscript.core.property.ClassProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.property.ThisProperty;

public class ClassPropertyBuilder {

   private final TypeIndexer indexer;
   
   public ClassPropertyBuilder(TypeIndexer indexer){
      this.indexer = indexer;
   }

   public List<Property> create(Class source) throws Exception {
      Type type = indexer.loadType(source);
      Type constraint = indexer.loadType(Type.class);
      
      if(type == null) {
         throw new InternalStateException("Could not load type for " + source);
      }
      List<Property> properties = new ArrayList<Property>();
      Property thisProperty = new ThisProperty(type);
      Property classProperty = new ClassProperty(type, constraint);
      
      properties.add(thisProperty);
      properties.add(classProperty);
      
      return properties;        
   }
}
