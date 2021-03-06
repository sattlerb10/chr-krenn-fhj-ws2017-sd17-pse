package org.se.lab.data;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PrivateMessageDAOTest extends AbstractDAOTest
{
	public User user = new User("User1", "test");
	public User user2 = new User("User2", "test");
	public PrivateMessage pm = new PrivateMessage("Hallo Textmessage Test 1", user, user2);
    public PrivateMessage pm2 = new PrivateMessage("Hallo Testmessage Test 2", user2, user);


    public static PrivateMessageDAOImpl pmdao = new PrivateMessageDAOImpl();

    static {
    	pmdao.setEntityManager(em);
    }

    @Test
    @Override
    public void testCreate() {
    	pmdao.insert(pm);
    }

    @Test
    @Override
    public void testRemove() {
    	pmdao.insert(pm);
    	pmdao.delete(pm);
        PrivateMessage pm3 = pmdao.findById(pm.getID());
        Assert.assertNull(pm3);
    }

    @Test
    public void testfindAll() {
    	pmdao.insert(pm);
    	pmdao.insert(pm2);
        List<PrivateMessage> pms = pmdao.findAll();
        Assert.assertEquals(2, pms.size());
    }

    @Test
    public void testfindById() {
    	pmdao.insert(pm);
        PrivateMessage pm3 = pmdao.findById(pm.getID());
        Assert.assertEquals(pm, pm3);
    }

	@Override
	public void testModify() {
		// TODO Auto-generated method stub
	}
}
