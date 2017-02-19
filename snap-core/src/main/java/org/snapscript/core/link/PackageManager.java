/*
 * PackageManager.java December 2016
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

package org.snapscript.core.link;

import static org.snapscript.core.link.ImportType.EXPLICIT;
import static org.snapscript.core.link.ImportType.IMPLICIT;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.NameBuilder;
import org.snapscript.core.TypeNameBuilder;
  
public class PackageManager {
   
   private final ImportScanner scanner;
   private final PackageLoader loader;
   private final NameBuilder builder;
   
   public PackageManager(PackageLoader loader, ImportScanner scanner) {
      this.builder = new TypeNameBuilder();;
      this.scanner = scanner;
      this.loader = loader;
   }
   
   public Package importPackage(String module) {
      Object result = scanner.importPackage(module);
      
      if(result == null) {
         try {
            return loader.load(IMPLICIT, module); // import some.package.*
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String module, String name) {
      String type  = builder.createFullName(module, name);            
      Object result = scanner.importType(type);
      
      if(result == null) {
         String outer  = builder.createTopName(module, name); 
         
         try {
            return loader.load(EXPLICIT, outer, module); // import some.package.Blah
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + module + "." + name + "'", e);
         }
      }
      return new NoPackage();
   }
   
   public Package importType(String type) {           
      Object result = scanner.importType(type);
      
      if(result == null) {
         String outer  = builder.createTopName(type); 
         
         try {
            return loader.load(EXPLICIT, outer); 
         } catch(Exception e){
            throw new InternalStateException("Problem importing '" + type + "'", e);
         }
      }
      return new NoPackage();
   }
}
