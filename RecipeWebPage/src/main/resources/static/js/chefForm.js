

function validateForm(){
    if (document.getElementById("name").textContent === "Anónimo"){
        alert("No es posible crear un Chef con ese nombre.")
        return false;
    }
    return true
}

document.getElementById('backBtn').addEventListener('click', (e) => {
    e.preventDefault();
    window.location.href =  e.target.getAttribute('data-url') ;
})