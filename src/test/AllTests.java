package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestPedido.class, TestTrabajador.class, TestCliente.class, TestProducto.class })
public class AllTests {

}
