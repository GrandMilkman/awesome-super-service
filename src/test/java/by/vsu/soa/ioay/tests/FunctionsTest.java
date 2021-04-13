package by.vsu.soa.ioay.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import by.vsu.soa.ioay.support.Functions;
import by.vsu.soa.ioay.entity.Entity;
import by.vsu.soa.ioay.entity.Group;

public class FunctionsTest {

    @Test
    public void join1() {
        List<Group> list=new ArrayList<Group>();
        list.add(new Group("group1"));
        assertTrue("group1".equals(Functions.join(list, "name", ", ")));
    }

    @Test
    public void join2() {
        List<Group> list=new ArrayList<Group>();
        assertTrue("".equals(Functions.join(list, "name", ", ")));
    }

    @Test
    public void join3() {
        List<Group> list=new ArrayList<Group>();
        list.add(new Group("group1"));
        list.add(new Group("group2"));
        assertTrue("group1, group2".equals(Functions.join(list, "name", ", ")));
    }

    @Test
    public void contains1() {
        List<Entity> list=new ArrayList<Entity>();
        Group g1=new Group("group1");
        Group g2=new Group("group2");
        Group g3=new Group("group3");
        g1.setId(1L);
        g2.setId(2L);
        g3.setId(3L);
        list.add(g1);
        list.add(g2);
        assertFalse(Functions.contains(g3, list));
    }

    @Test
    public void contains2() {
        List<Entity> list=new ArrayList<Entity>();
        Group g1=new Group("group1");
        Group g2=new Group("group2");
        Group g3=new Group("group3");
        g1.setId(1L);
        g2.setId(2L);
        g3.setId(1L);
        list.add(g1);
        list.add(g2);
        assertTrue(Functions.contains(g3, list));
    }

    @Test
    public void contains3() {
        List<Entity> list=new ArrayList<Entity>();
        Group g1=new Group("group1");
        Group g2=new Group("group2");
        Group g3=null;
        g1.setId(1L);
        g2.setId(2L);
        list.add(g1);
        list.add(g2);
        assertFalse(Functions.contains(g3, list));
    }

    @Test
    public void contains4() {
        List<Entity> list=null;
        Group g3=new Group("group3");
        g3.setId(3L);
        assertFalse(Functions.contains(g3, list));
    }
}
