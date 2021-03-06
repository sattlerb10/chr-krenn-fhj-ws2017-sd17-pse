package org.se.lab.data;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.se.lab.data.PostDAOImpl;

public class PostDAOTest extends AbstractDAOTest {

	private Community community = new Community("testPost", "test community");
	private User user = new User("testuserpost", "*****");
	private Post post = new Post(null, community, user, "Happy Path Test", new Date(180L));

	private PostDAOImpl dao = new PostDAOImpl();
	
	@Before
	public void setupPost() {
		dao.setEntityManager(em);
	}

	@Test
	public void testPersistPost() {
		em.persist(user);
		em.persist(community);
		em.persist(post);
	}

	@Test
	@Override
	public void testCreate() {
		em.persist(user);
		em.persist(community);
		Assert.assertNotNull(dao.insert(post));
		Assert.assertNotNull(dao.insert(post, community));
	}

	@Test
	@Override
	public void testModify() {

		em.persist(user);
		em.persist(community);

		Post persisted = dao.insert(post, community);
		List<Post> posts = dao.getPostsForUser(user);
		List<Post> postscom = dao.getPostsForCommunity(community);
		Assert.assertTrue(posts.contains(persisted));
		Assert.assertTrue(postscom.contains(persisted));
		Assert.assertTrue(persisted.getText().equals(post.getText()));

		persisted.setText("Modified");
		dao.update(persisted);
		Assert.assertTrue(persisted.getText().equals("Modified"));
	}

	@Test
	@Override
	public void testRemove() {

		em.persist(user);
		em.persist(community);

		dao.delete(post);
		Assert.assertTrue(!dao.getPostsForUser(user).contains(post));
	}

}
