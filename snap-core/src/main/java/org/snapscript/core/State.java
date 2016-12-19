package org.snapscript.core;


// This should be on the ThreadStack
// new ObjectInstance(state2....) ... pass the stack in to the object
//
//  Scope getInner() --> does this need to be a Scope???
//    1) if this is a stack, it can be created on the ObjectInstance only!!!... which has a reference to the stack
//
//
//
//  Scope getOuter() --> this expands the stack... if we have a State do we need it?
//
// interface Scope {
//
//    State getStack(); // this is inner.... this is controlled by the TraceStatement and TraceEvaluation....
//    State getObject(); // this is outer
// }
//
//
//  interface Scope {
//    State getInner();
//    Scope getObject() {
//        
//    }
//  }
//
//
//
/*
 * 
 * In the Address.getSource() we need to consider multiple sources. For example it could be the stack and a type
 * so we need to know how to delegate in to a compound scope
 * 
 * 
 * CompositeState needs to have this work done to it!!
 * 
 */
public interface State extends Iterable<String> {
   boolean contains(String name);
   Address address(String name);
   Value get(Address address);
   Value get(String name);
   //void add(Address address, Value value);
   Address add(String name, Value value); // this should return an Address so that we avoid scanning multiple times
   void set(Address address, Value value); // this should return an Address so that we avoid scanning multiple times
}