/* ============================================================
   Portail Doctorat – JavaScript principal
   ============================================================ */

document.addEventListener('DOMContentLoaded', () => {

  /* ── Auto-dismiss alerts after 5s ── */
  document.querySelectorAll('.alert.alert-dismissible').forEach(alert => {
    setTimeout(() => {
      const bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
      if (bsAlert) bsAlert.close();
    }, 5000);
  });

  /* ── Active nav link ── */
  const currentPath = window.location.pathname;
  document.querySelectorAll('.sidebar a.nav-link').forEach(link => {
    const href = link.getAttribute('href');
    if (href && currentPath === href) {
      link.classList.add('active');
    }
  });

  /* ── Confirm before form submit (data-confirm) ── */
  document.querySelectorAll('form[data-confirm]').forEach(form => {
    form.addEventListener('submit', e => {
      const msg = form.dataset.confirm || 'Confirmer cette action ?';
      if (!confirm(msg)) e.preventDefault();
    });
  });

  /* ── Sidebar mobile toggle ── */
  const sidebar = document.querySelector('.sidebar');
  const toggleBtn = document.getElementById('sidebar-toggle');
  if (toggleBtn && sidebar) {
    toggleBtn.addEventListener('click', () => sidebar.classList.toggle('open'));
  }

  /* ── Tooltips Bootstrap ── */
  document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(el => {
    new bootstrap.Tooltip(el, { trigger: 'hover' });
  });

  /* ── Animate progress bars on load ── */
  document.querySelectorAll('.progress-bar').forEach(bar => {
    const target = bar.style.width;
    bar.style.width = '0%';
    requestAnimationFrame(() => {
      bar.style.transition = 'width 0.7s ease';
      bar.style.width = target;
    });
  });

  /* ── Search filter table ── */
  const searchInput = document.getElementById('table-search');
  if (searchInput) {
    const tableBody = document.querySelector('#searchable-table tbody');
    searchInput.addEventListener('input', () => {
      const term = searchInput.value.toLowerCase();
      tableBody.querySelectorAll('tr').forEach(row => {
        const text = row.textContent.toLowerCase();
        row.style.display = text.includes(term) ? '' : 'none';
      });
    });
  }

});
