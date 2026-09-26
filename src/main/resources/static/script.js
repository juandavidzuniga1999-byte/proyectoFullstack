// ---- MENÚ MÓVIL (burger) ----
const burger = document.getElementById('burgerBtn');
const navLinks = document.getElementById('navLinks');

if (burger && navLinks) {
  burger.addEventListener('click', () => {
    const isOpen = navLinks.classList.toggle('open');
    burger.setAttribute('aria-expanded', isOpen);
  });

  navLinks.querySelectorAll('a').forEach(link => {
    link.addEventListener('click', () => {
      navLinks.classList.remove('open');
      burger.setAttribute('aria-expanded', 'false');
    });
  });
}

// ---- LIGHTBOX (galería) ----
function abrirLightbox(img) {
  const lightbox = document.getElementById('lightbox');
  const lightboxImg = document.getElementById('lightbox-img');
  if (!lightbox || !lightboxImg) return;
  lightboxImg.src = img.src;
  lightbox.classList.add('activo');
}

function cerrarLightbox() {
  const lightbox = document.getElementById('lightbox');
  if (lightbox) lightbox.classList.remove('activo');
}

const lightboxEl = document.getElementById('lightbox');
if (lightboxEl) {
  lightboxEl.addEventListener('click', (e) => {
    if (e.target.id === 'lightbox') {
      cerrarLightbox();
    }
  });
}

// ---- CARRUSEL DE SERVICIOS ----
const servicios = [
  { img: "images/casa1.jpg", alt: "Servicios hipotecarios", subtitulo: "SERVICIOS", titulo: "Hipotecarios" },
  { img: "images/casa1.jpg", alt: "administracion de inmuebles", subtitulo: "ADMINISTRACION DE", titulo: "Inmbuebles" },
  { img: "images/casa1.jpg", alt: "escritura propiedad", subtitulo: "ESCRITURACION DE", titulo: "Propiedad" },
  { img: "images/casa1.jpg", alt: "asesoria fiscal", subtitulo: "ASESORIA", titulo: "Fiscal" },
  { img: "images/casa1.jpg", alt: "gestion ante instituciones gubernamentales", subtitulo: "GESTION ANTE INSTITUCIONES", titulo: "Gubernamentales" },
  { img: "images/casa1.jpg", alt: "promocion de inmueble", subtitulo: "PROMOCION DE", titulo: "Inmbueble" },
  { img: "images/casa1.jpg", alt: "opiniones de valor", subtitulo: "OPINIONES DE", titulo: "Valor" },
  { img: "images/casa1.jpg", alt: "analisis juridico de inmuebles", subtitulo: "ANALISIS JURIDICO DE", titulo: "Inmbuebles" },
  { img: "images/casa1.jpg", alt: "servicios notariales", subtitulo: "SERVICIOS", titulo: "Notariales" },
  { img: "images/casa1.jpg", alt: "servicios de mantenimiento", subtitulo: "SERVICIOS DE", titulo: "Mantenimiento" },
];

let indiceActual = 1;

function crearCarrusel() {
  const track = document.getElementById('carruselTrack');
  const puntos = document.getElementById('carruselPuntos');
  if (!track || !puntos) return;

  track.innerHTML = '';
  servicios.forEach((servicio) => {
    const card = document.createElement('div');
    card.className = 'carrusel-card';

    let overlayHTML = '';
    if (servicio.titulo) {
      overlayHTML = `
        <div class="carrusel-overlay">
          <span class="carrusel-subtitulo">${servicio.subtitulo || ''}</span>
          <h3 class="carrusel-titulo">${servicio.titulo}</h3>
        </div>`;
    }

    card.innerHTML = `<img src="${servicio.img}" alt="${servicio.alt}">${overlayHTML}`;
    track.appendChild(card);
  });

  puntos.innerHTML = '';
  servicios.forEach((_, i) => {
    const punto = document.createElement('span');
    punto.onclick = () => { indiceActual = i; actualizarCarrusel(); };
    puntos.appendChild(punto);
  });

  actualizarCarrusel();
}

function actualizarCarrusel() {
  const track = document.getElementById('carruselTrack');
  const puntos = document.getElementById('carruselPuntos');
  if (!track || !puntos) return;

  const cards = track.querySelectorAll('.carrusel-card');
  const anchoCard = 340 + 24;

  const viewportWidth = track.parentElement.offsetWidth;
  const desplazamiento = (viewportWidth / 2) - (anchoCard * indiceActual) - (340 / 2);
  track.style.transform = `translateX(${desplazamiento}px)`;

  cards.forEach((card, i) => {
    card.classList.toggle('destacada', i === indiceActual);
  });

  puntos.querySelectorAll('span').forEach((punto, i) => {
    punto.classList.toggle('activo', i === indiceActual);
  });
}

function moverCarrusel(direccion) {
  indiceActual = (indiceActual + direccion + servicios.length) % servicios.length;
  actualizarCarrusel();
}

crearCarrusel();
window.addEventListener('resize', actualizarCarrusel);

// ---- PROPIEDADES DESDE LA BASE DE DATOS ----
function formatearPrecio(valor) {
  return new Intl.NumberFormat('es-MX', { style: 'currency', currency: 'MXN', maximumFractionDigits: 0 }).format(valor);
}

async function cargarPropiedades() {
  const grid = document.getElementById('propiedadesGrid');
  const estado = document.getElementById('propiedadesEstado');
  if (!grid) return;

  try {
    const respuesta = await fetch('/api/propiedades');
    if (!respuesta.ok) throw new Error('Respuesta no OK: ' + respuesta.status);

    const propiedades = await respuesta.json();

    if (!propiedades.length) {
      if (estado) estado.textContent = 'Aún no hay propiedades cargadas.';
      return;
    }

    grid.innerHTML = '';

    propiedades.forEach((p) => {
      const card = document.createElement('div');
      card.className = 'propiedad-card';

      card.innerHTML = `
        <div class="propiedad-imagen" style="background-image:url('${p.imagenUrl || 'images/casa1.jpg'}')"></div>
        <div class="propiedad-info">
          <h3 class="propiedad-titulo">${p.titulo || p.tipoPropiedad || 'Propiedad'}</h3>
          <div class="propiedad-datos">
            <span>Construcción: ${p.construccion ?? '-'} m²</span>
            <span>Terreno: ${p.terreno ?? '-'} m²</span>
          </div>
          <span class="propiedad-condicion">${p.condicion || ''}</span>
          <span class="propiedad-precio">${formatearPrecio(p.precio || 0)}</span>
        </div>
      `;

      card.style.cursor = 'pointer';
      card.onclick = () => { window.location.href = `propiedad.html?id=${p.id}`; };

      grid.appendChild(card);
    });
  } catch (error) {
    console.error('No se pudieron cargar las propiedades:', error);
    if (estado) estado.textContent = 'No se pudieron cargar las propiedades. Intenta más tarde.';
  }
}

cargarPropiedades();

// ---- GALERÍA DE FOTOS DE UNA PROPIEDAD (propiedad.html) ----
let fotosPropiedadActual = [];
let indiceFotoActual = 0;

function obtenerIdDeUrl() {
  return new URLSearchParams(window.location.search).get('id');
}

function mostrarFotoActual() {
  const fotoPrincipal = document.getElementById('fotoPrincipal');
  if (!fotoPrincipal || !fotosPropiedadActual.length) return;
  fotoPrincipal.src = fotosPropiedadActual[indiceFotoActual];
  document.querySelectorAll('.miniatura').forEach((mini, i) => {
    mini.classList.toggle('activa', i === indiceFotoActual);
  });
}

function cambiarFoto(direccion) {
  if (!fotosPropiedadActual.length) return;
  indiceFotoActual = (indiceFotoActual + direccion + fotosPropiedadActual.length) % fotosPropiedadActual.length;
  mostrarFotoActual();
}

async function cargarDetallePropiedad() {
  const fotoPrincipal = document.getElementById('fotoPrincipal');
  if (!fotoPrincipal) return; // no estamos en propiedad.html

  const id = obtenerIdDeUrl();
  const titulo = document.getElementById('tituloPropiedad');
  if (!id) {
    titulo.textContent = 'Propiedad no especificada';
    return;
  }

  try {
    const respuesta = await fetch(`/api/propiedades/${id}`);
    if (!respuesta.ok) throw new Error('No encontrada');
    const p = await respuesta.json();

    // --- fotos ---
    fotosPropiedadActual = (p.imagenes && p.imagenes.length) ? p.imagenes : [p.imagenUrl || 'images/casa1.jpg'];
    indiceFotoActual = 0;
    mostrarFotoActual();

    const miniaturas = document.getElementById('miniaturasPropiedad');
    miniaturas.innerHTML = '';
    fotosPropiedadActual.forEach((url, i) => {
      const mini = document.createElement('img');
      mini.src = url;
      mini.className = 'miniatura' + (i === 0 ? ' activa' : '');
      mini.onclick = () => { indiceFotoActual = i; mostrarFotoActual(); };
      miniaturas.appendChild(mini);
    });

    // --- título / precio ---
    titulo.textContent = p.titulo || p.tipoPropiedad || 'Propiedad';
    document.getElementById('precioPropiedad').textContent = formatearPrecio(p.precio || 0);

    // --- etiquetas ---
    const etiquetas = document.getElementById('etiquetasPropiedad');
    etiquetas.innerHTML = '';
    [p.condicion, p.tipoPropiedad, p.ubicacion].filter(Boolean).forEach(txt => {
      const span = document.createElement('span');
      span.className = 'etiqueta';
      span.textContent = txt;
      etiquetas.appendChild(span);
    });

    // --- datos numéricos ---
    document.getElementById('datosPropiedad').innerHTML = `
  <div class="dato"><span class="dato-num">${p.construccion ?? '-'}</span><span class="dato-label">m² Construcción</span></div>
  <div class="dato"><span class="dato-num">${p.terreno ?? '-'}</span><span class="dato-label">m² Terreno</span></div>
  <div class="dato"><span class="dato-num">${p.cuarto ?? '-'}</span><span class="dato-label">Recámaras</span></div>
  <div class="dato"><span class="dato-num">${p.banos ?? '-'}</span><span class="dato-label">Baños</span></div>
  <div class="dato"><span class="dato-num">${p.estacionamiento ?? '-'}</span><span class="dato-label">Estacionamiento</span></div>
  ${p.descripcion ? `
    <div class="dato-descripcion">
      <span class="dato-label">Descripción</span>
      <p class="detalle-descripcion">${p.descripcion}</p>
    </div>
  ` : ''}
`;

    
    

  } catch (error) {
    console.error('No se pudo cargar la propiedad:', error);
    titulo.textContent = 'No se pudo cargar la propiedad';
  }
}


cargarDetallePropiedad();