run:
	mvn package
	/opt/payara6/bin/asadmin redeploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war

test_deploy:
	mvn package -Dmaven.test.skip
	/opt/payara6/bin/asadmin deploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war
	mvn test

test:
	mvn package -Dmaven.test.skip
	/opt/payara6/bin/asadmin redeploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war
	mvn test
