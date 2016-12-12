
1) We need to change how the scope works it should look like this

interface Scope {
   Type getType();
   
   
   State2 getStack(); // this is taken from ThreadStack.get();, passed in to ObjectInstance on creation!!
   {
   		this stack is a constante for the thread, it may only ever need
   		to be references from ThreadStack when there is a creation/allocation
   }
   
   
   
   State getState(); // this represents the state of the object, but delegates to the stack if not found
   { 
   		if(object.contains(name){
   			return object.get(name);
   		}
   		return stack.get(name);
   
   }
   
   Scope getObject(); // is this even needed? probably is if we cross between object1 to object2 e.g object1.object2 ??
   Module getModule();   
   Model getModel();
}