## Steps to reproduce bug in spring-data-redis

We are trying to read/write redis and then write to Kafka within a transaction

Config.java contains the Spring configuration required

TransactionalFooService contains the @Transactional method

BootstrApp.java calls the method

```
docker-compose up -d
```

then run the app

```
mvn spring-boot:run
```

notice the stacktrace 

```

java.lang.IllegalStateException: Failed to execute CommandLineRunner
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:780) ~[spring-boot-2.6.7.jar:2.6.7]
	at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:761) ~[spring-boot-2.6.7.jar:2.6.7]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:310) ~[spring-boot-2.6.7.jar:2.6.7]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1312) ~[spring-boot-2.6.7.jar:2.6.7]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1301) ~[spring-boot-2.6.7.jar:2.6.7]
	at guru.bonacci.bug.BootstrApp.main(BootstrApp.java:12) ~[classes/:na]
Caused by: org.springframework.data.keyvalue.core.UncategorizedKeyValueException: nested exception is java.lang.NullPointerException
	at org.springframework.data.keyvalue.core.KeyValuePersistenceExceptionTranslator.translateExceptionIfPossible(KeyValuePersistenceExceptionTranslator.java:55) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.core.KeyValueTemplate.resolveExceptionIfPossible(KeyValueTemplate.java:476) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.core.KeyValueTemplate.execute(KeyValueTemplate.java:364) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.core.KeyValueTemplate.update(KeyValueTemplate.java:221) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at org.springframework.data.redis.core.RedisKeyValueTemplate.update(RedisKeyValueTemplate.java:178) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.repository.support.SimpleKeyValueRepository.save(SimpleKeyValueRepository.java:80) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:289) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:137) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:121) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.RepositoryComposition$RepositoryFragments.invoke(RepositoryComposition.java:529) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.RepositoryComposition.invoke(RepositoryComposition.java:285) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.RepositoryFactorySupport$ImplementationMethodExecutionInterceptor.invoke(RepositoryFactorySupport.java:639) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:163) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:138) ~[spring-data-commons-2.6.4.jar:2.6.4]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215) ~[spring-aop-5.3.19.jar:5.3.19]
	at com.sun.proxy.$Proxy54.save(Unknown Source) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:215) ~[spring-aop-5.3.19.jar:5.3.19]
	at com.sun.proxy.$Proxy54.save(Unknown Source) ~[na:na]
	at guru.bonacci.bug.RedisService.proceed(RedisService.java:18) ~[classes/:na]
	at guru.bonacci.bug.TransactionalFooService.txMethod(TransactionalFooService.java:17) ~[classes/:na]
	at guru.bonacci.bug.TransactionalFooService$$FastClassBySpringCGLIB$$4d500efc.invoke(<generated>) ~[classes/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123) ~[spring-tx-5.3.19.jar:5.3.19]
	at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:388) ~[spring-tx-5.3.19.jar:5.3.19]
	at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119) ~[spring-tx-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763) ~[spring-aop-5.3.19.jar:5.3.19]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:708) ~[spring-aop-5.3.19.jar:5.3.19]
	at guru.bonacci.bug.TransactionalFooService$$EnhancerBySpringCGLIB$$5c0b68ca.txMethod(<generated>) ~[classes/:na]
	at guru.bonacci.bug.BootstrApp.lambda$0(BootstrApp.java:19) ~[classes/:na]
	at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:777) ~[spring-boot-2.6.7.jar:2.6.7]
	... 5 common frames omitted
Caused by: java.lang.NullPointerException: null
	at org.springframework.data.redis.core.RedisKeyValueAdapter.lambda$put$0(RedisKeyValueAdapter.java:235) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:223) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:190) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.redis.core.RedisTemplate.execute(RedisTemplate.java:177) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.redis.core.RedisKeyValueAdapter.put(RedisKeyValueAdapter.java:230) ~[spring-data-redis-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.core.KeyValueTemplate.lambda$update$1(KeyValueTemplate.java:221) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	at org.springframework.data.keyvalue.core.KeyValueTemplate.execute(KeyValueTemplate.java:362) ~[spring-data-keyvalue-2.6.4.jar:2.6.4]
	... 53 common frames omitted

2022-04-30 11:40:58.700  INFO 21972 --- [           main] o.a.k.clients.producer.KafkaProducer     : [Producer clientId=producer-tx-0, transactionalId=tx-0] Closing the Kafka producer with timeoutMillis = 30000 ms.
2022-04-30 11:40:58.709  INFO 21972 --- [           main] org.apache.kafka.common.metrics.Metrics  : Metrics scheduler closed
2022-04-30 11:40:58.710  INFO 21972 --- [           main] org.apache.kafka.common.metrics.Metrics  : Closing reporter org.apache.kafka.common.metrics.JmxReporter
2022-04-30 11:40:58.710  INFO 21972 --- [           main] org.apache.kafka.common.metrics.Metrics  : Metrics reporters closed
2022-04-30 11:40:58.711  INFO 21972 --- [           main] o.a.kafka.common.utils.AppInfoParser     : App info kafka.producer for producer-tx-0 unregistered
```

The code passes this line in RedisService

``` 
if (repo.existsById(foo.getId())) {
```

and fails during the execution of 

```
repo.save(foo);
```

In org.springframework.data.redis.core.RedisKeyValueAdapter, the put method seems to contain a bug (last line of this snippet)

```
@Override
public Object put(Object id, Object item, String keyspace) {

	RedisData rdo = item instanceof RedisData ? (RedisData) item : new RedisData();
	if (!(item instanceof RedisData)) {
		converter.write(item, rdo);
	}

	if (ObjectUtils.nullSafeEquals(EnableKeyspaceEvents.ON_DEMAND, enableKeyspaceEvents)
			&& this.expirationListener.get() == null) {

		if (rdo.getTimeToLive() != null && rdo.getTimeToLive() > 0) {
			initKeyExpirationListener();
		}
	}

	if (rdo.getId() == null) {
		rdo.setId(converter.getConversionService().convert(id, String.class));
	}

	redisOps.execute((RedisCallback<Object>) connection -> {

		byte[] key = toBytes(rdo.getId());
		byte[] objectKey = createKey(rdo.getKeyspace(), rdo.getId());

		boolean isNew = connection.del(objectKey) == 0;
```

The JavaDoc on connection.del() states:

```
/**
 * Delete given {@code keys}.
 *
 * @param keys must not be {@literal null}.
 * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
 * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
 */
@Nullable
Long del(byte[]... keys);
```

As a result, since we're trying to use a transaction here, we try execute 'null == 0'

org.springframework.data.redis.connection.RedisKeyCommands.del() contains @Nullable as a warning.
Hence, likely a bug.
