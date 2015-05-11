package com.technos.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

public abstract class GenericDaoImpl<T, I extends Serializable> implements
		GenericDao<T, I> {

	private Conexao conexao;

	@Override
	public T save(T entity) {
		T saved = null;
		getEntityManager().getTransaction().begin();

		saved = getEntityManager().merge(entity);

		getEntityManager().getTransaction().commit();

		return saved;
	}

	@Override
	public void remove(T entity) {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(
				getEntityManager().contains(entity) ? entity
						: getEntityManager().merge(entity));
		getEntityManager().getTransaction().commit();

	}

	@Override
	public T getById(Class<T> classe, I pk) {

		try {
			return getEntityManager().find(classe, pk);
		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByField(String tabela, String campo, String valor) {

		try {
			return getEntityManager().createNativeQuery(
					"select * from " + tabela + " where " + valor)
					.getResultList();

		} catch (NoResultException e) {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<T> classe) {
		try {

			return getEntityManager().createQuery(
					"select o from " + classe.getSimpleName() + " o")
					.getResultList();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllWhere(Class<T> classe, String where) {
		try {

			return getEntityManager().createQuery(
					"select o from " + classe.getSimpleName() + " o WHERE "
							+ where).getResultList();
		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public EntityManager getEntityManager() {

		if (conexao == null) {
			conexao = new Conexao();
		}
		return conexao.getEntityManager();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> OpenQuery(String sql) {
		try {
			final Query query = getEntityManager().createNativeQuery(sql);
			List<Object[]> resultList = query.getResultList();
			return resultList;

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> OpenQuery2(String sql) {
		try {
			final Query query = getEntityManager().createNativeQuery(sql);
			List<T> resultList = query.getResultList();
			return resultList;

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public ResultSet OpenQueryRS(String sql) {
		try {
			return (ResultSet) getEntityManager().createNativeQuery(sql)
					.getResultList();

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public String callProcedure(String procedure, int id) {

		getEntityManager().getTransaction().begin();

		// Using implementation-independent JPA 2.1
		StoredProcedureQuery query = getEntityManager()
				.createStoredProcedureQuery(procedure);

		query.registerStoredProcedureParameter(1, Integer.class,
				ParameterMode.IN);
		query.setParameter(1, id);
		query.execute();
		getEntityManager().getTransaction().commit();

		// query.registerStoredProcedureParameter(
		// "o_output_1", String.class, ParameterMode.OUT);
		// String result = String.valueOf(
		// query.getOutputParameterValue("o_output_1"));

		// String response = (String) query.getOutputParameterValue(1);

		// query.

		return "";

	};

	@Override
	public void executarRegraPorId(int regra, int id) {

	}

	@Override
	public String callProcedure2(Class<T> classe, String procedure) {

		// Query query = session.createSQLQuery(
		// "CALL GetStocks(:stockCode)")
		// .addEntity(classe)
		// .setParameter("stockCode", "7277");
		return "";
	}

	/*
	 * @NamedStoredProcedureQuery( name="GETBALANCE_PROCEDURE",
	 * procedureName="getBalanceProc", returnsResultSet=false, parameters={
	 * 
	 * @StoredProcedureParameter(queryParameter="param1",name="p1",direction=
	 * Direction.IN,type=Integer.class),
	 * 
	 * @StoredProcedureParameter(queryParameter="param2",name="p2",direction=
	 * Direction.IN,type=Timestamp.class),
	 * 
	 * @StoredProcedureParameter(queryParameter="param3",name="p3",direction=
	 * Direction.IN,type=String.class),
	 * 
	 * @StoredProcedureParameter(queryParameter="param4",name="p4",direction=
	 * Direction.OUT,type=Integer.class) } )
	 * 
	 * 
	 * public int executeGetBalanceProcedure(int tranType, Timestamp dateTran,
	 * String oa){ EntityManager em = getEntityManager(); int theBalance = 0;
	 * try { EntityTransaction etx = em.getTransaction(); etx.begin(); Query q =
	 * em.createNamedQuery("GETBALANCE_PROCEDURE"); q.setParameter("param1",
	 * tranType); q.setParameter("param2", dateTran); q.setParameter("param3",
	 * oa); for (Iterator it = q.getParameters().iterator(); it.hasNext();) {
	 * Parameter pa = (Parameter)it.next();
	 * System.out.println(pa.getName()+" - "+q.getParameterValue(pa.getName()));
	 * } //q.executeUpdate(); SOBRA, el getSingleResult lo hace por el
	 * theBalance = (Integer)q.getSingleResult(); etx.commit(); } catch
	 * (Exception ex) {
	 * Logger.getLogger(StoredProcedureJpaController.class.getName
	 * ()).log(Level.SEVERE, null, ex); } finally { em.close(); } return
	 * theBalance; }
	 * 
	 * 
	 * DELIMITER $$
	 * 
	 * DROP PROCEDURE IF EXISTS `getBalanceProc` $$ CREATE
	 * DEFINER=`root`@`localhost` PROCEDURE `getBalanceProc`(IN tranType INT, IN
	 * dateTran TIMESTAMP, IN accNum VARCHAR(20), OUT balance INT, OUT
	 * errorMessage VARCHAR(45)) BEGIN
	 * 
	 * main:begin
	 * 
	 * SET errorMessage = 'Todo bien';
	 * 
	 * IF((select count(*) from accounts where idAccount = accNum) <= 0)THEN SET
	 * errorMessage = 'Origin account does not exist'; LEAVE main; END IF;
	 * 
	 * select currentBalance into balance from Accounts where idAccount =
	 * accNum; INSERT INTO Transactions (TransactionTypes_idTransactionType,
	 * dateTransaction, originAccount) VALUES (tranType, dateTran, accNum);
	 * 
	 * END main;
	 * 
	 * END $$
	 * 
	 * DELIMITER ;
	 */

	@Override
	public List<Object[]> callProcedure3(String sql, int id) {
		try {

			getEntityManager().getTransaction().begin();

			// sql = "call example_procedure(?p1,?p2);"
			getEntityManager().createNativeQuery(sql).setParameter(1, id)
					.executeUpdate();

			getEntityManager().getTransaction().commit();

			// final Query query = getEntityManager().createNativeQuery(sql);
			// List<Object[]> resultList = (List<Object[]>)
			// query.getResultList();
			return null;

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return null;

	}

	@Override
	public String callStoreProcedure(String procedure, String user, int id) {
		String msg = "";
		try {
			StoredProcedureQuery sp = getEntityManager()
					.createStoredProcedureQuery(procedure);
			sp.registerStoredProcedureParameter("user", String.class,
					ParameterMode.IN);
			sp.registerStoredProcedureParameter("i_id", Integer.class,
					ParameterMode.IN);
			sp.registerStoredProcedureParameter("msg", String.class,
					ParameterMode.INOUT);
			sp.setParameter("user", user);
			sp.setParameter("i_id", id);
			sp.setParameter("msg", msg);
			sp.execute();
			msg = (String) sp.getOutputParameterValue("msg");
		} catch (Exception e) {
			msg = e.getStackTrace().toString();
		}
		return msg;
	};

}