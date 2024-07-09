package Server;

import StudentApp.StudentManagement;
import StudentApp.StudentManagementHelper;
import StudentApp.StudentManagementPOA;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.ArrayList;
import java.util.List;

public class CorbaServer {

    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            // Activation POA
            POA poa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            poa.the_POAManager().activate();
            // Enregistrer le servant dans ORB
            StudentServer studentServer = new StudentServer();
            studentServer.setORB(orb);
            // Avoir la référence du servant
            Object ref = poa.servant_to_reference(studentServer);
            StudentManagement href = StudentManagementHelper.narrow(ref);
            // Faire appel au service CORBA
            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            NameComponent[] path = ncRef.to_name("StudentManagement");
            // Demarrer la communication
            ncRef.rebind(path, href);
            System.out.println("Server ready and waiting ...");
            orb.run();

        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du serveur " + e);
        }
    }

}
