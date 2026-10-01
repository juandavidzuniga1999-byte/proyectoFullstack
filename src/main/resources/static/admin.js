// ---- PANEL DE ADMINISTRACION ----
// Guarda el JWT en localStorage bajo esta llave.
const TOKEN_KEY = 'sg_admin_token';

function getToken() {
  return localStorage.getItem(TOKEN_KEY);
}

function setToken(token) {
  localStorage.setItem(TOKEN_KEY, token);
}

function limpiarToken() {
  localStorage.removeItem(TOKEN_KEY);
}

function mostrarPanel(mostrar) {
  const loginBox = document.getElementById('loginBox');
  const panelAdmin = document.getElementById('panelAdmin');
  if (!loginBox || !panelAdmin) return;
  loginBox.style.display = mostrar ? 'none' : 'block';
  panelAdmin.style.display = mostrar ? 'block' : 'none';
}

// ---- LOGIN ----
const loginForm = document.getElementById('loginForm');
if (loginForm) {
  loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const usuario = document.getElementById('loginUsuario').value;
    const contraseña = document.getElementById('loginPassword').value;
    const errorEl = document.getElementById('loginError');
    errorEl.textContent = '';

    try {
      const respuesta = await fetch('/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ usuario, "contraseña": contraseña })
      });

      if (!respuesta.ok) {
        const datos = await respuesta.json().catch(() => ({}));
        errorEl.textContent = datos.error || 'No se pudo iniciar sesión.';
        return;
      }

      const datos = await respuesta.json();
      setToken(datos.token);
      mostrarPanel(true);
      cargarPropiedadesAdmin();
    } catch (err) {
      console.error(err);
      errorEl.textContent = 'Error de conexión. Intenta de nuevo.';
    }
  });
}

// ---- LOGOUT ----
const logoutBtn = document.getElementById('logoutBtn');
if (logoutBtn) {
  logoutBtn.addEventListener('click', () => {
    limpiarToken();
    mostrarPanel(false);
  });
}

// ---- AGREGAR PROPIEDAD ----
const formPropiedad = document.getElementById('formPropiedad');
if (formPropiedad) {
  formPropiedad.addEventListener('submit', async (e) => {
    e.preventDefault();
    const errorEl = document.getElementById('formError');
    errorEl.textContent = '';

    const datosForm = new FormData(formPropiedad);
    const propiedad = {
      titulo: datosForm.get('titulo'),
      tipoPropiedad: datosForm.get('tipoPropiedad'),
      imagenUrl: datosForm.get('imagenUrl'),
      condicion: datosForm.get('condicion'),
      ubicacion: datosForm.get('ubicacion'),
      descripcion: datosForm.get('descripcion'),
      precio: Number(datosForm.get('precio')) || 0,
      construccion: Number(datosForm.get('construccion')) || 0,
      terreno: Number(datosForm.get('terreno')) || 0,
      cuarto: Number(datosForm.get('cuarto')) || 0,
      banos: Number(datosForm.get('banos')) || 0,
      medioBano: Number(datosForm.get('medioBano')) || 0,
      estacionamiento: Number(datosForm.get('estacionamiento')) || 0,
    };

    try {
      const respuesta = await fetch('/api/propiedades', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + getToken()
        },
        body: JSON.stringify(propiedad)
      });

      if (respuesta.status === 401 || respuesta.status === 403) {
        errorEl.textContent = 'Tu sesión expiró, vuelve a iniciar sesión.';
        limpiarToken();
        mostrarPanel(false);
        return;
      }

      if (!respuesta.ok) {
        errorEl.textContent = 'No se pudo guardar la propiedad.';
        return;
      }

      formPropiedad.reset();
      cargarPropiedadesAdmin();
    } catch (err) {
      console.error(err);
      errorEl.textContent = 'Error de conexión. Intenta de nuevo.';
    }
  });
}

// ---- LISTAR + BORRAR ----
function formatearPrecioAdmin(valor) {
  return new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 }).format(valor);
}

async function cargarPropiedadesAdmin() {
  const contenedor = document.getElementById('listaAdminPropiedades');
  if (!contenedor) return;

  try {
    const respuesta = await fetch('/api/propiedades');
    const propiedades = await respuesta.json();

    if (!propiedades.length) {
      contenedor.innerHTML = '<p>No hay propiedades cargadas todavía.</p>';
      return;
    }

    contenedor.innerHTML = '';
    propiedades.forEach((p) => {
      const fila = document.createElement('div');
      fila.className = 'admin-fila';
      fila.innerHTML = `
        <div>
          <strong>${p.titulo || p.tipoPropiedad || 'Propiedad'}</strong>
          <span class="admin-fila-detalle">${p.ubicacion || ''} · ${formatearPrecioAdmin(p.precio || 0)}</span>
        </div>
        <button class="admin-btn-borrar" data-id="${p.id}">Eliminar</button>
      `;
      contenedor.appendChild(fila);
    });

    contenedor.querySelectorAll('.admin-btn-borrar').forEach((btn) => {
      btn.addEventListener('click', () => borrarPropiedad(btn.dataset.id));
    });
  } catch (err) {
    console.error(err);
    contenedor.innerHTML = '<p>No se pudieron cargar las propiedades.</p>';
  }
}

async function borrarPropiedad(id) {
  if (!confirm('¿Seguro que quieres eliminar esta propiedad?')) return;

  try {
    const respuesta = await fetch(`/api/propiedades/${id}`, {
      method: 'DELETE',
      headers: { 'Authorization': 'Bearer ' + getToken() }
    });

    if (respuesta.status === 401 || respuesta.status === 403) {
      alert('Tu sesión expiró, vuelve a iniciar sesión.');
      limpiarToken();
      mostrarPanel(false);
      return;
    }

    if (!respuesta.ok && respuesta.status !== 204) {
      alert('No se pudo eliminar la propiedad.');
      return;
    }

    cargarPropiedadesAdmin();
  } catch (err) {
    console.error(err);
    alert('Error de conexión. Intenta de nuevo.');
  }
}

// ---- Al cargar la pagina, revisa si ya hay sesion guardada ----
if (document.getElementById('loginBox')) {
  if (getToken()) {
    mostrarPanel(true);
    cargarPropiedadesAdmin();
  } else {
    mostrarPanel(false);
  }
}