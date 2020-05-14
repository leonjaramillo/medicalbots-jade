package com.ponents.medicalbots.agents;

import com.ponents.medicalbots.model.MedicalRecord;
import com.ponents.medicalbots.model.MedicalRecordRepository;
import com.ponents.medicalbots.model.MedicalRecordRepositoryImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RecibirHistoriaBehaviour extends CyclicBehaviour {

    private static final String PERSISTENCE_UNIT_NAME = "medicalRecords";
    private EntityManagerFactory factory;
    String mensaje;

    @Override
    public void action() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        MedicalRecordRepository archivoHistoriaMedica = new MedicalRecordRepositoryImpl(em);

        MedicalRecord historiaMedica = new MedicalRecord();
        ACLMessage msg = getAgent().receive();

        if (msg != null) {
            System.out.println("Recibiendo los datos de la historia médica");
            mensaje = msg.getContent();
            if (mensaje.equalsIgnoreCase("listar")) {
                List<MedicalRecord> registros = archivoHistoriaMedica.getAll();
                System.out.println("ID -- NOMBRES -- APELLIDOS");
                registros.forEach((historia) -> {
                    System.out.println(historia.toString());
                });
                System.out.println();
            } else {
                String parametros[] = mensaje.split(",");
                historiaMedica.setFirstName(parametros[0]);
                historiaMedica.setLastName(parametros[1]);
                historiaMedica.setAge(Integer.parseInt(parametros[2]));
                historiaMedica.setDescription(parametros[3]);
                archivoHistoriaMedica.add(historiaMedica);
                System.out.println("Los datos de la historia médica se guardaron con éxito");
            }
        } else {
            block();
        }
    }
}
