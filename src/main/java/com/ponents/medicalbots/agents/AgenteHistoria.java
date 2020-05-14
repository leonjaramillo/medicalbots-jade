package com.ponents.medicalbots.agents;

import jade.core.Agent;

public class AgenteHistoria extends Agent {
    @Override
    protected void setup() {
        System.out.println("El agente encargado de las historias médicas está listo");
        addBehaviour(new RecibirHistoriaBehaviour());
    }
}
