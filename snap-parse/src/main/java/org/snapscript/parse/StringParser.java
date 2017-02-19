/*
 * StringParser.java December 2016
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

package org.snapscript.parse;

public abstract class StringParser {

   protected char[] source;
   protected int off;
   protected int count;

   protected StringParser(){
      this.source = new char[0];
   }

   public void parse(String text){
      if(text != null){
         ensureCapacity(text.length());
         count = text.length();
         text.getChars(0, count, source,0);
         init();
         parse();
      }
   }

   public void parse(char[] text){
      parse(text, 0, text.length);
   }

   public void parse(char[] text, int off, int len){
      if(text != null){
         ensureCapacity(len);
         count = len;
         System.arraycopy(text, off, source, 0, len);
         init();
         parse();
      }
   }

   protected void ensureCapacity(int min) {
      if(source.length < min) {
         int size = source.length * 2;
         int max = Math.max(min, size);
         char[] temp = new char[max];
         source = temp;
      }
   }

   protected boolean space(char c) {
      switch(c){
      case ' ': case '\t':
      case '\n': case '\r':
         return true;
      default:
         return false;
      }
   }

   protected boolean quote(char c) {
      return c == '"' || c == '\'';
   }

   protected boolean digit(char c){
      return c <= '9' && '0' <= c;
   }

   protected char toLower(char c) {
      if(c >= 'A' && c <= 'Z') {
         return (char)((c - 'A') + 'a');
      }
      return c;
   }

   protected boolean skip(String text){
      int size = text.length();
      int read = 0;

      if(off + size > count){
         return false;
      }
      while(read < size){
         char a = text.charAt(read);
         char b = source[off + read++];

         if(toLower(a) != toLower(b)){
            return false;
         }
      }
      off += size;
      return true;
   }
   
   protected boolean seek(String text){
      int seek = 0;      

      while(seek + off < count) {
         char next = source[off + seek];

         if(!space(next)){
            break;
         }
         seek++;
      }
      int size = text.length();
      int read = 0;      

      if(off + size > count){
         return false;
      }
      while(read < size){
         char a = text.charAt(read);
         char b = source[off + seek + read++];

         if(toLower(a) != toLower(b)){
            return false;
         }
      }
      off += size + seek;
      return true;
   }

   protected abstract void init();
   protected abstract void parse();
}
