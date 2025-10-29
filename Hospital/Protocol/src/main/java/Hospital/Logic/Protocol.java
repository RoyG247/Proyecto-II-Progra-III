package Hospital.Logic;

public class Protocol {
    // Configuración del servidor
    public static final String SERVER = "localhost";
    public static final int PORT = 1234;

    // Operaciones de Administrador
    public static final int ADMINISTRADOR_READ = 101;

    // Operaciones de Médico
    public static final int MEDICO_CREATE = 201;
    public static final int MEDICO_READ = 202;
    public static final int MEDICO_UPDATE = 203;
    public static final int MEDICO_DELETE = 204;
    public static final int MEDICO_SEARCH = 205;
    public static final int MEDICO_SEARCH_ID = 206;
    public static final int MEDICO_SEARCH_ID_FILT = 207;
    public static final int MEDICO_FIND_ALL = 208;

    // Operaciones de Paciente
    public static final int PACIENTE_CREATE = 301;
    public static final int PACIENTE_READ = 302;
    public static final int PACIENTE_UPDATE = 303;
    public static final int PACIENTE_DELETE = 304;
    public static final int PACIENTE_SEARCH = 305;
    public static final int PACIENTE_SEARCH_ONE = 306;
    public static final int PACIENTE_SEARCH_ID = 307;
    public static final int PACIENTE_FIND_ALL = 308;

    // Operaciones de Farmaceuta
    public static final int FARMACEUTA_CREATE = 401;
    public static final int FARMACEUTA_READ = 402;
    public static final int FARMACEUTA_UPDATE = 403;
    public static final int FARMACEUTA_DELETE = 404;
    public static final int FARMACEUTA_SEARCH = 405;
    public static final int FARMACEUTA_SEARCH_ID = 406;
    public static final int FARMACEUTA_SEARCH_ID_FILT = 407;
    public static final int FARMACEUTA_FIND_ALL = 408;

    // Operaciones de Medicamento
    public static final int MEDICAMENTO_CREATE = 501;
    public static final int MEDICAMENTO_READ = 502;
    public static final int MEDICAMENTO_UPDATE = 503;
    public static final int MEDICAMENTO_DELETE = 504;
    public static final int MEDICAMENTO_SEARCH = 505;
    public static final int MEDICAMENTO_SEARCH_ID = 506;
    public static final int MEDICAMENTO_SEARCH_ONE = 507;
    public static final int MEDICAMENTO_FIND_ALL = 508;

    // Operaciones de Receta
    public static final int RECETA_CREATE = 601;
    public static final int RECETA_READ = 602;
    public static final int RECETA_FIND_ALL = 603;
    public static final int RECETA_GET_RECETAS = 604;
    public static final int RECETA_POR_PACIENTE = 605;
    public static final int RECETA_AVANZAR_ESTADO = 606;

    // Operaciones de Empleado
    public static final int EMPLEADO_READ = 701;
    public static final int EMPLEADO_UPDATE = 702;

    // Códigos de error
    public static final int ERROR_NO_ERROR = 0;
    public static final int ERROR_ERROR = 1;
    public static final int ERROR_NOT_FOUND = 2;
    public static final int ERROR_INVALID_DATA = 3;
    public static final int ERROR_ALREADY_EXISTS = 4;
    public static final int ERROR_DATABASE = 5;

    // Misc
    public static final int SYNC = 10;
    public static final int ASYNC = 11;
    public static final int DISCONNECT = 99;
    public static final int DELIVER_EMPLOYEE = 666;
}