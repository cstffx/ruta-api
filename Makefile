run:
	mvn package
	/opt/payara6/bin/asadmin redeploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war

start:
	asadmin start-domain

build:
	mvn package -Dmaven.test.skip

deploy:
	/opt/payara6/bin/asadmin deploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war

redeploy:
	/opt/payara6/bin/asadmin redeploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war

check:
	mvn package -Dmaven.test.skip
	/opt/payara6/bin/asadmin deploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war
	mvn test
test:
	mvn package -Dmaven.test.skip
	asadmin undeploy ruta
	asadmin deploy --name ruta /home/user/eclipse-workspace/ruta/target/ruta.war
	#mvn test -Dmaven.package.skip
	mvn test-compile surefire:test
	
