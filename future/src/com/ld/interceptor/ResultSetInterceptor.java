package com.ld.interceptor;

import java.sql.Statement;
import java.util.Properties;

import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 
 * @author huang.hao
 *
 */
@Intercepts({@Signature(type=ResultSetHandler.class,method="handleResultSets",args={Statement.class})})
public class ResultSetInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
//		FastResultSetHandler frsHandler = (FastResultSetHandler)invocation.getTarget();
//		Method method = invocation.getMethod();
//		Object args[] = invocation.getArgs();
//		Statement stmt = (Statement) args[0];
//		stmt.getResultSet();
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
