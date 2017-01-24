package org.snapscript.parse;

public interface SyntaxChecker extends SyntaxReader {
   void commit(int start, int grammar);
   int reset(int start, int grammar); 
   int mark(int grammar); 
   int position(); 
   int peek();
   
   // do not check in
   void finish();
}
