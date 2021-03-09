package facturadesktopecuadoryomar.logica;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFacturacion
{
  private final EntityManager entityManager;
  
  private EntityManagerFacturacion() {
    EntityManagerFactory factory = Persistence.createEntityManagerFactory("FacturaDesktopEcuadorPU");
    this.entityManager = factory.createEntityManager();
  }
  
  public static EntityManagerFacturacion getInstance() {
    return EntityManagerSecurityHolder.INSTANCE;
  }
  
  public EntityManager getEntityManager() {
    return this.entityManager;
  }
  
  private static class EntityManagerSecurityHolder
  {
    private static final EntityManagerFacturacion INSTANCE = new EntityManagerFacturacion();
  }
}