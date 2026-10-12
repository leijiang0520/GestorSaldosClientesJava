package vista;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.ClienteDAO;
import dao.OperacionDAO;
import modelo.Cliente;
import modelo.Operacion;

public class VentanaPrincipal extends JFrame {
    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;
    private OperacionDAO operacionDAO;

    private JComboBox<Cliente> comboClientes;
    private JTextField txtNombreClienteNuevo;
    private JTextField txtTelefonoClienteNuevo;
    private JTextField txtMontoRecarga;
    private JTextField txtMontoConsumo;
    private JLabel labelSaldoActual;

    public VentanaPrincipal() {
    	
        clienteDAO = new ClienteDAO();
        operacionDAO = new OperacionDAO();
        crearVentanaPrincipal();
        cargarListaClientes();
    }

    private void crearVentanaPrincipal() {
        setTitle("Panel de Administración - Gestión de saldos clientes");
        setSize(600, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //1 选择客户
        panelPrincipal.add(new JLabel("<html><b>Seleccionar Cliente:</b></html>"));
        comboClientes = new JComboBox<>();
        comboClientes.addActionListener(e -> actualizarSaldoClienteSeleccionado());
        panelPrincipal.add(comboClientes);
        labelSaldoActual = new JLabel("Saldo actual: ---");
        panelPrincipal.add(labelSaldoActual);
        panelPrincipal.add(Box.createVerticalStrut(18));

        //2 添加客户
        panelPrincipal.add(new JLabel("<html><b>Añadir nuevo cliente</b></html>")); //GRUESO
        panelPrincipal.add(new JLabel("Nombre:"));
        txtNombreClienteNuevo = new JTextField();
        panelPrincipal.add(txtNombreClienteNuevo);
        panelPrincipal.add(new JLabel("Teléfono:"));
        txtTelefonoClienteNuevo = new JTextField();
        panelPrincipal.add(txtTelefonoClienteNuevo);
        JButton btnAnadirCliente = new JButton("Guardar Cliente Nuevo");
        btnAnadirCliente.addActionListener(e -> agregarNuevoCliente());
        panelPrincipal.add(btnAnadirCliente);
        panelPrincipal.add(Box.createVerticalStrut(18));

        //3 充值
        panelPrincipal.add(new JLabel("<html><b>Recargar saldo (>=100€ regalo 20€)</b></html>"));
        panelPrincipal.add(new JLabel("Monto a recargar:"));
        txtMontoRecarga = new JTextField();
        panelPrincipal.add(txtMontoRecarga);
        JButton btnRecargar = new JButton("Hacer recarga");
        btnRecargar.addActionListener(e -> hacerRecarga());
        panelPrincipal.add(btnRecargar);
        panelPrincipal.add(Box.createVerticalStrut(18));

        //4 消费
        panelPrincipal.add(new JLabel("<html><b>Registrar consumo</b></html>"));
        panelPrincipal.add(new JLabel("Monto del consumo:"));
        txtMontoConsumo = new JTextField();
        panelPrincipal.add(txtMontoConsumo);
        JButton btnConsumo = new JButton("Descontar consumo");
        btnConsumo.addActionListener(e -> hacerConsumo());
        panelPrincipal.add(btnConsumo);
        panelPrincipal.add(Box.createVerticalStrut(18));

        //5 查看历史
        JButton btnHistorial = new JButton("Ver Historial del cliente seleccionado");
        btnHistorial.addActionListener(e -> verHistorial());
        panelPrincipal.add(btnHistorial);
        this.add(panelPrincipal);
    }

    private void cargarListaClientes() {
        comboClientes.removeAllItems();
        List<Cliente> lista = clienteDAO.listarTodosClientes();
        for (Cliente c : lista) {
            comboClientes.addItem(c);
        }
    }

    private void actualizarSaldoClienteSeleccionado() {
        Cliente c = (Cliente) comboClientes.getSelectedItem();
        if (c != null) {
            labelSaldoActual.setText("Saldo actual: " + c.getSaldo() + " €");
        } else {
            labelSaldoActual.setText("Saldo actual: ---");
        }
    }

    private void agregarNuevoCliente() {
        String nombre = txtNombreClienteNuevo.getText().trim();//TRIM: QUITAR ESPACIOS
        String telefono = txtTelefonoClienteNuevo.getText().trim();
        if (nombre.isEmpty() || telefono.isEmpty()) { //EMPTY: SI ESTA VACIO
            JOptionPane.showMessageDialog(null, "Rellena nombre y teléfono");
            return;
        }
        Cliente nuevo = new Cliente();
        nuevo.setNombre(nombre);
        nuevo.setTelefono(telefono);
        nuevo.setSaldo(BigDecimal.ZERO);
        boolean ok = clienteDAO.agregarCliente(nuevo);
        if (ok) {
            JOptionPane.showMessageDialog(null, "Cliente creado correctamente");
            txtNombreClienteNuevo.setText(""); // SE VACIA
            txtTelefonoClienteNuevo.setText("");
            cargarListaClientes();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar cliente");
        }
    }

    private void hacerRecarga() {
        Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona primero un cliente");
            return;
        }
        String textoMonto = txtMontoRecarga.getText().trim();
        BigDecimal montoRecarga;
        try {
            montoRecarga = new BigDecimal(textoMonto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Introduce un número válido");
            return;
        }
        if (montoRecarga.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "El importe debe ser mayor a 0");
            return;
        }
        BigDecimal bonificacion = BigDecimal.ZERO; //BONIFICA 0 EURO POR DEFECTO
        if (montoRecarga.compareTo(new BigDecimal("100")) >= 0) {
            bonificacion = new BigDecimal("20");
        }
        BigDecimal saldoNuevo = clienteSeleccionado.getSaldo().add(montoRecarga).add(bonificacion);
        clienteDAO.actualizarSaldo(clienteSeleccionado.getId(), saldoNuevo); //ACTUALIZAR BASE DE DATO

        Operacion op = new Operacion();
        op.setIdCliente(clienteSeleccionado.getId());
        op.setTipo("RECARGAR");
        op.setMontoBase(montoRecarga);
        op.setBonificacion(bonificacion);
        op.setFecha(operacionDAO.obtenerFechaActual());
        operacionDAO.guardarOperacion(op);

        JOptionPane.showMessageDialog(null, "Recarga realizada.\nBonificación: " + bonificacion + " €");
        txtMontoRecarga.setText("");
        cargarListaClientes();
    }

    private void hacerConsumo() {
        Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona primero un cliente");
            return;
        }
        String textoMonto = txtMontoConsumo.getText().trim();
        BigDecimal montoConsumo;
        try {
            montoConsumo = new BigDecimal(textoMonto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Introduce un número válido");
            return;
        }
        if (montoConsumo.compareTo(BigDecimal.ZERO) <= 0) {
            JOptionPane.showMessageDialog(null, "El importe debe ser mayor a 0");
            return;
        }
        if (clienteSeleccionado.getSaldo().compareTo(montoConsumo) < 0) {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return;
        }
        BigDecimal saldoNuevo = clienteSeleccionado.getSaldo().subtract(montoConsumo); //SUBTRACT = RESTAR
        clienteDAO.actualizarSaldo(clienteSeleccionado.getId(), saldoNuevo);

        Operacion op = new Operacion();
        op.setIdCliente(clienteSeleccionado.getId());
        op.setTipo("GASTO");
        op.setMontoBase(montoConsumo);
        op.setBonificacion(BigDecimal.ZERO);
        op.setFecha(operacionDAO.obtenerFechaActual());
        operacionDAO.guardarOperacion(op);

        JOptionPane.showMessageDialog(null, "Consumo registrado");
        txtMontoConsumo.setText("");
        cargarListaClientes();
    }

    private void verHistorial() {
        Cliente clienteSeleccionado = (Cliente) comboClientes.getSelectedItem();
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Selecciona primero un cliente");
            return;
        }
        List<Operacion> listaHistorial = operacionDAO.listarHistorialCliente(clienteSeleccionado.getId());
        StringBuilder sb = new StringBuilder();
        sb.append("==== HISTORIAL CLIENTE: ").append(clienteSeleccionado.getNombre()).append(" ====\n\n");
        if (listaHistorial.isEmpty()) {
            sb.append("No hay operaciones registradas.");
        } else {
            for (Operacion op : listaHistorial) {
                sb.append("Fecha: ").append(op.getFecha()).append("\n");
                if (op.getTipo().equals("RECARGAR")) {
                    sb.append("→ RECARGA: ").append(op.getMontoBase()).append("€ | Bonificación: ").append(op.getBonificacion()).append("€\n");
                } else {
                    sb.append("→ CONSUMO: ").append(op.getMontoBase()).append("€\n");
                }
                sb.append("------------------------------------\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Historial de operaciones", JOptionPane.INFORMATION_MESSAGE);
  
    }
}
