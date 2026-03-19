
// ─── Contacts page — form submit ───
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('contact-form');
  if (!form) return;

  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const success = document.getElementById('form-success');
    if (success) success.style.display = 'block';
    form.reset();
    setTimeout(() => {
      if (success) success.style.display = 'none';
    }, 4000);
  });
});
