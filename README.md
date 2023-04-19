# The Spring Boot 3, Hibernate 6 and Postgresql (12) CLOB/TEXT challenge

This project is a simple example of how to use Spring Boot 3 and Postgresql 12 with CLOB/TEXT fields.

We have a 'Track' table which has a CLOB (text) field name 'description'.

And a REST endpoint '/api/tracks' which should return all the tracks in the database.

However with the current configuration, the description field is not returned 😢 

SUGGESTIONS ARE WELCOME! 


## Running the application

Make sure you're using Java 17 which is enforced by the maven plugin.

### PostgreSQL

Run the PostgreSQL database with Docker using:

```bash
   cd src/main/docker   
   docker-compose -f postgresql.yml up -d --force-recreate
```

Make sure a database with name "postgres" exists.
Add a user "postgres" with password "postgres".

### Track table DDL

```sql  
    CREATE TABLE cfp_track (
        id SERIAL PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description TEXT
    );
```

### Spring Boot

Now run the Spring Boot application with:

```bash
./mvnw spring-boot:run
```

### Hibernate 5 and SpringBoot 2

FYI - The below annotations work with Hibernate 5.3 and Spring Boot 2 but not with Hibernate 6 because @Type(type="") does not exist anymore...

```java
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "description")
    private String description;
```

### The error message 

When calling the REST endpoint /api/tracks, the following error message is returned:

```java
jpa.JpaSystemException: Unable to extract JDBC value for position `2`] with root cause

org.postgresql.util.PSQLException: Bad value for type long : Developer candy: stuff we want to know about but dont (generally) at work, Robotics, biological computing, cybernetics, AI, new toys, tomorrows world
        at org.postgresql.jdbc.PgResultSet.toLong(PgResultSet.java:3328) ~[postgresql-42.6.0.jar:42.6.0]
        at org.postgresql.jdbc.PgResultSet.getLong(PgResultSet.java:2540) ~[postgresql-42.6.0.jar:42.6.0]
        at org.postgresql.jdbc.PgResultSet.getClob(PgResultSet.java:493) ~[postgresql-42.6.0.jar:42.6.0]
        at com.zaxxer.hikari.pool.HikariProxyResultSet.getClob(HikariProxyResultSet.java) ~[HikariCP-5.0.1.jar:na]
        at org.hibernate.type.descriptor.jdbc.ClobJdbcType$1.doExtract(ClobJdbcType.java:62) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.type.descriptor.jdbc.BasicExtractor.extract(BasicExtractor.java:44) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.readCurrentRowValues(JdbcValuesResultSetImpl.java:263) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.advance(JdbcValuesResultSetImpl.java:244) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.processNext(JdbcValuesResultSetImpl.java:85) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.AbstractJdbcValues.next(AbstractJdbcValues.java:29) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.internal.RowProcessingStateStandardImpl.next(RowProcessingStateStandardImpl.java:88) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:197) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:33) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.doExecuteQuery(JdbcSelectExecutorStandardImpl.java:443) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.executeQuery(JdbcSelectExecutorStandardImpl.java:166) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.list(JdbcSelectExecutorStandardImpl.java:91) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.spi.JdbcSelectExecutor.list(JdbcSelectExecutor.java:31) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.lambda$new$0(ConcreteSqmSelectQueryPlan.java:113) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.withCacheableSqmInterpretation(ConcreteSqmSelectQueryPlan.java:335) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.performList(ConcreteSqmSelectQueryPlan.java:276) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.QuerySqmImpl.doList(QuerySqmImpl.java:571) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.spi.AbstractSelectionQuery.list(AbstractSelectionQuery.java:363) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.QuerySqmImpl.list(QuerySqmImpl.java:1073) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.Query.getResultList(Query.java:94) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll(SimpleJpaRepository.java:408) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll(SimpleJpaRepository.java:94) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:288) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:136) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:120) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:516) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:285) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:628) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:168) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:143) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:77) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:391) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:163) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:218) ~[spring-aop-6.0.7.jar:6.0.7]
        at jdk.proxy2/jdk.proxy2.$Proxy111.findAll(Unknown Source) ~[na:na]
        at com.devoxx.cfp.service.TrackService.findAll(TrackService.java:34) ~[classes/:na]
        at com.devoxx.cfp.web.rest.TrackResource.getAllTracks(TrackResource.java:38) ~[classes/:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:207) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:152) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1081) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:537) ~[tomcat-embed-core-10.1.7.jar:6.0]
        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:631) ~[tomcat-embed-core-10.1.7.jar:6.0]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:166) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]

2023-04-19T19:16:53.848+02:00 ERROR 17370 --- [nio-8080-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.orm.jpa.JpaSystemException: Unable to extract JDBC value for position `2`] with root cause

org.postgresql.util.PSQLException: Bad value for type long : Developer candy: stuff we want to know about but dont (generally) at work, Robotics, biological computing, cybernetics, AI, new toys, tomorrows world
        at org.postgresql.jdbc.PgResultSet.toLong(PgResultSet.java:3328) ~[postgresql-42.6.0.jar:42.6.0]
        at org.postgresql.jdbc.PgResultSet.getLong(PgResultSet.java:2540) ~[postgresql-42.6.0.jar:42.6.0]
        at org.postgresql.jdbc.PgResultSet.getClob(PgResultSet.java:493) ~[postgresql-42.6.0.jar:42.6.0]
        at com.zaxxer.hikari.pool.HikariProxyResultSet.getClob(HikariProxyResultSet.java) ~[HikariCP-5.0.1.jar:na]
        at org.hibernate.type.descriptor.jdbc.ClobJdbcType$1.doExtract(ClobJdbcType.java:62) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.type.descriptor.jdbc.BasicExtractor.extract(BasicExtractor.java:44) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.readCurrentRowValues(JdbcValuesResultSetImpl.java:263) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.advance(JdbcValuesResultSetImpl.java:244) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.processNext(JdbcValuesResultSetImpl.java:85) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.jdbc.internal.AbstractJdbcValues.next(AbstractJdbcValues.java:29) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.internal.RowProcessingStateStandardImpl.next(RowProcessingStateStandardImpl.java:88) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:197) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:33) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.doExecuteQuery(JdbcSelectExecutorStandardImpl.java:443) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.executeQuery(JdbcSelectExecutorStandardImpl.java:166) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.list(JdbcSelectExecutorStandardImpl.java:91) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.sql.exec.spi.JdbcSelectExecutor.list(JdbcSelectExecutor.java:31) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.lambda$new$0(ConcreteSqmSelectQueryPlan.java:113) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.withCacheableSqmInterpretation(ConcreteSqmSelectQueryPlan.java:335) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.ConcreteSqmSelectQueryPlan.performList(ConcreteSqmSelectQueryPlan.java:276) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.QuerySqmImpl.doList(QuerySqmImpl.java:571) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.spi.AbstractSelectionQuery.list(AbstractSelectionQuery.java:363) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.sqm.internal.QuerySqmImpl.list(QuerySqmImpl.java:1073) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.hibernate.query.Query.getResultList(Query.java:94) ~[hibernate-core-6.1.7.Final.jar:6.1.7.Final]
        at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll(SimpleJpaRepository.java:408) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at org.springframework.data.jpa.repository.support.SimpleJpaRepository.findAll(SimpleJpaRepository.java:94) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:288) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:136) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:120) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:516) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:285) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:628) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:168) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:143) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:77) ~[spring-data-commons-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:391) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:163) ~[spring-data-jpa-3.0.4.jar:3.0.4]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184) ~[spring-aop-6.0.7.jar:6.0.7]
        at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:218) ~[spring-aop-6.0.7.jar:6.0.7]
        at jdk.proxy2/jdk.proxy2.$Proxy111.findAll(Unknown Source) ~[na:na]
        at com.devoxx.cfp.service.TrackService.findAll(TrackService.java:34) ~[classes/:na]
        at com.devoxx.cfp.web.rest.TrackResource.getAllTracks(TrackResource.java:38) ~[classes/:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:207) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:152) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:118) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:884) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1081) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:903) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:537) ~[tomcat-embed-core-10.1.7.jar:6.0]
        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885) ~[spring-webmvc-6.0.7.jar:6.0.7]
        at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:631) ~[tomcat-embed-core-10.1.7.jar:6.0]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-6.0.7.jar:6.0.7]
        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116) ~[spring-web-6.0.7.jar:6.0.7]
        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:166) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-10.1.7.jar:10.1.7]
        at java.base/java.lang.Thread.run(Thread.java:833) ~[na:na]
```
