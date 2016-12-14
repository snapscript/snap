package org.snapscript.core.address;

import java.util.Iterator;

import junit.framework.TestCase;

import org.snapscript.core.ValueType;

public class AddressTableTest extends TestCase {
   
   public void testAddressTable() {
      AddressTable table = new AddressTable(null);
      
      table.add("a", ValueType.getReference("A"));
      table.add("b", ValueType.getReference("B"));
      table.add("c", ValueType.getReference("C"));
      
      assertEquals(table.address("a").getIndex(), 0);
      assertEquals(table.address("b").getIndex(), 1);
      assertEquals(table.address("c").getIndex(), 2);
      
      long position = table.mark();
      
      assertEquals(table.address("a").getIndex(), -1);
      assertEquals(table.address("b").getIndex(), -1);
      assertEquals(table.address("c").getIndex(), -1);
      
      table.add("d", ValueType.getReference("D"));
      table.add("e", ValueType.getReference("E"));
      table.add("f", ValueType.getReference("F"));
      
      assertEquals(table.address("d").getIndex(), 0);
      assertEquals(table.address("e").getIndex(), 1);
      assertEquals(table.address("f").getIndex(), 2);
      
      Iterator<String> names = table.iterator();
      
      assertEquals(names.next(), "d");
      assertEquals(names.next(), "e");
      assertEquals(names.next(), "f");
      
      table.reset(position);
      
      assertEquals(table.address("e").getIndex(), -1);
      assertEquals(table.address("f").getIndex(), -1);
      assertEquals(table.address("g").getIndex(), -1);
      
      assertEquals(table.address("a").getIndex(), 0);
      assertEquals(table.address("b").getIndex(), 1);
      assertEquals(table.address("c").getIndex(), 2);
      
      names = table.iterator();
      
      assertEquals(names.next(), "a");
      assertEquals(names.next(), "b");
      assertEquals(names.next(), "c");
   }

}
