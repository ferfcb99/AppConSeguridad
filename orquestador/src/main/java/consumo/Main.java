package consumo;

import dto.Clientes;
import dto.Empleados;
import dto.Productos;
import dto.Sucursales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    List<String> erroresCliente = new ArrayList<>();
    public static Integer cuenta = 0;

    public static void main(String[] args) {
        String urlClientesObtenerTodos = "http://localhost:8080/api/clientes";
        String urlEmpleadosObtenerTodos = "http://localhost:8080/api/empleados";
        String urlProductosObtenerTodos = "http://localhost:8080/api/productos";
        String urlSucursalesObtenerTodos = "http://localhost:8080/api/sucursales";

        ConsumoApi consumoApi = new ConsumoApi();

        List<Clientes> clientesList = consumoApi.obtenerClientes(urlClientesObtenerTodos);
        for (int i = 0; i < clientesList.size(); i++) {
            validaCliente(clientesList.get(i), (i+1));
        }
        logger.info("En el analisis de clientes se tuvieron {} errores y {} correcto", cuenta, (clientesList.size() - cuenta));

        cuenta = 0;
        List<Empleados> empleadosList = consumoApi.obtenerEmpleados(urlEmpleadosObtenerTodos);
        for (int i = 0; i < empleadosList.size(); i++) {
            validaEmpleado(empleadosList.get(i), (i+1));
        }
        logger.info("En el analisis de empleados se tuvieron {} y {} correcto", cuenta, (clientesList.size() - cuenta));
        cuenta = 0;

        List<Productos> productosList = consumoApi.obtenerProductos(urlProductosObtenerTodos);
        for (int i = 0; i < productosList.size(); i++) {
            validaProducto(productosList.get(i), (i+1));
        }
        logger.info("En el analisis de productos se tuvieron {} y {} correcto", cuenta, (clientesList.size() - cuenta));
        cuenta = 0;

        List<Sucursales> sucursalesList = consumoApi.obtenerSucursales(urlSucursalesObtenerTodos);
        for (int i = 0; i < sucursalesList.size(); i++) {
            validaSucursal(sucursalesList.get(i), (i+1));
        }
        logger.info("En el analisis de empleados se tuvieron {} y {} correcto", cuenta, (clientesList.size() - cuenta));
        cuenta = 0;

    }

    /* Validaciones Cliente */

    public static void validaCliente(Clientes cliente, int idLista) {
        boolean tieneErrores = false;
        if(!esNumeroLongValido(cliente.getIdClientes())){
            logger.error("Cliente [{}] : Error en el id - No puede ser null ni menor o igual a 0.", idLista);
            tieneErrores = true;
        }

        if(!esCadenaValida(cliente.getNombre())){ 
            logger.error("Cliente [{}] : Error en el nombre - No puede ser null o vacío.", idLista);
            tieneErrores = true;
        }else{ 
            String nombreLimpio = cliente.getNombre().trim();
            nombreLimpio = eliminarEspaciosConsecutivos(nombreLimpio);
            nombreLimpio = capitalizarPrimeraLetra(nombreLimpio);
            cliente.setNombre(nombreLimpio);
        }

        if(!esCadenaValida(cliente.getApellido())){
            logger.error("Cliente [{}] : Error en el nombre - No puede ser null o vacío.", idLista);
            tieneErrores = true;
        }else{ 
            String apellidoLimpio = cliente.getApellido().trim();
            apellidoLimpio = eliminarEspaciosConsecutivos(apellidoLimpio);
            apellidoLimpio = capitalizarPrimeraLetra(apellidoLimpio);
            cliente.setApellido(apellidoLimpio);
        }

        if(!esCorreoValido(cliente.getCorreo())){
            logger.error("Cliente [{}] : Error en el email - Validar formato de correo", idLista);
            tieneErrores = true;
        }else{
            String nuevoCorreo = cliente.getCorreo();
            nuevoCorreo = nuevoCorreo.trim().toLowerCase();
            nuevoCorreo = eliminarEspaciosConsecutivos(nuevoCorreo);
            cliente.setCorreo(nuevoCorreo);
        }

        if(!esCadenaValida(cliente.getTelefono())){ // null, ""
            logger.error("Cliente [{}] : Error en el telefono se quedara null", idLista);
            cliente.setTelefono(null);
            tieneErrores = true;
        }else{
            cliente.setTelefono(cliente.getTelefono().replaceAll("[ ()\\-]", ""));
        }

        if(!fechaValida(cliente.getFechaRegistro())){
            logger.error("Cliente [{}] : Error en la fecha - No puede ser null ni una fecha futura.", idLista);
            tieneErrores = true;
        }

        if(tieneErrores){
            cuenta = cuenta + 1;
        }
    }

    public static void validaEmpleado(Empleados empleado, int idLista){

        boolean tieneErrores = false;

        if(!esNumeroLongValido(empleado.getIdEmpleado())){
            logger.error("Empleado [{}] : Error en el id - No puede ser null ni menor o igual a 0.", idLista);
            tieneErrores = true;

        }

        if(!esCadenaValida(empleado.getNombre())){ 
            logger.error("Empleado [{}] : Error en el nombre - No puede ser null o vacío.", idLista);
            tieneErrores = true;

        }else{ 
            String nombreLimpio = empleado.getNombre().trim();
            nombreLimpio = eliminarEspaciosConsecutivos(nombreLimpio);
            nombreLimpio = capitalizarPrimeraLetra(nombreLimpio); // posible capitalizacion por palabra
            empleado.setNombre(nombreLimpio);
        }

        if(!esCadenaValida(empleado.getApellido())){
            logger.error("Empleado [{}] : Error en el apellido - No puede ser null o vacío.", idLista);
            tieneErrores = true;
        }else{ 
            String apellidoLimpio = empleado.getApellido().trim();
            apellidoLimpio = eliminarEspaciosConsecutivos(apellidoLimpio);
            apellidoLimpio = capitalizarPrimeraLetra(apellidoLimpio); // posible capitalizacion por palabra
            empleado.setApellido(apellidoLimpio);
        }

        if(!esCadenaValida(empleado.getPuesto())){
            logger.error("Empleado [{}] : Error en el puesto - No puede ser null o vacío.", idLista);
            tieneErrores = true;

        }else{ 
            String puestoLimpio = empleado.getPuesto().trim();
            puestoLimpio = eliminarEspaciosConsecutivos(puestoLimpio);
            empleado.setPuesto(puestoLimpio);
        }

        if(!esNumeroLongValido(empleado.getIdSucursal())){
            tieneErrores = true;
            logger.error("Empleado [{}] : Error en el idSucursal - No puede ser null ni menor o igual a 0.", idLista);
        }

        if(tieneErrores){
            cuenta = cuenta + 1;
        }

    }

    public static void validaProducto(Productos producto, int idLista){
        boolean tieneErrores = false;
        if(!esNumeroLongValido(producto.getIdProducto())){
            tieneErrores = true;
            logger.error("Producto [{}] : Error en el id - No puede ser null ni menor o igual a 0.", idLista);
        }

        if(!esCadenaValida(producto.getNombre())){ 
            tieneErrores = true;
            logger.error("Producto [{}] : Error en el nombre - No puede ser null o vacío.", idLista);
        }else{
            String nombreLimpio = producto.getNombre().trim();
            nombreLimpio = eliminarEspaciosConsecutivos(nombreLimpio);
            producto.setNombre(nombreLimpio);
        }

        if(producto.getDescripcion() != null){
            tieneErrores = true;
            String nuevaDescripcion = producto.getDescripcion().trim();
            producto.setDescripcion(nuevaDescripcion);
        }

        if(!esNumeroDecimalValido(producto.getPrecio())){
            tieneErrores = true;
            logger.error("Producto [{}] : Error en el precio - No puede ser null y debe ser mayor que 0", idLista);
        }

        if(!esStockValido(producto.getStock())){
            tieneErrores = true;
            logger.error("Producto [{}] : Error en el stock - No puede ser null ni negativo", idLista);
        }
        if(tieneErrores){
            cuenta = cuenta + 1;
        }

    }


    public static void validaSucursal(Sucursales sucursal, int idLista){
        boolean tieneErrores = false;

        if(!esNumeroLongValido(sucursal.getIdSucursal())){
            tieneErrores = true;
            logger.error("Sucursales [{}] : Error en el id - No puede ser null ni menor o igual a 0.", idLista);
        }

        if(!esCadenaValida(sucursal.getNombre())){ 
            tieneErrores = true;
            logger.error("Sucursales [{}] : Error en el nombre - No puede ser null o vacío.", idLista);
        }else{
            String nombreLimpio = sucursal.getNombre().trim();
            nombreLimpio = eliminarEspaciosConsecutivos(nombreLimpio);
            sucursal.setNombre(nombreLimpio);
        }

        if(!esCadenaValida(sucursal.getCiudad())){ 
            tieneErrores = true;
            logger.error("Sucursales [{}] : Error en el ciudad - No puede ser null o vacío.", idLista);
        }else{
            String ciudadLimpio = sucursal.getCiudad().trim();
            ciudadLimpio = eliminarEspaciosConsecutivos(ciudadLimpio);
            ciudadLimpio = capitalizarPrimeraLetra(ciudadLimpio); // posible capitalizacion por palabra
            sucursal.setCiudad(ciudadLimpio);
        }

        if(!esCadenaValida(sucursal.getEstado())){ 
            tieneErrores = true;
            logger.error("Sucursales [{}] : Error en el estado - No puede ser null o vacío.", idLista);
        }else{
            String estadoLimpio = sucursal.getEstado().trim();
            estadoLimpio = eliminarEspaciosConsecutivos(estadoLimpio);
            estadoLimpio = capitalizarPrimeraLetra(estadoLimpio); // posible capitalizacion por palabra
            sucursal.setEstado(estadoLimpio);
        }

        if(!fechaValida(sucursal.getFechaApertura())){
            tieneErrores = true;
            logger.error("Sucursales [{}] : Error en la fecha - No puede ser null ni una fecha futura.", idLista);
        }

        if(tieneErrores){
            cuenta = cuenta + 1;
        }


    }

    public static boolean esNumeroLongValido(Long numero){
        if(numero != null && numero > 0){
            return true;
        }
        return false;
    }

    public  static boolean esNumeroIntegerValido(Integer numero){
        if(numero != null && numero > 0){
            return true;
        }
        return false;
    }

    public static boolean esNumeroDecimalValido(Float numero){
        if(numero != null && numero > 0){
            return true;
        }
        return false;
    }

    public  static boolean esCadenaValida(String cad){
        if(cad != null && !cad.isEmpty() ){
            return true;
        }
        return false;
    }

    public static boolean fechaValida(LocalDate fecha){
        if(fecha != null && !fecha.isAfter(fecha)){
            return true;
        }else{
            return false;
        }
    }

    public static boolean esStockValido(Integer stock){
        if(stock != null && stock >= 0){
            return true;
        }
        return false;
    }

    public static boolean esCorreoValido(String email) {
       Pattern EMAIL_PATTERN = Pattern.compile(
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        );
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static String eliminarEspaciosConsecutivos(String cad){
        return cad.replaceAll(" {2,}", " ");
    }

    public static String capitalizarPrimeraLetra(String cad){
        return Character.toUpperCase(cad.charAt(0)) + cad.substring(1).toLowerCase();
    }




}
