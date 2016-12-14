package org.snapscript.core.address;

import java.lang.management.ManagementFactory;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import junit.framework.TestCase;

import org.snapscript.core.Value;
import org.snapscript.core.ValueType;

import com.sun.management.ThreadMXBean;

// compare hash containers to address table
public class StackStateTest extends TestCase {

   private static final ThreadMXBean THREAD_BEAN = (ThreadMXBean) ManagementFactory.getThreadMXBean();
   private static final int ITERATIONS = 100000000;
   private static final int ELEMENTS = 10;
   
   public void testThreadStack() throws Exception {
      final AddressTable table = new AddressTable(2);
      final AddressState stack = new AddressState(table, 0);
      final Map<String, Object> map = new HashMap<String, Object>();
      final Random random = new SecureRandom();
      final String[] names = new String[ELEMENTS];
      final Address[] addresses = new Address[ELEMENTS];
      
      for(int i = 0; i < ELEMENTS; i++) {
         int number = random.nextInt(ELEMENTS);
         String key = String.valueOf(number);
         Value value = ValueType.getReference(key);
         names[i] = key;
         map.put(key, value);
         stack.add(key, value);  
      }
      for(int i = 0; i < ELEMENTS; i++){
         String name = names[i];
         Address address = stack.address(name);
         Value value = stack.get(address);
         assertEquals(name, value.getValue()); // make sure it works
      }
      for(int n = 0; n < 3; n++) {
         timeIt(new Runnable() {
            @Override
            public void run(){
               Map<String, Object> local = map;
               for(int i = 0; i < ITERATIONS; i++){
                  String key = names[i%ELEMENTS];
                  local.get(key);
               }
            } 
         }, "HashMap READ      ");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               AddressState local = stack;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  Address address = addresses[index];
                  if(address == null){
                     address = addresses[index]=local.address(name);
                  }
                  local.get(address);
               }
            }
         }, "StackState READ   ");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               Map<String, Object> local = map;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  if(index ==0){
                     local = new HashMap<String, Object>();
                  }
                  Value value = ValueType.getReference(name);
                  String key = names[index];
                  local.put(key, value);
               }
            }
         }, "HashMap WRITE 1   ");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               State2 local = stack;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  if(index ==0){
                     stack.clear(); // copy
                  }
                  Value value = ValueType.getReference(name);
                  String key = names[index];
                  local.add(key, value);
               }
            }
         }, "StackState WRITE 1");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               Map<String, Object> local = map;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  if(index ==0){
                     local = new HashMap<String, Object>();
                  }
                  Value value = ValueType.getReference(name);
                  String key = names[index];
                  local.put(key, value);
               }
            }
         }, "HashMap WRITE 2   ");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               State2 local = stack;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  if(index ==0){
                     stack.clear(); 
                  }
                  Value value = ValueType.getReference(name);
                  String key = names[index];
                  local.add(key, value);
               }
            }
         }, "StackState WRITE 2");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               Map<String, Object> local = map;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  Value value = (Value)local.get(name);
                  value.setValue(name);
               }
            }
         }, "HashMap UPDATE    ");
         
         timeIt(new Runnable() {
            @Override
            public void run(){
               State2 local = stack;
               for(int i = 0; i < ITERATIONS; i++){
                  int index = i%ELEMENTS;
                  String name = names[index];
                  Address address = addresses[index];
                  if(address == null){
                     address = addresses[index]=local.address(name);
                  }
                  Value value = local.get(address);
                  value.setValue(name);
               }
            }
         }, "StackState UPDATE ");
      }
   }
   
   public static void timeIt(Runnable run, String message) {
      System.gc();
      System.gc();
      try{
         Thread.sleep(100);
      }catch(Exception e){
         e.printStackTrace();
      }
      Thread thread = Thread.currentThread();
      long id = thread.getId();
      long before = THREAD_BEAN.getThreadAllocatedBytes(id);
      long start = System.currentTimeMillis();
      run.run();
      long finish = System.currentTimeMillis();
      long after = THREAD_BEAN.getThreadAllocatedBytes(id);
      System.err.println(message+" : "+(finish-start)+ " ms");
      System.err.println(message+" : "+(after-before)+ " bytes");
   }
}
