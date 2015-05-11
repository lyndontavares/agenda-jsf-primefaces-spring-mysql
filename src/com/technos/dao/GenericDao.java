package com.technos.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;
 
public interface GenericDao<T, I extends Serializable> {
 
 public T save(T entity);
  
 public void remove(T entity);
  
 public T getById(Class<T> classe, I pk);

 public List<T> getByField(String tabela, String Campo, String valor);
  
 public List<T> getAll(Class<T> classe);
 
 public List<T> getAllWhere(Class<T> classe, String where );

 public EntityManager getEntityManager();
  
 public  List<Object[]> OpenQuery(String sql);

 public  List<T> OpenQuery2(String sql);

 public  ResultSet OpenQueryRS(String sql);
 
 public  String callProcedure(String procedure, int id);
 
 public  String callProcedure2(Class<T> classe, String procedure );

 public  String callStoreProcedure(String procedure, String user, int id );
 
 public List<Object[]> callProcedure3(String sql, int id);

 public void executarRegraPorId(int regra, int id);
}