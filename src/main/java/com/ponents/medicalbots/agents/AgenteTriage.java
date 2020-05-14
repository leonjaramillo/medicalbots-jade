package com.ponents.medicalbots.agents;

import jade.core.Agent;

public class AgenteTriage extends Agent {
    @Override
    protected void setup() {
        System.out.println("El agente encargado del proceso de triage está listo");
        addBehaviour(new RecibirTriageBehaviour());
    }
}
