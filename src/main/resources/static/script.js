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
  { img: "images/casa1.jpg", alt: "Servicios hipotecarios" , subtitulo: "SERVICIOS", titulo: "Hipotecarios" },
  { img: "images/casa1.jpg", alt: "administracion de inmuebles" , subtitulo: "ADMINISTRACION DE", titulo: "Inmbuebles" },
  { img: "images/casa1.jpg", alt: "escritura propiedad" , subtitulo: "ESCRITURACION DE", titulo: "Propiedad" },
  { img: "images/casa1.jpg", alt: "asesoria fiscal" , subtitulo: "ASESORIA", titulo: "Fiscal" },
  { img: "images/casa1.jpg", alt: "gestion ante instituciones gubernamentales" , subtitulo: "GESTION ANTE INSTITUCIONES", titulo: "Gubernamentales" },
  { img: "images/casa1.jpg", alt: "promocion de inmueble" , subtitulo: "PROMOCION DE", titulo: "Inmbueble" },
  { img: "images/casa1.jpg", alt: "opiniones de valor" , subtitulo: "OPINIONES DE", titulo: "Valor" },
  { img: "images/casa1.jpg", alt: "analisis juridico de inmuebles" , subtitulo: "ANALISIS JURIDICO DE", titulo: "Inmbuebles" },
  { img: "images/casa1.jpg", alt: "servicios notariales" , subtitulo: "SERVICIOS", titulo: "Notariales" },
  { img: "images/casa1.jpg", alt: "servicios de mantenimiento" , subtitulo: "SERVICIOS DE", titulo: "Mantenimiento" },
];

let indiceActual = 1;

function crearCarrusel() {
  const track = document.getElementById('carruselTrack');
  const puntos = document.getElementById('carruselPuntos');
  if (!track || !puntos) return;

  // Crea TODAS las tarjetas una sola vez (esto ya no se vuelve a borrar)
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

  // Crea los puntos una sola vez también
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
  const anchoCard = 340 + 24; // ancho de la tarjeta + el gap, deben coincidir con tu CSS

  // Mueve la fila entera para centrar la tarjeta activa
  const viewportWidth = track.parentElement.offsetWidth;
  const desplazamiento = (viewportWidth / 2) - (anchoCard * indiceActual) - (340 / 2);
  track.style.transform = `translateX(${desplazamiento}px)`;

  // Marca cuál tarjeta es la destacada
  cards.forEach((card, i) => {
    card.classList.toggle('destacada', i === indiceActual);
  });

  // Marca cuál punto está activo
  puntos.querySelectorAll('span').forEach((punto, i) => {
    punto.classList.toggle('activo', i === indiceActual);
  });
}

function moverCarrusel(direccion) {
  indiceActual = (indiceActual + direccion + servicios.length) % servicios.length;
  actualizarCarrusel();
}

crearCarrusel();

// Recalcula la posición si cambia el tamaño de la ventana
window.addEventListener('resize', actualizarCarrusel);