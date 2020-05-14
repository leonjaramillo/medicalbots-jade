# medicalbots-jade
A proof of concept about using JADE for Agent-Oriented Programming in Java using Maven, JPA and EclipseLink with an embedded Derby database. It was tested using NetBeans with the JDK 11.

For testing purposes, it has to be run with the next parameters (To put in Run configuration at the IDE or in the command line):

    -gui -agents t_agent:com.ponents.medicalbots.agents.AgenteTriage;h_agent:com.ponents.medicalbots.agents.AgenteHistoria
