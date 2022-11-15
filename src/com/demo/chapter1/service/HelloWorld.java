package com.demo.chapter1.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.demo.chapter1.entity.Todo;
import com.demo.chapter1.utils.HibernateUtil;

/**
 * Session Bean implementation class HelloWorld
 */
@Stateless
@LocalBean
public class HelloWorld implements HelloWorldRemote, HelloWorldLocal {

	/**
	 * Default constructor.
	 */
	public HelloWorld() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Todo> findAll() {
		Transaction transaction = null;
		List<Todo> result = new ArrayList<Todo>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			String hql = "FROM todo";
			transaction = session.beginTransaction();
			Query<Todo> query = session.createQuery(hql);
			result = query.getResultList();
			System.out.println(result);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Todo findById(int id) {
		Transaction transaction = null;
		Todo todo = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			todo = session.get(Todo.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return todo;

	}

	@Override
	public boolean add(Todo todo) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.save(todo);
			// commit transaction
			transaction.commit();

			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean edit(Todo todo) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.update(todo);
			// commit transaction
			transaction.commit();

			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Todo todo) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start a transaction
			transaction = session.beginTransaction();
			// save the student object
			session.delete(todo);
			// commit transaction
			transaction.commit();

			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

}
