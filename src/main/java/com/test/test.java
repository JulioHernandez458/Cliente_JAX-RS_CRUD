package com.test;

import com.domain.Persona;
import java.util.List;
import java.util.Scanner;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/*
 * @author julioh
 */
public class test {

    private static final String URL_BASE = "http://localhost:8080/CRUD-JAX-RS-18891530824644488918.0/api";
    private static Client cliente;
    private static WebTarget webTarget;
    private static Persona persona;
    private static List<Persona> personas;
    private static Invocation.Builder invocationBuilder;
    private static Response response;

    public static void main(String[] args) {

        cliente = ClientBuilder.newClient();

        //Leer una persona (metodo get)
        webTarget = cliente.target(URL_BASE).path("/personas");

        Scanner sc = new Scanner(System.in);
        String op;

        do {

            System.out.println("Bienvenido a la aplicación cliente CRUD JAX-WS! ");
            System.out.println("Escoja una de las siguinetes opciones del menú: ");
            System.out.println("1. Listar a todas las personas.");
            System.out.println("2. Agregar una nueva persona.");
            System.out.println("3. Actualizar una persona.");
            System.out.println("4. Eliminar una persona.");
            System.out.println("5. Buscar a una persona por id.");
            System.out.println("");
            int op2 = sc.nextInt();
            sc.nextLine();
            switch (op2) {
                case 1:
                    //Leer todas las personas (metodo get con readEntity de tipo List<>
                    personas = webTarget.request(MediaType.APPLICATION_XML).get(Response.class).readEntity(new GenericType<List<Persona>>() {
                    });
                    System.out.println("\nPersonas recuperadas");
                    imprimirPersonas(personas);
                    break;
                case 2:

                    System.out.println(" ** Agregue los datos de la nueva persona ** ");
                    Persona p = new Persona();
                    System.out.println("Nombre de la persona: ");
                    String nombre = sc.nextLine();
                    sc.nextLine();
                    p.setNombre(nombre);
                    System.out.println("Apellido de la persona: ");
                    String apellido = sc.nextLine();
                    sc.nextLine();
                    p.setApellido(apellido);
                    System.out.println("Email de la persona: ");
                    String email = sc.nextLine();
                    sc.nextLine();
                    p.setEmail(email);
                    System.out.println("Telefono de la persona: ");
                    String telefono = sc.nextLine();
                    sc.nextLine();
                    p.setTelefono(telefono);

                    invocationBuilder = webTarget.request(MediaType.APPLICATION_XML);
                    response = invocationBuilder.post(Entity.entity(p, MediaType.APPLICATION_XML));
                    System.out.println("");
                    System.out.println(response.getStatus());
                    //Recuperamos la personas recien agregada para despues modificarla y al final eliminarla
                    Persona personaRecuperada = response.readEntity(Persona.class);
                    System.out.println("Persona agregada:" + personaRecuperada);

                    break;
                case 3:

                    System.out.println(" ** Agregar los nuevos datos de la persona ** ");
                    Persona p2 = new Persona();
                    System.out.println("ID de la persona a actualizar: ");
                    Integer id = sc.nextInt();
                    sc.nextLine();
                    p2.setIdPersona(id);
                    System.out.println("Nombre de la persona: ");
                    String nombre2 = sc.nextLine();
                    sc.nextLine();
                    p2.setNombre(nombre2);
                    System.out.println("Apellido de la persona: ");
                    String apellido2 = sc.nextLine();
                    sc.nextLine();
                    p2.setApellido(apellido2);
                    System.out.println("Email de la persona: ");
                    String email2 = sc.nextLine();
                    sc.nextLine();
                    p2.setEmail(email2);
                    System.out.println("Telefono de la persona: ");
                    String telefono2 = sc.nextLine();
                    sc.nextLine();
                    p2.setTelefono(telefono2);

                    //Modificar la persona (metodo put)
                    //persona recuperada anteriormente
                    Persona personaModificar = p2;
                    String pathId = "/" + personaModificar.getIdPersona();
                    invocationBuilder = webTarget.path(pathId).request(MediaType.APPLICATION_XML);
                    response = invocationBuilder.put(Entity.entity(personaModificar, MediaType.APPLICATION_XML));

                    System.out.println("");
                    System.out.println("response:" + response.getStatus());
                    System.out.println("Persona modifica: " + response.readEntity(Persona.class));
                    break;
                case 4:
                    sc.nextLine();
                    System.out.println(" ** Agregar el id del registro a eliminar ** ");
                    System.out.println("ID de la persona a eliminar: ");
                    Persona p3 = new Persona();
                    Integer id2 = sc.nextInt();
                    sc.nextLine();
                    p3.setIdPersona(id2);

                    //eliminar una persona
                    //persona recuperada anteriormente
                    Persona personaEliminar = p3;
                    String pathEliminarId = "/" + personaEliminar.getIdPersona();
                    invocationBuilder = webTarget.path(pathEliminarId).request(MediaType.APPLICATION_XML);
                    response = invocationBuilder.delete();
                    System.out.println("");
                    System.out.println("response:" + response.getStatus());
                    System.out.println("Persona Eliminada" + personaEliminar);
                    break;
                case 5:
                    sc.nextLine();
                    System.out.println(" ** Buscar una persona por el ID ** ");
                    System.out.println("ID de la persona a buscar: ");
                    Integer id3 = sc.nextInt();
                    sc.nextLine();

                    //Proporcionamos un idPersona valido
                    persona = webTarget.path("/" + id3).request(MediaType.APPLICATION_XML).get(Persona.class);
                    System.out.println("Persona recuperada:" + persona);

                    break;

            }

            System.out.println("¿Desea Continuar en el menú?");
            System.out.println("S/N: ");
            op = sc.nextLine();

        } while ("S".equals(op) || "s".equals(op));

    }

    private static void imprimirPersonas(List<Persona> personas) {
        for (Persona p : personas) {
            System.out.println("Persona: " + p);
        }
    }

}
