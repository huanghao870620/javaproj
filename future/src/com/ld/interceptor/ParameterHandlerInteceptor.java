package com.ld.interceptor;

import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

/**
 * 
 * @author huang.hao
 *
 */
@Intercepts({@Signature(type=ParameterHandler.class,method="setParameters",args={PreparedStatement.class})})
public class ParameterHandlerInteceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		return null;
	}

	@Override
	public Object plugin(Object target) {
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
