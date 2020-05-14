package com.ponents.medicalbots.agents;

import com.ponents.medicalbots.model.Triage;
import com.ponents.medicalbots.model.TriageRepository;
import com.ponents.medicalbots.model.TriageRepositoryImpl;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RecibirTriageBehaviour extends CyclicBehaviour {

    private static final String PERSISTENCE_UNIT_NAME = "medicalRecords";
    private EntityManagerFactory factory;
    String mensaje;

    @Override
    public void action() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        TriageRepository archivoTriage = new TriageRepositoryImpl(em);

        Triage triage = new Triage();
        ACLMessage msg = getAgent().receive();

        if (msg != null) {
            System.out.println("Recibiendo los datos del triage");
            mensaje = msg.getContent();
            if (mensaje.equalsIgnoreCase("listar")) {
                List<Triage> registros = archivoTriage.getAll();
                System.out.println("ID -- FECHA -- HORA -- SÍNTOMAS");
                registros.forEach((triageItem) -> {
                    System.out.println(triageItem.toString());
                });
                System.out.println();
            } else {
                String parametros[] = mensaje.split(",");
                triage.setDate(parametros[0]);
                triage.setTime(parametros[1]);
                triage.setSymptoms(parametros[2]);
                archivoTriage.add(triage);
                System.out.println("Los datos del triage se guardaron con éxito");
                if (parametros[2].contains("tos")) {
                    ACLMessage msgSaliente = new ACLMessage(ACLMessage.REQUEST);
                    msgSaliente.addReceiver(new AID("h_agent", AID.ISLOCALNAME));
                    msgSaliente.setContent("Revisar caso fecha " + parametros[0] + ",y hora " + parametros[1] + ",0," + parametros[2]);
                    getAgent().send(msgSaliente);
                    System.out.println("Además, los datos del triage se guardaron en una historia médica");
                }
            }
        } else {
            block();
        }
    }
}
