package facturadesktopecuadoryomar.logica;

import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public abstract class AbstractFacadeDmz<T>
{
  protected boolean autoCommit = true;
  private EntityManagerFactory ef = Persistence.createEntityManagerFactory("FacturaWebClientePU");
  private EntityManager em = null;
  
  public AbstractFacadeDmz() {
    this.em = this.ef.createEntityManager();
  }
  
  protected EntityManager getEntitiManager() {
    return this.em;
  }
  
  protected EntityTransaction getTransaction() {
    return getEntitiManager().getTransaction();
  }
  
  public void guardar(T entidad) throws SQLException {
    if (this.autoCommit) {
      this.getTransaction().begin();
    }
    getEntitiManager().persist(entidad);
    if (this.autoCommit) {
      this.getTransaction().commit();
    }
  }
  
  public void modificar(T entidad) throws SQLException {
    if (this.autoCommit){
      if(!this.getTransaction().isActive()) {
        this.getTransaction().begin();
      }
    }
    
    this.getEntitiManager().merge(entidad);
    if (this.autoCommit) {
      this.getTransaction().commit();
    }
  }
  
  public void remove(T entidad) throws SQLException {
    if (this.autoCommit) {
      this.getTransaction().begin();
    }
    getEntitiManager().remove(entidad);
    if (this.autoCommit)
      this.getTransaction().commit(); 
  }
}