package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestCalzado.class, TestPedido.class, TestRopa.class, TestTrabajador.class, TestCliente.class })
public class AllTests {

}
