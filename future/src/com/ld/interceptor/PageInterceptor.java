package com.ld.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.mapping.MappedStatement.Builder; 
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;

import com.ld.page.BoundSqlSqlSource;
import com.ld.page.Page;
import com.ld.page.PageIn;


/**
 * 分页拦截器
 * @author hao.huang
 *
 */

@Intercepts({@Signature(type=Executor.class,method="query",args={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class PageInterceptor implements Interceptor {
	
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
	    MappedStatement mappedStatement=(MappedStatement)invocation.getArgs()[0];      
	    String mappedId = mappedStatement.getId();
	    Object parameter = invocation.getArgs()[1];   
	    BoundSql boundSql = mappedStatement.getBoundSql(parameter);   
	    String originalSql = boundSql.getSql().trim();  
	    Object parameterObject = boundSql.getParameterObject();  
	  
	    if(mappedId.indexOf("ByPaging") != -1){
	    	 String countSql = getCountSql(originalSql);  
	         Connection connection=mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection()  ;            
	         PreparedStatement countStmt = connection.prepareStatement(countSql);    
	         BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);  
	         DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);  
	         parameterHandler.setParameters(countStmt);  
	         ResultSet rs = countStmt.executeQuery();  
	         int totpage=0;  
	         if (rs.next()) {    
	           totpage = rs.getInt(1);    
	         }  
	         rs.close();    
	         countStmt.close();    
	         connection.close();  
	           
	         PageIn pi = (PageIn)parameterObject;
	         Page page = pi.getPage();
	         //分页计算  
	         page.setTotalRecord(totpage);  
	         
	         StringBuilder sb = new StringBuilder();
	         BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());  
	         MappedStatement newMs = copyFromMappedStatement(mappedStatement,new BoundSqlSqlSource(newBoundSql));    
	         invocation.getArgs()[0]= newMs; 
	    }
	   
	    return invocation.proceed(); 
	}
	
	
	/** 
	   * 复制BoundSql对象 
	   */  
	  private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {  
	    BoundSql newBoundSql = new BoundSql(ms.getConfiguration(),sql, boundSql.getParameterMappings(), boundSql.getParameterObject());  
	    for (ParameterMapping mapping : boundSql.getParameterMappings()) {  
	        String prop = mapping.getProperty();  
	        if (boundSql.hasAdditionalParameter(prop)) {  
	            newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));  
	        }  
	    }  
	    return newBoundSql;  
	  }  
	  
	  
	  /** 
	   * 复制MappedStatement对象 
	   */  
	  private MappedStatement copyFromMappedStatement(MappedStatement ms,SqlSource newSqlSource) {  
	    Builder builder = new Builder(ms.getConfiguration(),ms.getId(),newSqlSource,ms.getSqlCommandType());  
	      
	    builder.resource(ms.getResource());  
	    builder.fetchSize(ms.getFetchSize());  
	    builder.statementType(ms.getStatementType());  
	    builder.keyGenerator(ms.getKeyGenerator()); 
	    builder.keyProperty(ms.getKeyProperties()[0]);  
	    builder.timeout(ms.getTimeout());  
	    builder.parameterMap(ms.getParameterMap());  
	    builder.resultMaps(ms.getResultMaps());  
	    builder.resultSetType(ms.getResultSetType());  
	    builder.cache(ms.getCache());  
	    builder.flushCacheRequired(ms.isFlushCacheRequired());  
	    builder.useCache(ms.isUseCache());  
	      
	    return builder.build();  
	  }  
	  
	
	  /** 
	   * 根据原Sql语句获取对应的查询总记录数的Sql语句 
	   */  
	  private String getCountSql(String sql) {  
	    return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";  
	  }  
	
	
	 @Override
	public Object plugin(Object target) {
	      return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
