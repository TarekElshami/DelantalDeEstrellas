

document.getElementById('backBtn').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href =  e.target.getAttribute('data-url') ;
})