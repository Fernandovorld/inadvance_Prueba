package com.inadvance.inadvance.serviceTest;
import com.inadvance.inadvance.Dao.IPhonesDao;
import com.inadvance.inadvance.Dao.IStatusDao;
import com.inadvance.inadvance.Dao.IUsuarioDao;
import com.inadvance.inadvance.entity.Phones;
import com.inadvance.inadvance.entity.Usuario;
import com.inadvance.inadvance.service.UsuarioServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class UsuarioServiceImpTest {

    @Mock
    private IUsuarioDao usuarioDaoMock;

    @Mock
    private IPhonesDao phonesDaoMock;

    @Mock
    private IStatusDao statusDaoMock;

    @InjectMocks
    private UsuarioServiceImp usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUsuarioTest() {
        // Mock de un usuario existente
        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1L);
        existingUsuario.setEmail("fernando@example.com");
        existingUsuario.setName("Fernando");
        existingUsuario.setFechaModificacion(new Date());
        existingUsuario.setPhones(new ArrayList<>());

        // Mock de una solicitud HTTP con un token (puede ser simplificada)
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer token");

        // Usuario enviado en el PUT request
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setEmail("fernando@example.com");
        usuarioActualizado.setName("Fernando Updated");
        usuarioActualizado.setPhones(new ArrayList<>());

        // Mockear la búsqueda de usuario
        when(usuarioDaoMock.findByEmail("fernando@example.com")).thenReturn(existingUsuario);

        // Llamada al método bajo prueba
        Usuario result = usuarioService.updateUsuario(usuarioActualizado, mockRequest);

        // Verificar que los métodos internos fueron invocados correctamente
        verify(usuarioDaoMock).findByEmail("fernando@example.com");
        verify(usuarioDaoMock).save(any(Usuario.class));

        // Validar el resultado
        assertEquals("Fernando Updated", result.getName());
    }

    @Test
    void setUsuariosTest() {
        // Mock de un nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail("nuevo@example.com");
        nuevoUsuario.setName("Nuevo Usuario");
        nuevoUsuario.setPhones(new ArrayList<>());

        // Mock de una solicitud HTTP
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer token");

        // Mockear la búsqueda de usuario
        when(usuarioDaoMock.findByEmail("nuevo@example.com")).thenReturn(null);
        when(usuarioDaoMock.save(any(Usuario.class))).thenReturn(nuevoUsuario);

        // Llamada al método bajo prueba
        Usuario result = usuarioService.setUsuarios(nuevoUsuario, mockRequest);

        // Verificar que se guardó el usuario correctamente
        verify(usuarioDaoMock).save(any(Usuario.class));
        assertEquals("nuevo@example.com", result.getEmail());
        assertEquals("Nuevo Usuario", result.getName());
    }

    @Test
    void getUsuarioTest() {
        // Mock de un usuario existente
        Usuario existingUsuario = new Usuario();
        existingUsuario.setEmail("fernando@example.com");

        // Mock de una solicitud HTTP
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getHeader("Authorization")).thenReturn("Bearer token");

        // Mockear la búsqueda de usuario
        when(usuarioDaoMock.findByEmail("fernando@example.com")).thenReturn(existingUsuario);

        // Llamada al método bajo prueba
        Usuario result = usuarioService.getUsuario(existingUsuario, mockRequest);

        // Verificar que el resultado es correcto
        assertEquals("fernando@example.com", result.getEmail());
    }
}
