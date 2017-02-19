/*
 * TypeNameBuilder.java December 2016
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

package org.snapscript.core;

public class TypeNameBuilder implements NameBuilder {
   
   private static final String[] DIMENSIONS = {"", "[]", "[][]", "[][][]" };     
   private static final String DIMENSION = "[]";
   
   public TypeNameBuilder(){
      super();
   }
   
   @Override
   public String createFullName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createFullName(entry) + DIMENSION;
      }
      return type.getName();
   }
   
   @Override
   public String createShortName(Class type) {
      Class entry = type.getComponentType();
      
      if(entry != null) {
         return createShortName(entry) + DIMENSION;
      }
      String name = type.getName();
      int index = name.lastIndexOf('.');
      int length = name.length();
      
      if(index > 0) {
         return name.substring(index+1, length);
      }
      return name;
   }
   
   @Override
   public String createFullName(String module, String name) {
      if(module == null) { // is a null module legal?
         return name;
      }
      if(name == null) {
         return module;
      }
      return module + "." + name;
   }
   
   @Override
   public String createArrayName(String module, String name, int size) {
      int limit = DIMENSIONS.length;
      
      if(size >= DIMENSIONS.length) {
         throw new InternalArgumentException("Maximum of " + limit + " dimensions exceeded");
      }
      String bounds = DIMENSIONS[size];
      
      if(module != null) { // is a null module legal?
         return createFullName(module, name) + bounds;
      }
      return name + bounds;
   }
   
   @Override
   public String createOuterName(String module, String name) {
      if(name != null) {
         int index = name.lastIndexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return createFullName(module, parent);
            }
         }
      }
      return null;
   }
   
   @Override
   public String createTopName(String type) {
      if(type != null) {
         int index = type.indexOf('$');
         
         if(index > 0) {
            return type.substring(0, index);
         }
         return type;
      }
      return null;
   }
   
   @Override
   public String createTopName(String module, String name) {
      if(name != null) {
         int index = name.indexOf('$');
         
         if(index > 0) {
            String parent = name.substring(0, index);
            int length = parent.length();
            
            if(length > 0) {
               return createFullName(module, parent);
            }
         }
         return createFullName(module, name);
      }
      return null;
   }
}
