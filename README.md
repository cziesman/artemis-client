# artemis-client
Demo Spring Boot project for a remote JMS client

A few items related to JMS need to be configured in the EAP 7.2 server in order for this example to work.
* A new user `guest:guest` in group `guest` needs to be created.
* A JMS queue with the name `mailbox` and the JNDI name `java:jboss/exported/jms/queue/mailbox` needs to be created.
